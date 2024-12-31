package com.cmcc.suite.service.impl;

import com.cmcc.common.core.domain.entity.SysCase;
import com.cmcc.suite.mapper.SysCaseMapper;
import com.cmcc.suite.service.SysCaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用例信息表(SysCase)表服务实现类
 *
 * @author makejava
 * @since 2024-12-29 18:34:36
 */
@Service("sysCaseService")
public class SysCaseServiceImpl implements SysCaseService {
    @Resource
    private SysCaseMapper sysCaseMapper;

    /**
     * 查询所有数据
     *
     * @return 实例对象集合
     */
    @Override
    public List<SysCase> queryAll(SysCase sysCase) {
        return this.sysCaseMapper.queryAll(sysCase);
    }
    
    /**
     * 通过ID查询单条数据
     *
     * @param caseId 主键
     * @return 实例对象
     */
    @Override
    public SysCase queryById(Long caseId) {
        return this.sysCaseMapper.queryById(caseId);
    }

    /**
     * 新增数据
     *
     * @param sysCase 实例对象
     * @return 实例对象
     */
    @Override
    public SysCase insert(SysCase sysCase) {
        this.sysCaseMapper.insert(sysCase);
        return sysCase;
    }

    /**
     * 修改数据
     *
     * @param sysCase 实例对象
     * @return 实例对象
     */
    @Override
    public SysCase update(SysCase sysCase) {
        this.sysCaseMapper.update(sysCase);
        return this.queryById(sysCase.getCaseId());
    }

    /**
     * 通过主键删除数据
     *
     * @param caseId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long caseId) {
        return this.sysCaseMapper.deleteById(caseId) > 0;
    }
}
