package com.github.ecsoya.sword.controller;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.ecsoya.sword.common.annotation.RepeatSubmit;
import com.github.ecsoya.sword.common.config.GlobalConfig;
import com.github.ecsoya.sword.common.constant.Constants;
import com.github.ecsoya.sword.common.core.domain.AjaxResult;
import com.github.ecsoya.sword.common.utils.IdWorker;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.common.utils.file.FileUtils;
import com.github.ecsoya.sword.common.utils.file.ImageUtils;
import com.github.ecsoya.sword.upload.utils.FileUploadUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * The Class CommonController.
 */
@RestController
@Api(tags = { "上传" }, description = "上传、下载")
public class CommonController {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(CommonController.class);

	/**
	 * File download.
	 *
	 * @param fileName the file name
	 * @param delete   the delete
	 * @param response the response
	 * @param request  the request
	 */
	@ApiOperation("通用下载请求")
	@GetMapping("common/download")
	public void fileDownload(String fileName, Boolean delete, HttpServletResponse response,
			HttpServletRequest request) {
		try {
			if (!FileUtils.checkAllowDownload(fileName)) {
				throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
			}
			final String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
			final String filePath = GlobalConfig.getDownloadPath() + fileName;

			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			FileUtils.setAttachmentResponseHeader(response, realFileName);
			FileUtils.writeBytes(filePath, response.getOutputStream());
			if (delete) {
				FileUtils.deleteFile(filePath);
			}
		} catch (final Exception e) {
			log.error("下载文件失败", e);
		}
	}

	/**
	 * Upload file.
	 *
	 * @param file  the file
	 * @param width the width
	 * @return the ajax result
	 * @throws Exception the exception
	 */
	@ApiOperation("通用上传请求")
	@PostMapping("/common/upload")
	@RepeatSubmit
	public AjaxResult uploadFile(MultipartFile file, Integer width) throws Exception {
		if (width != null && width > 0 && file != null) {
			// 压缩文件上传
			InputStream in = ImageUtils.compressImage(file.getInputStream(), width);
			if (in != null) {
				try {
					final String fileName = FileUploadUtils.upload(IdWorker.getIdStr() + ".jpg", in);
					final AjaxResult ajax = AjaxResult.success();
					ajax.put("fileName", fileName);
					ajax.put("url", fileName);
					return ajax;
				} catch (final Exception e) {
					return AjaxResult.error(e.getMessage());
				}
			}
		}
		try {
			// 上传并返回新文件名称
			final String fileName = FileUploadUtils.upload(file);
			final AjaxResult ajax = AjaxResult.success();
			ajax.put("fileName", fileName);
			ajax.put("url", fileName);
			return ajax;
		} catch (final Exception e) {
			return AjaxResult.error(e.getMessage());
		}
	}

	/**
	 * Resource download.
	 *
	 * @param resource the resource
	 * @param request  the request
	 * @param response the response
	 * @throws Exception the exception
	 */
	@ApiOperation("本地资源通用下载")
	@GetMapping("/common/download/resource")
	public void resourceDownload(String resource, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			if (!FileUtils.checkAllowDownload(resource)) {
				throw new Exception(StringUtils.format("资源文件({})非法，不允许下载。 ", resource));
			}
			// 本地资源路径
			final String localPath = GlobalConfig.getProfile();
			// 数据库资源地址
			final String downloadPath = localPath
					+ org.apache.commons.lang3.StringUtils.substringAfter(resource, Constants.RESOURCE_PREFIX);
			// 下载名称
			final String downloadName = org.apache.commons.lang3.StringUtils.substringAfterLast(downloadPath, "/");
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			FileUtils.setAttachmentResponseHeader(response, downloadName);
			FileUtils.writeBytes(downloadPath, response.getOutputStream());
		} catch (final Exception e) {
			log.error("下载文件失败", e);
		}
	}
}
