package com.daphnis.collections;

import java.util.Set;
import java.util.TreeSet;

public class TreeSetDemo{
    public static void main(String... args) {
        Set<Integer> nums=new TreeSet<>();
        nums.add(15);
        nums.add(2);
        nums.add(11);
        nums.add(7);

        for (Integer num : nums) {
            System.out.println(num);
        }
    }
}