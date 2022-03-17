package com.github.ecsoya.sword.mining.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.ecsoya.sword.common.annotation.RepeatSubmit;
import com.github.ecsoya.sword.common.core.controller.BaseController;
import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.user.domain.UserCertificate;
import com.github.ecsoya.sword.user.service.IUserCertificateService;
import com.github.ecsoya.sword.utils.SwordUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/user/certificate")
@Api(tags = { "实名认证" }, description = "增、删、改")
public class UserCertificateController extends BaseController {

	@Autowired
	private IUserCertificateService certificateService;

	@ApiOperation(value = "检测是否需要实名验证", notes = "kind: 1-钱包 2-提币 4-购买矿机")
	@GetMapping("/verify")
	public CommonResult<?> verify(Integer kind) {
		return certificateService.checkUserCertificate(SwordUtils.getUserId(), kind);
	}

	@ApiOperation(value = "获取实名验证信息", notes = "type: 0-身份证 1-护照；status: 0-提交 1-失败 2-成功；")
	@GetMapping("/info")
	public CommonResult<UserCertificate> info() {
		return CommonResult.build(certificateService.selectUserCertificateById(SwordUtils.getUserId()));
	}

	@ApiOperation(value = "添加或修改实名验证信息", notes = "type: 0-身份证 1-护照 2-其它；realName: 实名；identityNumber：号码；frontImageUrl：正面照片；backImageUrl：背面照片；holdingImageUrl：手持照片；country：国家\n 三位字母国家码（ARE(\"阿拉伯联合酋长国\", \"United Arab Emirates\"),\n"
			+ "	JOR(\"约旦\", \"Jordan\"),\n" + "	SYR(\"叙利亚\", \"Syria\"),\n" + "	HRV(\"克罗地亚\", \"Croatia\"),\n"
			+ "	BEL(\"比利时\", \"Belgium\"),\n" + "	PAN(\"巴拿马\", \"Panama\"),\n" + "	MLT(\"马耳他\", \"Malta\"),\n"
			+ "	VEN(\"委内瑞拉\", \"Venezuela\"),\n" + "	TWN(\"台湾地区\", \"Taiwan\"),\n" + "	DNK(\"丹麦\", \"Denmark\"),\n"
			+ "	PRI(\"波多黎哥\", \"Puerto Rico\"),\n" + "	VNM(\"越南\", \"Vietnam\"),\n"
			+ "	USA(\"美国\", \"United States\"),\n" + "	MNE(\"黑山\", \"Montenegro\"),\n" + "	SWE(\"瑞典\", \"Sweden\"),\n"
			+ "	BOL(\"玻利维亚\", \"Bolivia\"),\n" + "	SGP(\"新加坡\", \"Singapore\"),\n" + "	BHR(\"巴林\", \"Bahrain\"),\n"
			+ "	SAU(\"沙特阿拉伯\", \"Saudi Arabia\"),\n" + "	YEM(\"也门\", \"Yemen\"),\n" + "	IND(\"印度\", \"India\"),\n"
			+ "	FIN(\"芬兰\", \"Finland\"),\n" + "	BIH(\"波斯尼亚和黑山共和国\", \"Bosnia and Herzegovina\"),\n"
			+ "	UKR(\"乌克兰\", \"Ukraine\"),\n" + "	CHE(\"瑞士\", \"Switzerland\"),\n"
			+ "	ARG(\"阿根廷\", \"Argentina\"),\n" + "	EGY(\"埃及\", \"Egypt\"),\n" + "	JPN(\"日本\", \"Japan\"),\n"
			+ "	SLV(\"萨尔瓦多\", \"El Salvador\"),\n" + "	BRA(\"巴西\", \"Brazil\"),\n" + "	ISL(\"冰岛\", \"Iceland\"),\n"
			+ "	CZE(\"捷克共和国\", \"Czech Republic\"),\n" + "	POL(\"波兰\", \"Poland\"),\n" + "	ESP(\"西班牙\", \"Spain\"),\n"
			+ "	MYS(\"马来西亚\", \"Malaysia\"),\n" + "	COL(\"哥伦比亚\", \"Colombia\"),\n" + "	BGR(\"保加利亚\", \"Bulgaria\"),\n"
			+ "	PRY(\"巴拉圭\", \"Paraguay\"),\n" + "	ECU(\"厄瓜多尔\", \"Ecuador\"),\n" + "	SDN(\"苏丹\", \"Sudan\"),\n"
			+ "	ROU(\"罗马尼亚\", \"Romania\"),\n" + "	PHL(\"菲律宾\", \"Philippines\"),\n"
			+ "	TUN(\"突尼斯\", \"Tunisia\"),\n" + "	GTM(\"危地马拉\", \"Guatemala\"),\n"
			+ "	KOR(\"韩国\", \"South Korea\"),\n" + "	CYP(\"塞浦路斯\", \"Cyprus\"),\n" + "	MEX(\"墨西哥\", \"Mexico\"),\n"
			+ "	RUS(\"俄罗斯\", \"Russia\"),\n" + "	HND(\"洪都拉斯\", \"Honduras\"),\n" + "	HKG(\"香港\", \"Hong Kong\"),\n"
			+ "	NOR(\"挪威\", \"Norway\"),\n" + "	HUN(\"匈牙利\", \"Hungary\"),\n" + "	THA(\"泰国\", \"Thailand\"),\n"
			+ "	IRQ(\"伊拉克\", \"Iraq\"),\n" + "	CHL(\"智利\", \"Chile\"),\n" + "	MAR(\"摩洛哥\", \"Morocco\"),\n"
			+ "	IRL(\"爱尔兰\", \"Ireland\"),\n" + "	TUR(\"土耳其\", \"Turkey\"),\n" + "	EST(\"爱沙尼亚\", \"Estonia\"),\n"
			+ "	QAT(\"卡塔尔\", \"Qatar\"),\n" + "	PRT(\"葡萄牙\", \"Portugal\"),\n" + "	LUX(\"卢森堡\", \"Luxembourg\"),\n"
			+ "	OMN(\"阿曼\", \"Oman\"),\n" + "	ALB(\"阿尔巴尼亚\", \"Albania\"),\n"
			+ "	DOM(\"多米尼加共和国\", \"Dominican Republic\"),\n" + "	CUB(\"古巴\", \"Cuba\"),\n"
			+ "	NZL(\"新西兰\", \"New Zealand\"),\n" + "	SRB(\"塞尔维亚\", \"Serbia\"),\n"
			+ "	URY(\"乌拉圭\", \"Uruguay\"),\n" + "	GRC(\"希腊\", \"Greece\"),\n" + "	ISR(\"以色列\", \"Israel\"),\n"
			+ "	ZAF(\"南非\", \"South Africa\"),\n" + "	FRA(\"法国\", \"France\"),\n" + "	AUT(\"奥地利\", \"Austria\"),\n"
			+ "	AUS(\"澳大利亚\", \"Australia\"),\n" + "	NLD(\"荷兰\", \"Netherlands\"),\n"
			+ "	CAN(\"加拿大\", \"Canada\"),\n" + "	LVA(\"拉脱维亚\", \"Latvia\"),\n"
			+ "	CRI(\"哥斯达黎加\", \"Costa Rica\"),\n" + "	KWT(\"科威特\", \"Kuwait\"),\n" + "	LBY(\"利比亚\", \"Libya\"),\n"
			+ "	DEU(\"德国\", \"Germany\"),\n" + "	DZA(\"阿尔及利亚\", \"Algeria\"),\n" + "	SVK(\"斯洛伐克\", \"Slovakia\"),\n"
			+ "	LTU(\"立陶宛\", \"Lithuania\"),\n" + "	ITA(\"意大利\", \"Italy\"),\n" + "	CHN(\"中国\", \"China\"),\n"
			+ "	LBN(\"黎巴嫩\", \"Lebanon\"),\n" + "	NIC(\"尼加拉瓜\", \"Nicaragua\"),\n"
			+ "	MKD(\"马其顿王国\", \"Macedonia\"),\n" + "	BLR(\"白俄罗斯\", \"Belarus\"),\n"
			+ "	SVN(\"斯洛文尼亚\", \"Slovenia\"),\n" + "	PER(\"秘鲁\", \"Peru\"),\n"
			+ "	IDN(\"印度尼西亚\", \"Indonesia\"),\n" + "	GBR(\"英国\", \"United Kingdom\");）")
	@PostMapping("/edit")
	@RepeatSubmit
	public CommonResult<?> edit(UserCertificate uc) {
		uc.setUserId(SwordUtils.getUserId());
		uc.setStatus(UserCertificate.STATUS_NONE);
		return CommonResult.ajax(certificateService.insertUserCertificate(uc));
	}
}
