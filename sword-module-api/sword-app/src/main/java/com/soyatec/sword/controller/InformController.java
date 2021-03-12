package com.soyatec.sword.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soyatec.sword.common.core.controller.BaseController;
import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.common.core.page.TableDataInfo;
import com.soyatec.sword.system.domain.SysNotice;
import com.soyatec.sword.system.service.ISysNoticeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/inform")
@Api(tags = { "消息" }, description = "查询、详情")
public class InformController extends BaseController {

	@Autowired
	private ISysNoticeService noticeService;

	@ApiOperation("查询消息列表（支持分页）")
	@GetMapping("/list")
	public TableDataInfo list() {
		startPage();
		List<SysNotice> informs = noticeService.selectNoticeListByType(SysNotice.NOTICE_TYPE_INFORM);
		return getDataTable(informs);
	}

	@ApiOperation("查询消息详情")
	@GetMapping("/detail")
	public CommonResult<SysNotice> detail(Long id) {
		return CommonResult.build(noticeService.selectNoticeById(id));
	}
}
