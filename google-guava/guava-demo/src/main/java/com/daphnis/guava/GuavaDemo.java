package com.daphnis.guava;

import java.util.List;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;

/**
 * Hello world!
 *
 */
public class GuavaDemo 
{
	
	public static void listDemo() {
		List<String> list=Lists.newArrayList("hello","guava");
        System.out.println( "create a list by guava: "+String.join(" ", list) );
	}
	
	public static void stringDemo() {
		String str1="Daphnis";
		if (Strings.isNullOrEmpty(str1)) {
			System.out.println("str1 is null or empty..");
		}else{
			System.out.println("str1 is "+str1);
		}
		
		String str2=null;
		if (Strings.isNullOrEmpty(str2)) {
			System.out.println("str2 is null or empty..");
		}else{
			System.out.println("str2 is "+str2);
		}
	}
	
    public static void main( String[] args )
    {
    	listDemo();
		stringDemo();	
    }
    
}




