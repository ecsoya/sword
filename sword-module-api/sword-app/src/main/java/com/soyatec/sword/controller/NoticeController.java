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
@Api(tags = { "公告" }, description = "首页小喇叭信息查询、列表")
public class NoticeController extends BaseController {

	@Autowired
	private ISysNoticeService noticeService;

	@ApiOperation("查询公告")
	@GetMapping("/latest")
	public CommonResult<String> notice() {
		return CommonResult.build(noticeService.getLatestNoticeTitle());
	}

	@ApiOperation("查询公告列表")
	@GetMapping("/list")
	public CommonResult<List<SysNotice>> noticeList() {
		List<SysNotice> list = noticeService.selectNoticeListByType(SysNotice.NOTICE_TYPE_NOTICE);
		return CommonResult.build(list);
	}
}
