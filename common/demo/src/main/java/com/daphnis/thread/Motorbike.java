package com.daphnis.thread;

/**
 * @author daphnis
 * @title 摩托车实体类
 * @date 2022-11-12 11:47
 */
public class Motorbike {
    private String brand;
    private String model;

    public Motorbike(String brand, String model) {
        this.brand = brand;
        this.model = model;
    }

    @Override
    public String toString() {
        return String.format("Motorbike[%s-%s]", brand, model);
    }
}
