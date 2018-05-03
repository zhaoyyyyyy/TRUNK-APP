package com.asiainfo.biapp.si.loc.bd.datadeal.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.asiainfo.biapp.si.loc.core.ServiceConstants;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.asiainfo.biapp.si.loc.bd.datadeal.component.LabelDealComponent;
import com.asiainfo.biapp.si.loc.bd.datadeal.service.IVerticalLabelService;
import com.asiainfo.biapp.si.loc.bd.datadeal.task.VerticalLabelTask;
import com.asiainfo.biapp.si.loc.bd.datadeal.vo.BackParamVo;

/**
 * Created by pwj on 2018/3/30.
 */
@Service
public class VerticalLabelServiceImpl implements IVerticalLabelService {
    private String data_date = "";
    private Integer data_cycle = 0;

    @Autowired
    private LabelDealComponent labelDealComponent;
    
    /**
     * 
     * {@inheritDoc}
     * @see com.asiainfo.biapp.si.loc.bd.datadeal.service.IVerticalLabelService#exeRun(com.asiainfo.biapp.si.loc.bd.datadeal.vo.BackParamVo)
     */
    public void exeRun(BackParamVo backParamVo) {
        if(StringUtils.isNotEmpty(backParamVo.getDataDate())){
            data_date = backParamVo.getDataDate();
            LogUtil.info("日期解析：" + data_date);
        }
        if (labelDealComponent.isnumber(data_date)) {
            if (data_date.length() == 6) {
                data_cycle = ServiceConstants.LABEL_CYCLE_TYPE_M;
            } else if (data_date.length() == 8) {
                data_cycle = ServiceConstants.LABEL_CYCLE_TYPE_D;
            } else {
                LogUtil.error("传入参数有误，请重新传入参数：yyyymmdd 或 yyyymm");
                return;
            }
        } else {
            LogUtil.error("传入参数有误，请参照重新传入参数");
            return;
        }
        labelDealComponent.setData_date(data_date);
        labelDealComponent.setDate_cycle(data_cycle);
        List<String> configId = new ArrayList<String>();
        boolean initDate = labelDealComponent.initDate(data_date);
        if (initDate) {
            configId = labelDealComponent.getConfigId01(backParamVo);
        }
        configId = labelDealComponent.configIsOk(configId);
        if (configId.size() > 0) {
            //判断专区状态
            LogUtil.info("可跑的专区为：" + configId);
            for (int i = 0; i < configId.size(); i++) {
                VerticalLabelTask myThread = new VerticalLabelTask();
                myThread.setConfig_id(configId.get(i));
                myThread.setData_date(data_date);
                myThread.setDate_cycle(data_cycle);
                Thread thread = new Thread(myThread);
                thread.start();
            }
        } else {
            LogUtil.error("没有可跑的专区，任务结束");
        }
    }
}
