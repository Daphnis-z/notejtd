package com.daphnis.spark.functions;

import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

import org.apache.spark.api.java.function.FlatMapFunction;

public class FlatMapFunc implements FlatMapFunction<String, String> {

    private int strLengthLimit;

    public FlatMapFunc(int strLengthLimit){
        this.strLengthLimit=strLengthLimit;
    }

    @Override
    public Iterator<String> call(String str) throws Exception {
        List<String> results=Lists.newArrayList();

        if(str.length()<=strLengthLimit){
            results.add(str);
        }

        return results.iterator();
    }

}