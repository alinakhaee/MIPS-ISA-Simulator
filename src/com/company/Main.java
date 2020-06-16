package com.company;


import com.company.Units.Register;

public class Main {

    public static void main(String[] args) {
        int sID = 0b11000000000000000000000000101010;
        Register register = new Register(1);
        String result = Long.toBinaryString( Integer.toUnsignedLong(sID) | 0x100000000L ).substring(1);
        int s = sID << 2;
        Register instructionRegister = new Register(0b01010111111000001111100000011111);
        String instructionString = Long.toBinaryString(Integer.toUnsignedLong(instructionRegister.getValue()) | 0x100000000L).substring(1);
        int jumpValue = Integer.parseInt(instructionString.substring(6,32),2);
        Register PCRegister = new Register(0);
        String PCString = Long.toBinaryString(Integer.toUnsignedLong(PCRegister.getValue()) | 0x100000000L).substring(1);
        String jumpString = PCString.substring(0,4) + instructionString.substring(6,32) + "00";
        System.out.println(jumpString);
        System.out.println("check if works");
    }
}
