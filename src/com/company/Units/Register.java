package com.company.Units;

public class Register {
    private int value;
    private String name;

    public Register(){ this.value = 0; }
    public Register(int value) { this.value = value; }
    public Register(String name) { this.name = name; }
    public Register(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() { return value; }
    public void setValue(int value) { this.value = value; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
