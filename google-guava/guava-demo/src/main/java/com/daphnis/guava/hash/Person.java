package com.daphnis.guava.hash;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

public class Person {
    private String name;
    private int age;
    private String country;

    public Person(String name,int age,String country){
        this.name=name;
        this.age=age;
        this.country=country;
    }

    public int geneHash(){
        return Hashing.
        md5().
        newHasher().
        putString(name, Charsets.UTF_8).
        putInt(age).
        putString(country, Charsets.UTF_8).
        hash().
        asInt();
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    
}