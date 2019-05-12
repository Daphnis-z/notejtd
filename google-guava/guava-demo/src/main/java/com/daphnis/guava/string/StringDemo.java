package com.daphnis.guava.string;

import java.util.List;

import com.google.common.base.CaseFormat;
import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

public class StringDemo{

    public static void println(Object obj) {
        System.out.println("##### "+obj.toString());
    }

    public static void joinerDemo() {
        List<String> list=Lists.newArrayList("hello","","world");
        list.add(null);
        println(String.join(",", list));

        Joiner joiner=Joiner.on(",");
        println(joiner.skipNulls().join(list));
        println(joiner.useForNull("empty").join(list));
    }

    public static void splitterDemo() {
        String str="hello,, world ,";
        List<String> list=Splitter.on(",").trimResults().omitEmptyStrings().splitToList(str);

        println(list);
    }

    public static void charsetsDemo() {
        String str="charsets";
        byte[] bytes=str.getBytes(Charsets.UTF_8);
        println(bytes.length);
    }

    public static void caseFormatDemo() {
        String str="googleGuava";
        println(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, str));
    }

    public static void main(String... args) {
        // joinerDemo();     
        // splitterDemo();
        // charsetsDemo();

        caseFormatDemo();
    }

}