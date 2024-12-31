package com.cmcc.common.core.domain.entity;


import com.cmcc.common.core.domain.BaseEntity;

/**
 * 用例信息表(SysCase)实体类
 *
 * @author makejava
 * @since 2024-12-29 18:34:36
 */

public class SysCase extends BaseEntity {
    private static final long serialVersionUID = 632534622917379303L;
    /**
     * 用例ID
     */
    private Long caseId;
    /**
     * 用例文件夹ID
     */
    private Long funcId;
    /**
     * 用例账号
     */
    private String caseName;
    /**
     * 用例类型（sql）
     */
    private String caseType;
    /**
     * 用例绝对地址
     */
    private String path;
    /**
     * 用例状态（0正常 1停用）
     */
    private String status;
    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;


    public Long getCaseId() {
        return caseId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    public Long getFuncId() {
        return funcId;
    }

    public void setFuncId(Long funcId) {
        this.funcId = funcId;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

}
