package com.asiainfo.biapp.si.loc.bd.datadeal.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.asiainfo.biapp.si.loc.bd.datadeal.DataDealConstants;
import com.asiainfo.biapp.si.loc.bd.datadeal.component.LabelDealComponent;
import com.asiainfo.biapp.si.loc.bd.datadeal.service.IWideLabelService;
import com.asiainfo.biapp.si.loc.bd.datadeal.task.VerticalLabelTask;
import com.asiainfo.biapp.si.loc.bd.datadeal.task.WideLabelTask;
import com.asiainfo.biapp.si.loc.bd.datadeal.vo.BackParamVo;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by pwj on 2018/1/7.
 */
@Service
public class WideLabelServiceImpl implements IWideLabelService {
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
        if (StringUtils.isNotEmpty(backParamVo.getData())) {
            data_date = backParamVo.getData();
            LogUtil.info("日期解析：" + data_date);
        }
        if (labelDealComponent.isnumber(data_date)) {
            if (data_date.length() == 6) {
                data_cycle = DataDealConstants.LABEL_CYCLE_TYPE_M;
            } else if (data_date.length() == 8) {
                data_cycle = DataDealConstants.LABEL_CYCLE_TYPE_D;
            } else {
                LogUtil.info("月周期：YYYYMM；日周期：YYYYMMDD");
                return;
            }
        } else {
            LogUtil.debug("传入参数有误，请参照提示重新传入参数");
            LogUtil.info("1、传入一个参数：yyyymmdd 或 yyyymmdd");
            LogUtil.info("eg：20180302");
            LogUtil.info("所有专区准备好的表多线程一起跑");
            LogUtil.info("2、传入两个参数：时间 表名");
            LogUtil.info("eg：20180302 coc.table_01,coc.table_02,coc.table_03");
            LogUtil.info("根据传入的表名找对应的专区，跑对应专区可跑的标签");
            LogUtil.info("3、传入三个参数：时间 all 专区id_01,config_id_01");
            LogUtil.info("eg：20180302 all P001");
            LogUtil.info("指定专区，跑指定专区的数据,all:固定标识");
            LogUtil.info("4、传入三个参数：时间 表名 专区id");
            LogUtil.info("eg：20180302 coc.table_01,coc.table_02,coc.table_03 config_id_01,config_id_02");
            LogUtil.info("指定专区、表跑数据");
            return;
        }
        labelDealComponent.setData_date(data_date);
        labelDealComponent.setDate_cycle(data_cycle);
        List<String> configId = new ArrayList<String>();
        boolean initDate = labelDealComponent.initDate(data_date);
        if (initDate) {
            configId = labelDealComponent.getConfigId01(backParamVo);
        }
        //传入专区
        if (configId.size() > 0) {
            //判断专区状态
            configId = labelDealComponent.configIsOk(configId);
            LogUtil.info("可跑的专区为：" + configId);
            for (int i = 0; i < configId.size(); i++) {
                WideLabelTask myThread = new WideLabelTask();
                myThread.setConfig_id(configId.get(i));
                myThread.setData_date(data_date);
                myThread.setDate_cycle(data_cycle);
                Thread thread = new Thread(myThread);
                thread.start();
            }
        } else {
            LogUtil.debug("获取专区id失败，任务结束");
        }
    }
}
