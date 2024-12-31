package com.cmcc.suite.service.impl;

import com.cmcc.common.annotation.DataScope;
import com.cmcc.common.constant.UserConstants;
import com.cmcc.common.core.domain.TreeSelect;
import com.cmcc.common.core.domain.entity.SysFunc;
import com.cmcc.common.core.domain.entity.SysRole;
import com.cmcc.common.core.domain.entity.SysUser;
import com.cmcc.common.core.text.Convert;
import com.cmcc.common.exception.ServiceException;
import com.cmcc.common.utils.SecurityUtils;
import com.cmcc.common.utils.StringUtils;
import com.cmcc.common.utils.spring.SpringUtils;
import com.cmcc.suite.mapper.SysFuncMapper;
import com.cmcc.suite.service.ISysFuncService;
import com.cmcc.system.mapper.SysRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 部门管理 服务实现
 *
 * @author ruoyi
 */
@Service
public class SysFuncServiceImpl implements ISysFuncService {
    @Autowired
    private SysFuncMapper funcMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    /**
     * 查询部门管理数据
     *
     * @param dept 部门信息
     * @return 部门信息集合
     */
    @Override
    public List<SysFunc> selectFuncList(SysFunc dept) {
        return funcMapper.selectFuncList(dept);
    }

    /**
     * 查询部门树结构信息
     *
     * @param dept 部门信息
     * @return 部门树信息集合
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<TreeSelect> selectFuncTreeList(SysFunc dept) {
        List<SysFunc> funcs = SpringUtils.getAopProxy(this).selectFuncList(dept);
        return buildDeptTreeSelect(funcs);
    }

    /**
     * 构建前端所需要树结构
     *
     * @param funcs 部门列表
     * @return 树结构列表
     */
    @Override
    public List<SysFunc> buildDeptTree(List<SysFunc> funcs) {
        List<SysFunc> returnList = new ArrayList<SysFunc>();
        List<Long> tempList = funcs.stream().map(SysFunc::getFuncId).collect(Collectors.toList());
        for (SysFunc dept : funcs) {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(dept.getParentId())) {
                recursionFn(funcs, dept);
                returnList.add(dept);
            }
        }
        if (returnList.isEmpty()) {
            returnList = funcs;
        }
        return returnList;
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param funcs 部门列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildDeptTreeSelect(List<SysFunc> funcs) {
        List<SysFunc> funcTrees = buildDeptTree(funcs);
        return funcTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 根据角色ID查询部门树信息
     *
     * @param roleId 角色ID
     * @return 选中部门列表
     */
    @Override
    public List<Long> selectFuncListByRoleId(Long roleId) {
        SysRole role = roleMapper.selectRoleById(roleId);
        return funcMapper.selectFuncListByRoleId(roleId, role.isDeptCheckStrictly());
    }

    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    @Override
    public SysFunc selectFuncById(Long deptId) {
        return funcMapper.selectFuncById(deptId);
    }

    /**
     * 根据ID查询所有子部门（正常状态）
     *
     * @param deptId 部门ID
     * @return 子部门数
     */
    @Override
    public int selectNormalChildrenDeptById(Long deptId) {
        return funcMapper.selectNormalChildrenDeptById(deptId);
    }

    /**
     * 是否存在子节点
     *
     * @param deptId 部门ID
     * @return 结果
     */
    @Override
    public boolean hasChildByDeptId(Long deptId) {
        int result = funcMapper.hasChildByDeptId(deptId);
        return result > 0;
    }

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    @Override
    public boolean checkDeptExistUser(Long deptId) {
        int result = funcMapper.checkDeptExistUser(deptId);
        return result > 0;
    }

    /**
     * 校验部门名称是否唯一
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public boolean checkDeptNameUnique(SysFunc dept) {
        Long deptId = StringUtils.isNull(dept.getFuncId()) ? -1L : dept.getFuncId();
        SysFunc info = funcMapper.checkDeptNameUnique(dept.getFuncName(), dept.getParentId());
        if (StringUtils.isNotNull(info) && info.getFuncId().longValue() != deptId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验部门是否有数据权限
     *
     * @param deptId 部门id
     */
    @Override
    public void checkDeptDataScope(Long deptId) {
        if (!SysUser.isAdmin(SecurityUtils.getUserId()) && StringUtils.isNotNull(deptId)) {
            SysFunc dept = new SysFunc();
            dept.setFuncId(deptId);
            List<SysFunc> funcs = SpringUtils.getAopProxy(this).selectFuncList(dept);
            if (StringUtils.isEmpty(funcs)) {
                throw new ServiceException("没有权限访问部门数据！");
            }
        }
    }

    /**
     * 新增保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public int insertDept(SysFunc dept) {
        SysFunc info = funcMapper.selectFuncById(dept.getParentId());
        // 如果父节点不为正常状态,则不允许新增子节点
        if (!UserConstants.DEPT_NORMAL.equals(info.getStatus())) {
            throw new ServiceException("部门停用，不允许新增");
        }
        dept.setAncestors(info.getAncestors() + "," + dept.getParentId());
        return funcMapper.insertDept(dept);
    }

    /**
     * 修改保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public int updateDept(SysFunc dept) {
        SysFunc newParentDept = funcMapper.selectFuncById(dept.getParentId());
        SysFunc oldDept = funcMapper.selectFuncById(dept.getFuncId());
        if (StringUtils.isNotNull(newParentDept) && StringUtils.isNotNull(oldDept)) {
            String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getFuncId();
            String oldAncestors = oldDept.getAncestors();
            dept.setAncestors(newAncestors);
            updateDeptChildren(dept.getFuncId(), newAncestors, oldAncestors);
        }
        int result = funcMapper.updateDept(dept);
        if (UserConstants.DEPT_NORMAL.equals(dept.getStatus()) && StringUtils.isNotEmpty(dept.getAncestors())
                && !StringUtils.equals("0", dept.getAncestors())) {
            // 如果该部门是启用状态，则启用该部门的所有上级部门
            updateParentDeptStatusNormal(dept);
        }
        return result;
    }

    /**
     * 修改该部门的父级部门状态
     *
     * @param dept 当前部门
     */
    private void updateParentDeptStatusNormal(SysFunc dept) {
        String ancestors = dept.getAncestors();
        Long[] deptIds = Convert.toLongArray(ancestors);
        funcMapper.updateDeptStatusNormal(deptIds);
    }

    /**
     * 修改子元素关系
     *
     * @param deptId       被修改的部门ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    public void updateDeptChildren(Long deptId, String newAncestors, String oldAncestors) {
        List<SysFunc> children = funcMapper.selectChildrenDeptById(deptId);
        for (SysFunc child : children) {
            child.setAncestors(child.getAncestors().replaceFirst(oldAncestors, newAncestors));
        }
        if (children.size() > 0) {
            funcMapper.updateDeptChildren(children);
        }
    }

    /**
     * 删除部门管理信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    @Override
    public int deleteDeptById(Long deptId) {
        return funcMapper.deleteDeptById(deptId);
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<SysFunc> list, SysFunc t) {
        // 得到子节点列表
        List<SysFunc> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysFunc tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysFunc> getChildList(List<SysFunc> list, SysFunc t) {
        List<SysFunc> tlist = new ArrayList<SysFunc>();
        Iterator<SysFunc> it = list.iterator();
        while (it.hasNext()) {
            SysFunc n = (SysFunc) it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getFuncId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysFunc> list, SysFunc t) {
        return getChildList(list, t).size() > 0;
    }
}
