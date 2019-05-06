package com.daphnis.guava.collection;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;
import com.google.common.primitives.Ints;
import com.google.gson.Gson;

public class CollectionDemo{

    public static void println(Object obj) {
        System.out.println("##### "+obj.toString());
    }

    public static void listDemmo() {
        List<Integer> emptyList=Lists.newArrayList();
        println("create an empty array list ..");

        List<String> initList=Lists.newArrayList("hello","guava");
        initList.add("!!");
        println( "create an array list and init: "+String.join(" ", initList) );

        List<String> linkList=Lists.newLinkedList(Lists.newArrayList("This","is","a","linked","list"));
        println("create a linked list: "+String.join(" ", linkList.subList(0, linkList.size())));
    }

    public static void multisetDemo() {
        // like Map<E, Integer>
        Multiset<String> multiset=HashMultiset.create();
        multiset.addAll(Lists.newArrayList("d","b","b","a","a","a"));
        println(multiset);
        println("multiset,b: "+multiset.count("b"));
        println("multiset size: "+multiset.size());
        println("multiset uniq size: "+multiset.elementSet().size());

        // desc sort by count
        println(Multisets.copyHighestCountFirst(multiset));
    }

    public static void multimapDemo(){
        // like Map<E, List<E>>
        Multimap<String,String> mulmap=ArrayListMultimap.create();
        mulmap.put("name", "mike");
        mulmap.put("name", "mary");

        println("multimap size: "+mulmap.size());
        println("multimap key size: "+mulmap.keySet().size());
        println("name: "+String.join(",", mulmap.get("name")));

        // List implements Collection
        Map<String,Collection<String>> normap=mulmap.asMap();
        println(new Gson().toJson(normap));
    }

    public static void setDemo() {
        Set<String> nums=Sets.newHashSet("1","2","3");
        println("create set nums: "+nums);
        Set<String> nums2=ImmutableSet.of("2","3","4","5");
        println("create set nums2: "+nums2);

        // intersection
        Set<String> innNums=Sets.intersection(nums, nums2);
        println("intersection set: "+String.join(",", innNums));

        // union
        Set<String> unionNums=Sets.union(nums, nums2);
        println("union set: "+String.join(",", unionNums));

        // symmetric difference
        Set<String> syeNums=Sets.symmetricDifference(nums, nums2);
        println("symmetric difference set；"+String.join(",", syeNums));

        // difference: exist in nums2 but not in nums
        Set<String> dieNums=Sets.difference(nums2, nums);
        println("difference set: "+String.join(",", dieNums));
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

    /**
     * find value by key and find key by value
     */
    public static void bimapDemo() {
        BiMap<String,Integer> biMap=HashBiMap.create();
        biMap.put("num", 2);
        println("key is num,value: "+biMap.get("num"));
        println("key is 2,value: "+biMap.inverse().get(2));

        biMap.forcePut("force", 2);
        println("key is 2,value: "+biMap.inverse().get(2));

        biMap.put("two", 2);
    }

    public static void tableDemo() {
        Table<String,String,Integer> table=HashBasedTable.create();
        table.put("li", "xiaolong", 30);
        
        println("xiao long age: "+table.get("li", "xiaolong"));

        table.put("li", "xueli", 16);
        println("first name is li has: "+table.row("li").size());
    }

    public static void iterableDemo() {
        Iterable<Integer> nums=Iterables.concat(Ints.asList(1,2,3),Ints.asList(4,5,6));
        println("first num: "+Iterables.getFirst(nums,-1));
        println("last  num: "+Iterables.getLast(nums,-1));

        Iterator ite=Lists.newArrayList("str1","str2").iterator();
        println("iterator size: "+Iterators.size(ite));
    }

    public static void main(String... args) {
        // listDemmo();
        // immutableCollectionDemo();
        multisetDemo();
        // multimapDemo();
        // setDemo();
        // bimapDemo();
        // tableDemo();
        // iterableDemo();
    }

}
