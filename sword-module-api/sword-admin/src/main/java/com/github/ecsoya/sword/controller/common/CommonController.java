package com.github.ecsoya.sword.controller.common;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.ecsoya.sword.common.annotation.RepeatSubmit;
import com.github.ecsoya.sword.common.config.GlobalConfig;
import com.github.ecsoya.sword.common.constant.Constants;
import com.github.ecsoya.sword.common.core.controller.BaseController;
import com.github.ecsoya.sword.common.core.domain.AjaxResult;
import com.github.ecsoya.sword.common.utils.IdWorker;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.common.utils.file.FileUtils;
import com.github.ecsoya.sword.common.utils.file.ImageUtils;
import com.github.ecsoya.sword.framework.shiro.util.ShiroUtils;
import com.github.ecsoya.sword.service.IMailCodeService;
import com.github.ecsoya.sword.service.IMobileCodeService;
import com.github.ecsoya.sword.upload.utils.FileUploadUtils;

/**
 * The Class CommonController.
 */
@Controller
public class CommonController extends BaseController {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(CommonController.class);

	/** The mail code service. */
	@Autowired(required = false)
	private IMailCodeService mailCodeService;

	/** The mobile code service. */
	@Autowired(required = false)
	private IMobileCodeService mobileCodeService;

	/**
	 * Delivery.
	 *
	 * @param subject  the subject
	 * @param template the template
	 * @return the ajax result
	 */
	@PostMapping("/code/delivery")
	@RepeatSubmit
	@ResponseBody
	public AjaxResult delivery(String subject, String template) {
		final Long userId = ShiroUtils.getUserId();
		if (GlobalConfig.getEmailCode()) {
			if (mailCodeService == null) {
				return error("不支持邮件");
			}
			return toAjax(mailCodeService.sendCodeByUserId(userId, subject, template));
		}
		if (mobileCodeService == null) {
			return error("不支持短信");
		}
		return toAjax(mobileCodeService.sendCodeByUserId(userId));
	}

	/**
	 * Verify code.
	 *
	 * @param code the code
	 * @return the ajax result
	 */
	@PostMapping("/code/verify")
	@RepeatSubmit
	@ResponseBody
	public AjaxResult verifyCode(String code) {
		if (StringUtils.isEmpty(code)) {
			return error("请输入验证码");
		}
		final Long userId = ShiroUtils.getUserId();
		if (GlobalConfig.getEmailCode()) {
			if (mailCodeService == null) {
				return error("不支持邮件");
			}
			return toAjax(mailCodeService.verifyCodeByUserId(userId, code));
		}
		if (mobileCodeService == null) {
			return error("不支持短信");
		}
		return toAjax(mobileCodeService.verifyCodeByUserId(userId, code));
	}

	/**
	 * File download.
	 *
	 * @param fileName the file name
	 * @param delete   the delete
	 * @param response the response
	 * @param request  the request
	 */
	@GetMapping("common/download")
	public void fileDownload(String fileName, Boolean delete, HttpServletResponse response,
			HttpServletRequest request) {
		try {
//			if (!FileUtils.checkAllowDownload(fileName)) {
//				throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
//			}
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
	@PostMapping("/common/upload")
	@ResponseBody
	public AjaxResult uploadFile(MultipartFile file, Integer width) throws Exception {
		if (width != null && width > 0 && file != null) {
			InputStream in = ImageUtils.compressImage(file.getInputStream(), width);
			if (in != null) {
				try {
					// 上传并返回新文件名称
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
	 * Upload file cloud.
	 *
	 * @param file  the file
	 * @param width the width
	 * @return the ajax result
	 * @throws Exception the exception
	 */
	@PostMapping("/common/uploadCloud")
	@ResponseBody
	public AjaxResult uploadFileCloud(MultipartFile file, Integer width) throws Exception {
		if (width != null && width > 0 && file != null) {
			InputStream in = ImageUtils.compressImage(file.getInputStream(), width);
			if (in != null) {
				try {
					// 上传并返回新文件名称
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
	 * Resource download.
	 *
	 * @param resource the resource
	 * @param request  the request
	 * @param response the response
	 * @throws Exception the exception
	 */
	@GetMapping("/common/download/resource")
	public void resourceDownload(String resource, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
//			if (!FileUtils.checkAllowDownload(resource)) {
//				throw new Exception(StringUtils.format("资源文件({})非法，不允许下载。 ", resource));
//			}
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
