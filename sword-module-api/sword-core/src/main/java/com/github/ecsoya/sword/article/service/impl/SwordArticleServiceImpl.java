package com.github.ecsoya.sword.article.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.article.domain.SwordArticle;
import com.github.ecsoya.sword.article.mapper.SwordArticleMapper;
import com.github.ecsoya.sword.article.service.ISwordArticleService;
import com.github.ecsoya.sword.common.core.text.Convert;
import com.github.ecsoya.sword.common.utils.DateUtils;
import com.github.ecsoya.sword.common.utils.IdWorker;

/**
 * The Class SwordArticleServiceImpl.
 */
@Service
public class SwordArticleServiceImpl implements ISwordArticleService {

	/** The sword article mapper. */
	@Autowired
	private SwordArticleMapper swordArticleMapper;

	/**
	 * Select sword article by id.
	 *
	 * @param id the id
	 * @return the sword article
	 */
	@Override
	public SwordArticle selectSwordArticleById(Long id) {
		return swordArticleMapper.selectSwordArticleById(id);
	}

	/**
	 * Select sword article list.
	 *
	 * @param swordArticle the sword article
	 * @return the list
	 */
	@Override
	public List<SwordArticle> selectSwordArticleList(SwordArticle swordArticle) {
		return swordArticleMapper.selectSwordArticleList(swordArticle);
	}

	/**
	 * Insert sword article.
	 *
	 * @param swordArticle the sword article
	 * @return the int
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
	 * Update sword article.
	 *
	 * @param swordArticle the sword article
	 * @return the int
	 */
	@Override
	public int updateSwordArticle(SwordArticle swordArticle) {
		swordArticle.setUpdateTime(DateUtils.getNowDate());
		return swordArticleMapper.updateSwordArticle(swordArticle);
	}

	/**
	 * Delete sword article by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	@Override
	public int deleteSwordArticleByIds(String ids) {
		return swordArticleMapper.deleteSwordArticleByIds(Convert.toStrArray(ids));
	}

	/**
	 * Delete sword article by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	@Override
	public int deleteSwordArticleById(Long id) {
		return swordArticleMapper.deleteSwordArticleById(id);
	}

	/**
	 * Select sword article categories.
	 *
	 * @return the list
	 */
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
