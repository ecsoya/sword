package com.github.ecsoya.sword.system.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.ecsoya.sword.common.annotation.DataScope;
import com.github.ecsoya.sword.common.constant.UserConstants;
import com.github.ecsoya.sword.common.core.domain.Ztree;
import com.github.ecsoya.sword.common.core.domain.entity.SysDept;
import com.github.ecsoya.sword.common.core.domain.entity.SysRole;
import com.github.ecsoya.sword.common.exception.BusinessException;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.system.mapper.SysDeptMapper;
import com.github.ecsoya.sword.system.service.ISysDeptService;

/**
 * The Class SysDeptServiceImpl.
 */
@Service
public class SysDeptServiceImpl implements ISysDeptService {

	/** The dept mapper. */
	@Autowired
	private SysDeptMapper deptMapper;

	/**
	 * Select dept list.
	 *
	 * @param dept the dept
	 * @return the list
	 */
	@Override
	@DataScope(deptAlias = "d")
	public List<SysDept> selectDeptList(SysDept dept) {
		return deptMapper.selectDeptList(dept);
	}

	/**
	 * Select dept tree.
	 *
	 * @param dept the dept
	 * @return the list
	 */
	@Override
	@DataScope(deptAlias = "d")
	public List<Ztree> selectDeptTree(SysDept dept) {
		final List<SysDept> deptList = deptMapper.selectDeptList(dept);
		final List<Ztree> ztrees = initZtree(deptList);
		return ztrees;
	}

	/**
	 * Select dept tree exclude child.
	 *
	 * @param dept the dept
	 * @return the list
	 */
	@Override
	@DataScope(deptAlias = "d")
	public List<Ztree> selectDeptTreeExcludeChild(SysDept dept) {
		final Long deptId = dept.getDeptId();
		final List<SysDept> deptList = deptMapper.selectDeptList(dept);
		final Iterator<SysDept> it = deptList.iterator();
		while (it.hasNext()) {
			final SysDept d = it.next();
			if (d.getDeptId().intValue() == deptId || ArrayUtils
					.contains(org.apache.commons.lang3.StringUtils.split(d.getAncestors(), ","), deptId + "")) {
				it.remove();
			}
		}
		final List<Ztree> ztrees = initZtree(deptList);
		return ztrees;
	}

	/**
	 * Role dept tree data.
	 *
	 * @param role the role
	 * @return the list
	 */
	@Override
	public List<Ztree> roleDeptTreeData(SysRole role) {
		final Long roleId = role.getRoleId();
		List<Ztree> ztrees = new ArrayList<Ztree>();
		final List<SysDept> deptList = selectDeptList(new SysDept());
		if (StringUtils.isNotNull(roleId)) {
			final List<String> roleDeptList = deptMapper.selectRoleDeptTree(roleId);
			ztrees = initZtree(deptList, roleDeptList);
		} else {
			ztrees = initZtree(deptList);
		}
		return ztrees;
	}

	/**
	 * Inits the ztree.
	 *
	 * @param deptList the dept list
	 * @return the list
	 */
	public List<Ztree> initZtree(List<SysDept> deptList) {
		return initZtree(deptList, null);
	}

	/**
	 * Inits the ztree.
	 *
	 * @param deptList     the dept list
	 * @param roleDeptList the role dept list
	 * @return the list
	 */
	public List<Ztree> initZtree(List<SysDept> deptList, List<String> roleDeptList) {

		final List<Ztree> ztrees = new ArrayList<Ztree>();
		final boolean isCheck = StringUtils.isNotNull(roleDeptList);
		for (final SysDept dept : deptList) {
			if (UserConstants.DEPT_NORMAL.equals(dept.getStatus())) {
				final Ztree ztree = new Ztree();
				ztree.setId(dept.getDeptId());
				ztree.setpId(dept.getParentId());
				ztree.setName(dept.getDeptName());
				ztree.setTitle(dept.getDeptName());
				if (isCheck) {
					ztree.setChecked(roleDeptList.contains(dept.getDeptId() + dept.getDeptName()));
				}
				ztrees.add(ztree);
			}
		}
		return ztrees;
	}

	/**
	 * Select dept count.
	 *
	 * @param parentId the parent id
	 * @return the int
	 */
	@Override
	public int selectDeptCount(Long parentId) {
		final SysDept dept = new SysDept();
		dept.setParentId(parentId);
		return deptMapper.selectDeptCount(dept);
	}

