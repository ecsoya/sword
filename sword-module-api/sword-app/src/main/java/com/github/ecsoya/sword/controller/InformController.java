package com.github.ecsoya.sword.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.ecsoya.sword.common.core.controller.BaseController;
import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.common.core.page.TableDataInfo;
import com.github.ecsoya.sword.system.domain.SysNotice;
import com.github.ecsoya.sword.system.service.ISysNoticeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * The Class InformController.
 */
@RestController
@RequestMapping("/inform")
@Api(tags = { "消息" }, description = "查询、详情")
public class InformController extends BaseController {

	/** The notice service. */
	@Autowired
	private ISysNoticeService noticeService;

	/**
	 * List.
	 *
	 * @param pageSize the page size
	 * @param pageNum  the page num
	 * @return the table data info
	 */
	@ApiOperation("查询消息列表（支持分页）")
	@GetMapping("/list")
	public TableDataInfo list(@ApiParam("每页显示多少条") Integer pageSize, @ApiParam("当前页数，第几页") Integer pageNum) {
		startPage("create_time desc");
		final List<SysNotice> informs = noticeService.selectNoticeListByType(SysNotice.NOTICE_TYPE_INFORM);
		return getDataTable(informs);
	}

	/**
	 * Detail.
	 *
	 * @param id the id
	 * @return the common result
	 */
	@ApiOperation("查询消息详情")
	@GetMapping("/detail")
	public CommonResult<SysNotice> detail(Long id) {
		return CommonResult.build(noticeService.selectNoticeById(id));
	}
}
