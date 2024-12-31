package com.cmcc.suite.service;


import com.cmcc.common.core.domain.entity.SysCase;

import java.util.List;

/**
 * 用例信息表(SysCase)表服务接口
 *
 * @author makejava
 * @since 2024-12-29 18:34:36
 */
public interface SysCaseService {

    /**
     * 查询所有数据
     *
     * @return 实例对象集合
     */
    List<SysCase> queryAll(SysCase sysCase);
    
    /**
     * 通过ID查询单条数据
     *
     * @param caseId 主键
     * @return 实例对象
     */
    SysCase queryById(Long caseId);

    /**
     * 新增数据
     *
     * @param sysCase 实例对象
     * @return 实例对象
     */
    SysCase insert(SysCase sysCase);

    /**
     * 修改数据
     *
     * @param sysCase 实例对象
     * @return 实例对象
     */
    SysCase update(SysCase sysCase);

    /**
     * 通过主键删除数据
     *
     * @param caseId 主键
     * @return 是否成功
     */
    boolean deleteById(Long caseId);

}
