package com.cmcc.common.core.domain;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import com.cmcc.common.core.domain.entity.SysFunc;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.cmcc.common.constant.UserConstants;
import com.cmcc.common.core.domain.entity.SysDept;
import com.cmcc.common.core.domain.entity.SysMenu;
import com.cmcc.common.utils.StringUtils;

/**
 * Treeselect树结构实体类
 */
public class TreeSelect implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 节点ID
     */
    private Long id;

    /**
     * 节点名称
     */
    private String label;

    /**
     * 节点禁用
     */
    private boolean disabled = false;

    /**
     * 子节点
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeSelect> children;

    public TreeSelect() {

    }

    public TreeSelect(SysFunc func) {
        this.id = func.getFuncId();
        this.label = func.getFuncName();
        this.disabled = StringUtils.equals(UserConstants.DEPT_DISABLE, func.getStatus());
        this.children = func.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }


    public TreeSelect(SysDept dept) {
        this.id = dept.getDeptId();
        this.label = dept.getDeptName();
        this.disabled = StringUtils.equals(UserConstants.DEPT_DISABLE, dept.getStatus());
        this.children = dept.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    public TreeSelect(SysMenu menu) {
        this.id = menu.getMenuId();
        this.label = menu.getMenuName();
        this.children = menu.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public List<TreeSelect> getChildren() {
        return children;
    }

    public void setChildren(List<TreeSelect> children) {
        this.children = children;
    }
}
