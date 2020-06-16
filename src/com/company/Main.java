package com.company;


import com.company.Units.ALU;
import com.company.Units.ALUControlUnit;
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
        Register A, B, immediate;
        A = B = new Register(0b101010);
        System.out.println(A.getValue());
        immediate = new Register(0b100000);
        ALU alu = new ALU();
        ALUControlUnit aluControlUnit = new ALUControlUnit();
        boolean ALUSrc = false;
        int ALUOp = 0b000;
        String function = Long.toBinaryString(Integer.toUnsignedLong(immediate.getValue()) | 0x100000000L).substring(1);
        System.out.println(function.substring(26,32));
        alu.compute(A.getValue(), ALUSrc ? immediate.getValue() : B.getValue() , aluControlUnit.compute(ALUOp, Integer.parseInt(function.substring(26,32),2)));
        System.out.println(alu.result);
    }
}
