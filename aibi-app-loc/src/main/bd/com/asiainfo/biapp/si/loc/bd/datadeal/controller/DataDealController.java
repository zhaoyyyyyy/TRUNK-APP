package com.asiainfo.biapp.si.loc.bd.datadeal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.asiainfo.biapp.si.loc.base.utils.WebResult;
import com.asiainfo.biapp.si.loc.bd.datadeal.service.IVerticalLabelService;
import com.asiainfo.biapp.si.loc.bd.datadeal.service.IWideLabelService;
import com.asiainfo.biapp.si.loc.bd.datadeal.vo.BackParamVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api(value = "010.01->-标签数据处理", description = "彭文杰")
@RequestMapping("api/datadeal")
@RestController
public class DataDealController {
    @Autowired
    private IVerticalLabelService iverticalLabelService;

    @Autowired
    private IWideLabelService iwideLabelService;

    @ApiOperation(value = "执行标签宽表处理标签流程")
    @RequestMapping(value = "/wideTableDeal/excute", method = RequestMethod.POST)
    public WebResult<String> runWideTable(@ModelAttribute BackParamVo backParamVo) {
        WebResult<String> webResult = new WebResult<>();
        iwideLabelService.exeRun(backParamVo);
        return webResult.success("获取列成功", null);
    }
    

    @ApiOperation(value = "执行标签纵表处理标签流程")
    @RequestMapping(value = "/verticalTableDeal/excute", method = RequestMethod.POST)
    public WebResult<String> runVerticalTable(@ModelAttribute BackParamVo backParamVo) {
        WebResult<String> webResult = new WebResult<>();
        iverticalLabelService.exeRun(backParamVo);
        return webResult.success("获取列成功", null);
    }
}
