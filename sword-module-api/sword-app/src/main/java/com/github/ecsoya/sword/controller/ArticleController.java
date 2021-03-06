package com.github.ecsoya.sword.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.ecsoya.sword.article.domain.SwordArticle;
import com.github.ecsoya.sword.article.domain.UserArticle;
import com.github.ecsoya.sword.article.service.ISwordArticleService;
import com.github.ecsoya.sword.article.service.IUserArticleService;
import com.github.ecsoya.sword.common.annotation.RepeatSubmit;
import com.github.ecsoya.sword.common.core.controller.BaseController;
import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.common.core.page.TableDataInfo;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.common.utils.async.AsyncManager;
import com.github.ecsoya.sword.utils.SwordUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * The Class ArticleController.
 */
@RestController
@RequestMapping("/article")
@Api(tags = { "文章" }, description = "查询、详情")
public class ArticleController extends BaseController {

	/** The article service. */
	@Autowired
	private ISwordArticleService articleService;

	/** The user article service. */
	@Autowired
	private IUserArticleService userArticleService;

	/**
	 * List.
	 *
	 * @param pageSize the page size
	 * @param pageNum  the page num
	 * @param category the category
	 * @return the table data info
	 */
	@ApiOperation(value = "查询消息列表（支持分页）", notes = "{\n" + "            \"createTime\": \"2021-02-04 18:23:36\",\n"
			+ "            \"updateTime\": null,\n" + "            \"remark\": \"\",\n"
			+ "            \"params\": {},\n" + "            \"id\": \"1\",\n"
			+ "            \"title\": \"这是一篇文章的标题，最多256个字符\",                                                           //文章标题\n"
			+ "            \"description\": \"这个文章内容的简介，最多支持512个字符。\",                                                //文章简介\n"
			+ "            \"imageUrl\": \"https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/02/04/IMG 0040.jpg\", //文章主图\n"
			+ "            \"content\": null,                                                                                     //文章正文（列表时不查询，详情时查询）\n"
			+ "            \"status\": \"1\",\n"
			+ "            \"reads\": null,                                                                                       //阅读量\n"
			+ "            \"likes\": null                                                                                        //点赞量\n"
			+ "        }")
	@GetMapping("/list")
	public TableDataInfo list(@ApiParam("每页显示多少条") Integer pageSize, @ApiParam("当前页数，第几页") Integer pageNum,
			String category) {
		startPage("create_time desc");
		final SwordArticle query = new SwordArticle();
		query.setStatus(SwordArticle.STATUS_PUBLISH);
		query.setCategory(category);
		final List<SwordArticle> list = articleService.selectSwordArticleList(query);
		return getDataTable(list);
	}

	/**
	 * Categories.
	 *
	 * @return the common result
	 */
	@ApiOperation(value = "查询分类")
	@GetMapping("/categories")
	public CommonResult<List<String>> categories() {
		return CommonResult.build(articleService.selectSwordArticleCategories());
	}

	/**
	 * Detail.
	 *
	 * @param id the id
	 * @return the common result
	 */
	@ApiOperation(value = "查询消息详情", notes = "id: 文章Id，\ncontent：为富文本内容")
	@GetMapping("/detail")
	public CommonResult<SwordArticle> detail(Long id) {
		final CommonResult<SwordArticle> build = CommonResult.build(articleService.selectSwordArticleById(id));
		if (build.isSuccess()) {
			final Long userId = SwordUtils.getUserId();
			AsyncManager.me().execute(new Runnable() {

				@Override
				public void run() {
					final UserArticle article = userArticleService.selectUserArticleByArticleId(id, userId);
					article.setRead(UserArticle.READ_YES);
					userArticleService.updateUserArticle(article);
				}
			});
		}
		return build;
	}

	/**
	 * User.
	 *
	 * @param id the id
	 * @return the common result
	 */
	@ApiOperation(value = "查询用户的点赞和回复", notes = "id: 文章Id，\nlike：0-未点赞，1-已点赞，\ncomment：为评论")
	@GetMapping("/user")
	public CommonResult<UserArticle> user(Long id) {
		return CommonResult.build(userArticleService.selectUserArticleByArticleId(id, SwordUtils.getUserId()));
	}

	/**
	 * Like.
	 *
	 * @param id the id
	 * @return the common result
	 */
	@ApiOperation(value = "点赞", notes = "id: 文章Id")
	@PostMapping("/like")
	@RepeatSubmit
	public CommonResult<?> like(Long id) {
		if (id == null) {
			return CommonResult.fail("参数错误");
		}
		final UserArticle userArticle = userArticleService.selectUserArticleByArticleId(id, SwordUtils.getUserId());
		userArticle.setLike(UserArticle.LIKE_YES);
		return CommonResult.ajax(userArticleService.updateUserArticle(userArticle));
	}

	/**
	 * Unlike.
	 *
	 * @param id the id
	 * @return the common result
	 */
	@ApiOperation(value = "取消点赞", notes = "id: 文章Id")
	@PostMapping("/unlike")
	@RepeatSubmit
	public CommonResult<?> unlike(Long id) {
		if (id == null) {
			return CommonResult.fail("参数错误");
		}
		final UserArticle userArticle = userArticleService.selectUserArticleByArticleId(id, SwordUtils.getUserId());
		userArticle.setLike(UserArticle.LIKE_NO);
		return CommonResult.ajax(userArticleService.updateUserArticle(userArticle));
	}

	/**
	 * Comment.
	 *
	 * @param id      the id
	 * @param comment the comment
	 * @return the common result
	 */
	@ApiOperation(value = "添加评论", notes = "id: 文章Id，\ncomment：评论内容，当前每篇文章每人仅能发表一次评论")
	@PostMapping("/comment/add")
	@RepeatSubmit
	public CommonResult<?> comment(Long id, String comment) {
		if (id == null) {
			return CommonResult.fail("参数错误");
		}
		if (StringUtils.isEmpty(comment)) {
			return CommonResult.fail("回复为空");
		}
		final UserArticle userArticle = userArticleService.selectUserArticleByArticleId(id, SwordUtils.getUserId());
		userArticle.setComment(comment);
		return CommonResult.ajax(userArticleService.updateUserArticle(userArticle));
	}

	/**
	 * Comment list.
	 *
	 * @param pageSize the page size
	 * @param pageNum  the page num
	 * @param id       the id
	 * @return the table data info
	 */
	@ApiOperation(value = "评论列表", notes = "查询变量id: 文章Id；\n返回结果：\n{\n"
			+ "            \"createTime\": \"2021-02-05 09:02:08\",\n"
			+ "            \"updateTime\": \"2021-02-05 09:02:08\",\n"
			+ "            \"avatar\": \"\",                                        //用户头像\n"
			+ "            \"loginName\": \"MF222\",                                // 用户登录名\n"
			+ "            \"articleId\": \"2\",\n" + "            \"userId\": \"1174\",\n"
			+ "            \"comment\": \"Not Bad\"                                 //用户的评论\n" + "        }")
	@GetMapping("/comment/list")
	public TableDataInfo commentList(@ApiParam("每页显示多少条") Integer pageSize, @ApiParam("当前页数，第几页") Integer pageNum,
			Long id) {
		startPage();
		return getDataTable(userArticleService.selectUserArticleCommentList(id));
	}
}
