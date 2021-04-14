package com.soyatec.sword.article.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soyatec.sword.article.domain.SwordArticle;
import com.soyatec.sword.article.mapper.SwordArticleMapper;
import com.soyatec.sword.article.service.ISwordArticleService;
import com.soyatec.sword.common.core.text.Convert;
import com.soyatec.sword.common.utils.DateUtils;
import com.soyatec.sword.common.utils.IdWorker;

/**
 * 文章Service业务层处理
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-02-04
 */
@Service
public class SwordArticleServiceImpl implements ISwordArticleService {
	@Autowired
	private SwordArticleMapper swordArticleMapper;

	/**
	 * 查询文章
	 *
	 * @param id 文章ID
	 * @return 文章
	 */
	@Override
	public SwordArticle selectSwordArticleById(Long id) {
		return swordArticleMapper.selectSwordArticleById(id);
	}

	/**
	 * 查询文章列表
	 *
	 * @param swordArticle 文章
	 * @return 文章
	 */
	@Override
	public List<SwordArticle> selectSwordArticleList(SwordArticle swordArticle) {
		return swordArticleMapper.selectSwordArticleList(swordArticle);
	}

	/**
	 * 新增文章
	 *
	 * @param swordArticle 文章
	 * @return 结果
	 */
	@Override
	public int insertSwordArticle(SwordArticle swordArticle) {
		if (swordArticle.getId() == null) {
			swordArticle.setId(IdWorker.getId());
		}
		if (swordArticle.getCreateTime() == null) {
			swordArticle.setCreateTime(DateUtils.getNowDate());
		}
		return swordArticleMapper.insertSwordArticle(swordArticle);
	}

	/**
	 * 修改文章
	 *
	 * @param swordArticle 文章
	 * @return 结果
	 */
	@Override
	public int updateSwordArticle(SwordArticle swordArticle) {
		swordArticle.setUpdateTime(DateUtils.getNowDate());
		return swordArticleMapper.updateSwordArticle(swordArticle);
	}

	/**
	 * 删除文章对象
	 *
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int deleteSwordArticleByIds(String ids) {
		return swordArticleMapper.deleteSwordArticleByIds(Convert.toStrArray(ids));
	}

	/**
	 * 删除文章信息
	 *
	 * @param id 文章ID
	 * @return 结果
	 */
	@Override
	public int deleteSwordArticleById(Long id) {
		return swordArticleMapper.deleteSwordArticleById(id);
	}

	@Override
	public List<String> selectSwordArticleCategories() {
		final List<String> list = swordArticleMapper.selectSwordArticleCategories();
		final List<String> categories = new ArrayList<>();
		for (final String c : list) {
			final String[] split = c.split("\\|");
			for (final String d : split) {
				if (!categories.contains(d)) {
					categories.add(d);
				}
			}
		}
		// 加到最前
		if (categories.remove("最新")) {
			categories.add(0, "最新");
		}

		// 加到最后
		if (categories.remove("其它")) {
			categories.add("其它");
		}
		return categories;
	}
}
