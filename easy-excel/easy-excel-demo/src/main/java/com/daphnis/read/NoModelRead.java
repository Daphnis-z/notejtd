package com.daphnis.read;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.support.ExcelTypeEnum;

public class NoModelRead {

    public void testExcel2003NoModel() {
        try (InputStream inputStream = new FileInputStream("test-datas/excel03.xls")){
            // 解析每行结果在listener中处理
            ExcelListener listener = new ExcelListener();

            ExcelReader excelReader = new ExcelReader(inputStream, ExcelTypeEnum.XLS, null, listener);
            excelReader.read();

            List<Object> excelDatas=listener.getDatas();
            System.out.println("title: "+excelDatas.get(0).toString());
            for (int i = 1; i < excelDatas.size(); i++) {
                System.out.println("data line "+i+": "+excelDatas.get(i));
            }

            System.out.println("read excel 03 complete..");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String... args) {
        NoModelRead noModelRead=new NoModelRead();
        noModelRead.testExcel2003NoModel();
    }

}
