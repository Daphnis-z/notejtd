package com.daphnis.guava.collection;

import java.util.List;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;

public class CollectionDemo{

    public static void println(Object obj) {
        System.out.println(obj.toString());
    }

    public static void listDemmo() {
        List<Integer> emptyList=Lists.newArrayList();
        println("##### create an empty array list ..");

        List<String> initList=Lists.newArrayList("hello","guava");
        initList.add("!!");
        println( "##### create an array list and init: "+String.join(" ", initList) );

        List<String> linkList=Lists.newLinkedList(Lists.newArrayList("This","is","a","linked","list"));
        println("##### create a linked list: "+String.join(" ", linkList.subList(0, linkList.size())));
    }

    public static void multisetDemo() {
        // like Map<E, Integer>
        Multiset<String> multiset=HashMultiset.create();
        multiset.addAll(Lists.newArrayList("a","b","b","a","a","d"));
        println("##### multiset,b: "+multiset.count("b"));
        println("##### multiset size: "+multiset.size());
        println("##### multiset uniq size: "+multiset.elementSet().size());
    }

    public static void multimapDemo(){

    }

    public static void setDemo() {
        
    }

    /**
     * Immutable Collection
     */
    public static void immutableCollectionDemo() {
        List<String> imlist=ImmutableList.of("immutiale","list");
        try {
            imlist.add("word");    
        } catch (Exception e) {
            println("add word error !!");
            e.printStackTrace();
        }

        List<String> arrList=Lists.newArrayList("hello");
        arrList.add("world");
        List<String> imlist2=ImmutableList.copyOf(arrList);
        imlist2.add("!!");
    }

    public static void main(String... args) {
        // listDemmo();
        // immutableCollectionDemo();
        multisetDemo();
    }

}
