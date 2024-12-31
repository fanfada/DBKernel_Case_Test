package com.cmcc.web.controller.suite;

import com.cmcc.common.core.controller.BaseController;
import com.cmcc.common.core.domain.AjaxResult;
import com.cmcc.common.core.domain.entity.SysCase;
import com.cmcc.common.core.domain.entity.SysFunc;
import com.cmcc.common.core.page.TableDataInfo;
import com.cmcc.suite.service.ISysFuncService;
import com.cmcc.suite.service.SysCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * 测试用例按模块功分 suite套件
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/suite/casepack")
public class SysCasePackController extends BaseController {

    @Autowired
    private ISysFuncService funcService;

    @Resource
    private SysCaseService sysCaseService;

    /**
     * 获取用例文件夹列表
     */
    @PreAuthorize("@ss.hasPermi('suite:testcase:list')")
    @GetMapping("/funcTree")
    public AjaxResult funcTree(@RequestBody(required = false) SysFunc dept) {
        return success(funcService.selectFuncTreeList(dept));
    }

    /**
     * 查询所有数据
     *
     * @return 实例对象集合
     */
    @PreAuthorize("@ss.hasPermi('suite:testcase:list')")
    @GetMapping(value = "/list")
    public TableDataInfo queryAll(SysCase sysCase) {
        startPage();
        List<SysCase> sysCaseList = this.sysCaseService.queryAll(sysCase);
        return getDataTable(sysCaseList);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable("id") Long id) {
        return success(this.sysCaseService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param sysCase 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(SysCase sysCase) {
        return success(this.sysCaseService.insert(sysCase));
    }

    /**
     * 编辑数据
     *
     * @param sysCase 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(SysCase sysCase) {
        return success(this.sysCaseService.update(sysCase));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public AjaxResult deleteById(Long id) {
        return success(this.sysCaseService.deleteById(id));
    }

}
