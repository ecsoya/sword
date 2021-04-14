package com.soyatec.sword.controller.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.soyatec.sword.common.config.GlobalConfig;
import com.soyatec.sword.common.constant.Constants;
import com.soyatec.sword.common.core.domain.AjaxResult;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.common.utils.file.FileUtils;
import com.soyatec.sword.upload.utils.FileUploadUtils;

/**
 * 通用请求处理
 *
 * @author Jin Liu (angryred@qq.com)
 */
@Controller
public class CommonController {
	private static final Logger log = LoggerFactory.getLogger(CommonController.class);

	/**
	 * 通用下载请求
	 *
	 * @param fileName 文件名称
	 * @param delete   是否删除
	 */
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
	 * 通用上传请求
	 */
	@PostMapping("/common/upload")
	@ResponseBody
	public AjaxResult uploadFile(MultipartFile file) throws Exception {
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

	@PostMapping("/common/uploadCloud")
	@ResponseBody
	public AjaxResult uploadFileCloud(MultipartFile file) throws Exception {
		try {
			// 上传并返回新文件名称
			final String fileName = FileUploadUtils.upload(file);
			final String url = fileName;
			final AjaxResult ajax = AjaxResult.success();
			ajax.put("fileName", fileName);
			ajax.put("url", url);
			return ajax;
		} catch (final Exception e) {
			return AjaxResult.error(e.getMessage());
		}
	}

	/**
	 * 本地资源通用下载
	 */
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
