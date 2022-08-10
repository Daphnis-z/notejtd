package com.daphnis.mybatis.entity;

public class Say {
    private String people;
    private String sentence;

    public Say() {
    }

    public Say(String people, String sentence) {
        this.people = people;
        this.sentence = sentence;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    @Override
    public String toString() {
        return String.format("%s --%s",sentence,people);
    }
}
