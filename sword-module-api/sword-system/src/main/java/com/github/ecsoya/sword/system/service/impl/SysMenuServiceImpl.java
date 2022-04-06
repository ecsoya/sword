package com.github.ecsoya.sword.system.service.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.common.constant.UserConstants;
import com.github.ecsoya.sword.common.core.domain.Ztree;
import com.github.ecsoya.sword.common.core.domain.entity.SysMenu;
import com.github.ecsoya.sword.common.core.domain.entity.SysRole;
import com.github.ecsoya.sword.common.core.domain.entity.SysUser;
import com.github.ecsoya.sword.common.core.text.Convert;
import com.github.ecsoya.sword.common.utils.DateUtils;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.system.mapper.SysMenuMapper;
import com.github.ecsoya.sword.system.mapper.SysRoleMenuMapper;
import com.github.ecsoya.sword.system.service.ISysMenuService;

/**
 * The Class SysMenuServiceImpl.
 */
@Service
public class SysMenuServiceImpl implements ISysMenuService {

	/** The Constant PREMISSION_STRING. */
	public static final String PREMISSION_STRING = "perms[\"{0}\"]";

	/** The menu mapper. */
	@Autowired
	private SysMenuMapper menuMapper;

	/** The role menu mapper. */
	@Autowired
	private SysRoleMenuMapper roleMenuMapper;

	/**
	 * Select menus by user.
	 *
	 * @param user the user
	 * @return the list
	 */
	@Override
	public List<SysMenu> selectMenusByUser(SysUser user) {
		List<SysMenu> menus = new LinkedList<SysMenu>();
		// 管理员显示所有菜单信息
		if (user.isAdmin()) {
			menus = menuMapper.selectMenuNormalAll();
		} else {
			menus = menuMapper.selectMenusByUserId(user.getUserId());
		}
		return getChildPerms(menus, 0);
	}

	/**
	 * Select menu list.
	 *
	 * @param menu   the menu
	 * @param userId the user id
	 * @return the list
	 */
	@Override
	public List<SysMenu> selectMenuList(SysMenu menu, Long userId) {
		List<SysMenu> menuList = null;
		if (SysUser.isAdmin(userId)) {
			menuList = menuMapper.selectMenuList(menu);
		} else {
			menu.getParams().put("userId", userId);
			menuList = menuMapper.selectMenuListByUserId(menu);
		}
		return menuList;
	}

	/**
	 * Select menu all.
	 *
	 * @param userId the user id
	 * @return the list
	 */
	@Override
	public List<SysMenu> selectMenuAll(Long userId) {
		List<SysMenu> menuList = null;
		if (SysUser.isAdmin(userId)) {
			menuList = menuMapper.selectMenuAll();
		} else {
			menuList = menuMapper.selectMenuAllByUserId(userId);
		}
		return menuList;
	}

	/**
	 * Select perms by user id.
	 *
	 * @param userId the user id
	 * @return the sets the
	 */
	@Override
	public Set<String> selectPermsByUserId(Long userId) {
		final List<String> perms = menuMapper.selectPermsByUserId(userId);
		final Set<String> permsSet = new HashSet<>();
		for (final String perm : perms) {
			if (StringUtils.isNotEmpty(perm)) {
				permsSet.addAll(Arrays.asList(perm.trim().split(",")));
			}
		}
		return permsSet;
	}

	/**
	 * Role menu tree data.
	 *
	 * @param role   the role
	 * @param userId the user id
	 * @return the list
	 */
	@Override
	public List<Ztree> roleMenuTreeData(SysRole role, Long userId) {
		final Long roleId = role.getRoleId();
		List<Ztree> ztrees = new ArrayList<Ztree>();
		final List<SysMenu> menuList = selectMenuAll(userId);
		if (StringUtils.isNotNull(roleId)) {
			final List<String> roleMenuList = menuMapper.selectMenuTree(roleId);
			ztrees = initZtree(menuList, roleMenuList, false);
		} else {
			ztrees = initZtree(menuList, null, false);
		}
		return ztrees;
	}

