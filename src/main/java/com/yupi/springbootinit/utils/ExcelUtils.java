package com.yupi.springbootinit.utils;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import org.apache.commons.collections4.map.LinkedMap;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * excel 转 csv
 */
public class ExcelUtils {
    public static String exceToCsv(MultipartFile multipartFile) {
        File file = null;

        try {
            file = ResourceUtils.getFile("classpath:网站数据.xlsx");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        List<Map<Integer, String>> list = EasyExcel.read(file)
                .excelType(ExcelTypeEnum.XLSX)   // 文件类型
                .sheet()  // 读取第几张表
                .headRowNumber(0)  // 读取第几行
                .doReadSync();   // 读取数据
        //如果数据为空
        if(CollUtil.isEmpty(list)) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();

        //转换为csv
        //读取表头（第一行）
        LinkedHashMap<Integer, String> headerMap = (LinkedHashMap)list.get(0);
        List<String> headerList = headerMap.values().stream().filter(ObjectUtils::isNotEmpty).collect(Collectors.toList());
        stringBuilder.append(StringUtils.join(headerList,",")).append("\n");
        //System.out.println(headerList);
        //读取数据（读取完表头后，从第一行开始读取）
        for(int i = 1; i < list.size(); i++) {
            LinkedHashMap<Integer, String> dataMap = (LinkedHashMap)list.get(i);
            List<String> dataList = dataMap.values().stream().filter(ObjectUtils::isNotEmpty).collect(Collectors.toList());
            stringBuilder.append(StringUtils.join(dataList,",")).append("\n");
            //System.out.println(dataList);
        }
        System.out.println(list);
        return "";
    }

    public static void main(String[] args) {
        exceToCsv(null);
    }

}
