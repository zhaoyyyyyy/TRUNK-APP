/*
 * @(#)StringToSourceInfoListConverter.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.base.utils;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.core.convert.converter.Converter;

import com.asiainfo.biapp.si.loc.core.source.entity.SourceInfo;

/**
 * Title : StringToSourceInfoListConverter
 * <p/>
 * Description :
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 5.0 +
 * <p/>
 * Modification History :
 * <p/>
 * 
 * <pre>
 * NO.    Date    Modified By    Why & What is modified
 * </pre>
 * 
 * <pre>
 * 1    2017年11月28日    zhangnan7        Created
 * </pre>
 * <p/>
 *
 * @author zhangnan7
 * @version 1.0.0.2017年11月28日
 */
public class StringToSourceInfoListConverter implements Converter<String, List<SourceInfo>> {

    @Override
    public List<SourceInfo> convert(String source) {
        if (StringUtil.isBlank(source)) {
            return null;
        }
        source = source.trim();
        List<SourceInfo> sourceInfoList = new ArrayList<>();
        if (source.contains("sourceInfoList{{")) {
            source = source.replace("sourceInfoList{", "");
            source = source.substring(0, source.length() - 1);
            if (source.contains("},")) {
                String[] str = source.split("},");
                for (int i = 0; i < str.length; i++) {
                    SourceInfo sourceInfo = new SourceInfo();
                    if (!"}".equals(str[i].charAt(str[i].length() - 1))) {
                        str[i] += "}";
                    }
                    JSONObject obj = JSONObject.fromObject(str[i]);
                    sourceInfo = (SourceInfo) JSONObject.toBean(obj, SourceInfo.class);
                    sourceInfoList.add(sourceInfo);
                }
            } else {
                SourceInfo sourceInfo = new SourceInfo();
                JSONObject obj = JSONObject.fromObject(source);
                sourceInfo = (SourceInfo) JSONObject.toBean(obj, SourceInfo.class);
                sourceInfoList.add(sourceInfo);
            }
        } else {
            SourceInfo sourceInfo = new SourceInfo();
            sourceInfo.setSourceTableId("please-format-sourceInfoList");
            sourceInfoList.add(sourceInfo);
        }
        return sourceInfoList;
    }

}
