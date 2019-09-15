package com.daphnis.write;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.google.common.collect.Lists;

public class NoModelWrite {

    public static void main(String... args) {
        try (OutputStream out = new FileOutputStream("writeTest.xlsx")){
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX,false);
            //写第一个sheet, sheet1  数据全是List<String> 无模型映射关系
            Sheet sheet1 = new Sheet(1, 0);
            sheet1.setSheetName("第一个sheet");

            List<List<String>> outData=Lists.newArrayList();
            outData.add(Lists.newArrayList("李雷","18"));
            outData.add(Lists.newArrayList("韩梅梅","17"));
            System.out.println("ready to write data: "+outData.toString());

            writer.write0(outData, sheet1);
            writer.finish();

            System.out.println("generate excel success..");
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

}