	/**
	 * Check dept exist user.
	 *
	 * @param deptId the dept id
	 * @return true, if successful
	 */
	@Override
	public boolean checkDeptExistUser(Long deptId) {
		final int result = deptMapper.checkDeptExistUser(deptId);
		return result > 0 ? true : false;
	}

	/**
	 * Delete dept by id.
	 *
	 * @param deptId the dept id
	 * @return the int
	 */
	@Override
	public int deleteDeptById(Long deptId) {
		return deptMapper.deleteDeptById(deptId);
	}

	/**
	 * Insert dept.
	 *
	 * @param dept the dept
	 * @return the int
	 */
	@Override
	public int insertDept(SysDept dept) {
		final SysDept info = deptMapper.selectDeptById(dept.getParentId());
		// 如果父节点不为"正常"状态,则不允许新增子节点
		if (!UserConstants.DEPT_NORMAL.equals(info.getStatus())) {
			throw new BusinessException("部门停用，不允许新增");
		}
		dept.setAncestors(info.getAncestors() + "," + dept.getParentId());
		return deptMapper.insertDept(dept);
	}

	/**
	 * Update dept.
	 *
	 * @param dept the dept
	 * @return the int
	 */
	@Override
	@Transactional
	public int updateDept(SysDept dept) {
		final SysDept newParentDept = deptMapper.selectDeptById(dept.getParentId());
		final SysDept oldDept = selectDeptById(dept.getDeptId());
		if (StringUtils.isNotNull(newParentDept) && StringUtils.isNotNull(oldDept)) {
			final String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getDeptId();
			final String oldAncestors = oldDept.getAncestors();
			dept.setAncestors(newAncestors);
			updateDeptChildren(dept.getDeptId(), newAncestors, oldAncestors);
		}
		final int result = deptMapper.updateDept(dept);
		if (UserConstants.DEPT_NORMAL.equals(dept.getStatus())) {
			// 如果该部门是启用状态，则启用该部门的所有上级部门
			updateParentDeptStatus(dept);
		}
		return result;
	}

	/**
	 * Update parent dept status.
	 *
	 * @param dept the dept
	 */
	private void updateParentDeptStatus(SysDept dept) {
		final String updateBy = dept.getUpdateBy();
		dept = deptMapper.selectDeptById(dept.getDeptId());
		dept.setUpdateBy(updateBy);
		deptMapper.updateDeptStatus(dept);
	}

	/**
	 * Update dept children.
	 *
	 * @param deptId       the dept id
	 * @param newAncestors the new ancestors
	 * @param oldAncestors the old ancestors
	 */
	public void updateDeptChildren(Long deptId, String newAncestors, String oldAncestors) {
		final List<SysDept> children = deptMapper.selectChildrenDeptById(deptId);
		for (final SysDept child : children) {
			child.setAncestors(child.getAncestors().replaceFirst(oldAncestors, newAncestors));
		}
		if (children.size() > 0) {
			deptMapper.updateDeptChildren(children);
		}
	}

	/**
	 * Select dept by id.
	 *
	 * @param deptId the dept id
	 * @return the sys dept
	 */
	@Override
	public SysDept selectDeptById(Long deptId) {
		return deptMapper.selectDeptById(deptId);
	}

	/**
	 * Select normal children dept by id.
	 *
	 * @param deptId the dept id
	 * @return the int
	 */
	@Override
	public int selectNormalChildrenDeptById(Long deptId) {
		return deptMapper.selectNormalChildrenDeptById(deptId);
	}

	/**
	 * Check dept name unique.
	 *
	 * @param dept the dept
	 * @return the string
	 */
	@Override
	public String checkDeptNameUnique(SysDept dept) {
		final Long deptId = StringUtils.isNull(dept.getDeptId()) ? -1L : dept.getDeptId();
		final SysDept info = deptMapper.checkDeptNameUnique(dept.getDeptName(), dept.getParentId());
		if (StringUtils.isNotNull(info) && info.getDeptId().longValue() != deptId.longValue()) {
			return UserConstants.DEPT_NAME_NOT_UNIQUE;
		}
		return UserConstants.DEPT_NAME_UNIQUE;
	}
}
