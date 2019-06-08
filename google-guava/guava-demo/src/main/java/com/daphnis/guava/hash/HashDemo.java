package com.daphnis.guava.hash;

public class HashDemo {
    public static void main(String... args) {
        Person p1=new Person("daphnis", 22, "chinese");
        Person p2=new Person("韩梅梅", 18, "中国");

		System.out.println("hash1: "+p1.geneHash());
		System.out.println("hash2: "+p2.geneHash());
    }
}