	/**
	 * Menu tree data.
	 *
	 * @param userId the user id
	 * @return the list
	 */
	@Override
	public List<Ztree> menuTreeData(Long userId) {
		final List<SysMenu> menuList = selectMenuAll(userId);
		final List<Ztree> ztrees = initZtree(menuList);
		return ztrees;
	}

	/**
	 * Select perms all.
	 *
	 * @param userId the user id
	 * @return the linked hash map
	 */
	@Override
	public LinkedHashMap<String, String> selectPermsAll(Long userId) {
		final LinkedHashMap<String, String> section = new LinkedHashMap<>();
		final List<SysMenu> permissions = selectMenuAll(userId);
		if (StringUtils.isNotEmpty(permissions)) {
			for (final SysMenu menu : permissions) {
				section.put(menu.getUrl(), MessageFormat.format(PREMISSION_STRING, menu.getPerms()));
			}
		}
		return section;
	}

	/**
	 * Inits the ztree.
	 *
	 * @param menuList the menu list
	 * @return the list
	 */
	public List<Ztree> initZtree(List<SysMenu> menuList) {
		return initZtree(menuList, null, false);
	}

	/**
	 * Inits the ztree.
	 *
	 * @param menuList     the menu list
	 * @param roleMenuList the role menu list
	 * @param permsFlag    the perms flag
	 * @return the list
	 */
	public List<Ztree> initZtree(List<SysMenu> menuList, List<String> roleMenuList, boolean permsFlag) {
		final List<Ztree> ztrees = new ArrayList<Ztree>();
		final boolean isCheck = StringUtils.isNotNull(roleMenuList);
		for (final SysMenu menu : menuList) {
			final Ztree ztree = new Ztree();
			ztree.setId(menu.getMenuId());
			ztree.setpId(menu.getParentId());
			ztree.setName(transMenuName(menu, permsFlag));
			ztree.setTitle(menu.getMenuName());
			if (isCheck) {
				ztree.setChecked(roleMenuList.contains(menu.getMenuId() + menu.getPerms()));
			}
			ztrees.add(ztree);
		}
		return ztrees;
	}

	/**
	 * Trans menu name.
	 *
	 * @param menu      the menu
	 * @param permsFlag the perms flag
	 * @return the string
	 */
	public String transMenuName(SysMenu menu, boolean permsFlag) {
		final StringBuffer sb = new StringBuffer();
		sb.append(menu.getMenuName());
		if (permsFlag) {
			sb.append("<font color=\"#888\">&nbsp;&nbsp;&nbsp;" + menu.getPerms() + "</font>");
		}
		return sb.toString();
	}

	/**
	 * Delete menu by id.
	 *
	 * @param menuId the menu id
	 * @return the int
	 */
	@Override
	public int deleteMenuById(Long menuId) {
		return menuMapper.deleteMenuById(menuId);
	}

	/**
	 * Select menu by id.
	 *
	 * @param menuId the menu id
	 * @return the sys menu
	 */
	@Override
	public SysMenu selectMenuById(Long menuId) {
		return menuMapper.selectMenuById(menuId);
	}

	/**
	 * Select count menu by parent id.
	 *
	 * @param parentId the parent id
	 * @return the int
	 */
	@Override
	public int selectCountMenuByParentId(Long parentId) {
		return menuMapper.selectCountMenuByParentId(parentId);
	}

	/**
	 * Select count role menu by menu id.
	 *
	 * @param menuId the menu id
	 * @return the int
	 */
	@Override
	public int selectCountRoleMenuByMenuId(Long menuId) {
		return roleMenuMapper.selectCountRoleMenuByMenuId(menuId);
	}

	/**
	 * Insert menu.
	 *
	 * @param menu the menu
	 * @return the int
	 */
	@Override
	public int insertMenu(SysMenu menu) {
		final int rows = menuMapper.insertMenu(menu);
		if (rows > 0 && "C".equals(menu.getMenuType())) {
			final String buttons = menu.getButtons();
			if (!StringUtils.isEmpty(buttons)) {
				final Long[] longArray = Convert.toLongArray(buttons);
				for (final Long type : longArray) {
					addButton(menu.getMenuId(), menu.getPerms(), type);
				}
			}
		}
		return rows;
	}

