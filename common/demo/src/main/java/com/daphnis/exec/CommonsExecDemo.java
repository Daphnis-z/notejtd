package com.daphnis.exec;

import org.apache.commons.exec.*;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

/**
 * @author daphnis
 * @title 使用 commons-exec执行外部命令或程序
 * @date 2023-05-30 18:19
 */
public class CommonsExecDemo {
    // 同步执行命令
    public static void syncDemo() throws Exception {
        String command = "ping localhost";
        //接收正常结果流
        ByteArrayOutputStream susStream = new ByteArrayOutputStream();
        //接收异常结果流
        ByteArrayOutputStream errStream = new ByteArrayOutputStream();
        CommandLine commandLine = CommandLine.parse(command);
        DefaultExecutor exec = new DefaultExecutor();
        PumpStreamHandler streamHandler = new PumpStreamHandler(susStream, errStream);
        exec.setStreamHandler(streamHandler);
        int code = exec.execute(commandLine);
        System.out.println("退出代码: " + code);
        System.out.println(susStream.toString("GBK"));
        System.out.println("-----------------------------------------------------------");
        System.out.println(errStream.toString("GBK"));
    }

    // 异步执行命令
    public static void asyncDemo() throws Exception {
        String command = "ping localhost2";
        //接收正常结果流
        ByteArrayOutputStream susStream = new ByteArrayOutputStream();
        //接收异常结果流
        ByteArrayOutputStream errStream = new ByteArrayOutputStream();
        CommandLine commandLine = CommandLine.parse(command);
        DefaultExecutor exec = new DefaultExecutor();


        PumpStreamHandler streamHandler = new PumpStreamHandler(susStream, errStream);
        exec.setStreamHandler(streamHandler);
        ExecuteResultHandler erh = new ExecuteResultHandler() {
            @Override
            public void onProcessComplete(int exitValue) {
                try {
                    String suc = susStream.toString("GBK");
                    System.out.println(suc);
                    System.out.println("3. 异步执行完成");
                } catch (UnsupportedEncodingException uee) {
                    uee.printStackTrace();
                }
            }

            @Override
            public void onProcessFailed(ExecuteException e) {
                try {
                    String err = errStream.toString("GBK");
                    System.out.println(err);
                    System.out.println("3. 异步执行出错");
                } catch (UnsupportedEncodingException uee) {
                    uee.printStackTrace();
                }
            }
        };
        System.out.println("1. 开始执行");
        exec.execute(commandLine, erh);
        System.out.println("2. 做其他操作");
        // 避免主线程退出导致程序退出
        Thread.currentThread().join();
    }

    public static void main(String[] args) throws Exception {
        //syncDemo();
        asyncDemo();
    }

}
