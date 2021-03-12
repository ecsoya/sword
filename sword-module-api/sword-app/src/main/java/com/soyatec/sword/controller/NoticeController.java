package com.soyatec.sword.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soyatec.sword.common.core.controller.BaseController;
import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.system.domain.SysNotice;
import com.soyatec.sword.system.service.ISysNoticeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/notice")
@Api(tags = { "通知" }, description = "查询、列表")
public class NoticeController extends BaseController {

	@Autowired
	private ISysNoticeService noticeService;

	@ApiOperation("查询通知")
	@GetMapping("/latest")
	public CommonResult<String> notice() {
		return CommonResult.build(noticeService.getLatestNoticeTitle());
	}

	@ApiOperation("查询通知列表")
	@GetMapping("/list")
	public CommonResult<List<SysNotice>> noticeList() {
		List<SysNotice> list = noticeService.selectNoticeListByType(SysNotice.NOTICE_TYPE_NOTICE);
		return CommonResult.build(list);
	}
}
