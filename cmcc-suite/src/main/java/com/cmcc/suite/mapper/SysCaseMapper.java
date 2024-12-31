package com.cmcc.suite.mapper;


import com.cmcc.common.core.domain.entity.SysCase;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用例信息表(SysCase)表数据库访问层
 *
 * @author makejava
 * @since 2024-12-29 18:34:36
 */
@Mapper
public interface SysCaseMapper {

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
     * 统计总行数
     *
     * @param sysCase 查询条件
     * @return 总行数
     */
    long count(SysCase sysCase);

    /**
     * 新增数据
     *
     * @param sysCase 实例对象
     * @return 影响行数
     */
    int insert(SysCase sysCase);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<SysCase> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<SysCase> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<SysCase> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<SysCase> entities);

    /**
     * 修改数据
     *
     * @param sysCase 实例对象
     * @return 影响行数
     */
    int update(SysCase sysCase);

    /**
     * 通过主键删除数据
     *
     * @param caseId 主键
     * @return 影响行数
     */
    int deleteById(Long caseId);

}