	/**
	 * Adds the button.
	 *
	 * @param parentId    the parent id
	 * @param parentPerms the parent perms
	 * @param type        the type
	 */
	private void addButton(Long parentId, String parentPerms, Long type) {
		if (parentId == null || type == null || StringUtils.isEmpty(parentPerms) || parentPerms.indexOf(":") == 0) {
			return;
		}
		final String prefix = parentPerms.substring(0, parentPerms.lastIndexOf(":"));
		final SysMenu menu = new SysMenu();
		String menuName = null;
		final String icon = "#";
		String perms = null;
		switch (type.intValue()) {
		case 1:
			menuName = "添加";
			perms = prefix + ":add";
			break;
		case 2:
			menuName = "删除";
			perms = prefix + ":remove";
			break;
		case 3:
			menuName = "修改";
			perms = prefix + ":edit";
			break;
		case 4:
			menuName = "查询";
			perms = prefix + ":list";
			break;
		case 5:
			menuName = "导入";
			perms = prefix + ":import";
			break;
		case 6:
			menuName = "导出";
			perms = prefix + ":export";
			break;

		default:
			break;
		}
		menu.setMenuName(menuName);
		menu.setIcon(icon);
		menu.setParentId(parentId);
		menu.setPerms(perms);
		menu.setMenuType("F");
		menu.setUrl("#");
		menu.setOrderNum(type.toString());
		menu.setTarget("menuItem");
		menu.setVisible("0");
		menu.setCreateBy("develop");
		menu.setCreateTime(DateUtils.getNowDate());
		menuMapper.insertMenu(menu);
	}

	/**
	 * Update menu.
	 *
	 * @param menu the menu
	 * @return the int
	 */
	@Override
	public int updateMenu(SysMenu menu) {
		return menuMapper.updateMenu(menu);
	}

	/**
	 * Check menu name unique.
	 *
	 * @param menu the menu
	 * @return the string
	 */
	@Override
	public String checkMenuNameUnique(SysMenu menu) {
		final Long menuId = StringUtils.isNull(menu.getMenuId()) ? -1L : menu.getMenuId();
		final SysMenu info = menuMapper.checkMenuNameUnique(menu.getMenuName(), menu.getParentId());
		if (StringUtils.isNotNull(info) && info.getMenuId().longValue() != menuId.longValue()) {
			return UserConstants.MENU_NAME_NOT_UNIQUE;
		}
		return UserConstants.MENU_NAME_UNIQUE;
	}

	/**
	 * Gets the child perms.
	 *
	 * @param list     the list
	 * @param parentId the parent id
	 * @return the child perms
	 */
	public List<SysMenu> getChildPerms(List<SysMenu> list, int parentId) {
		final List<SysMenu> returnList = new ArrayList<SysMenu>();
		for (final Iterator<SysMenu> iterator = list.iterator(); iterator.hasNext();) {
			final SysMenu t = iterator.next();
			// 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
			if (t.getParentId() == parentId) {
				recursionFn(list, t);
				returnList.add(t);
			}
		}
		return returnList;
	}

	/**
	 * Recursion fn.
	 *
	 * @param list the list
	 * @param t    the t
	 */
	private void recursionFn(List<SysMenu> list, SysMenu t) {
		// 得到子节点列表
		final List<SysMenu> childList = getChildList(list, t);
		t.setChildren(childList);
		for (final SysMenu tChild : childList) {
			if (hasChild(list, tChild)) {
				recursionFn(list, tChild);
			}
		}
	}

	/**
	 * Gets the child list.
	 *
	 * @param list the list
	 * @param t    the t
	 * @return the child list
	 */
	private List<SysMenu> getChildList(List<SysMenu> list, SysMenu t) {
		final List<SysMenu> tlist = new ArrayList<SysMenu>();
		final Iterator<SysMenu> it = list.iterator();
		while (it.hasNext()) {
			final SysMenu n = it.next();
			if (n.getParentId().longValue() == t.getMenuId().longValue()) {
				tlist.add(n);
			}
		}
		return tlist;
	}

	/**
	 * Checks for child.
	 *
	 * @param list the list
	 * @param t    the t
	 * @return true, if successful
	 */
	private boolean hasChild(List<SysMenu> list, SysMenu t) {
		return getChildList(list, t).size() > 0 ? true : false;
	}

}
