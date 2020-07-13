package com.company;


import com.company.Stages.*;
import com.company.Units.*;

public class Main {

    public static void main(String[] args) {
//        int sID = 0b11000000000000000000000000101010;
//        Register register = new Register(1);
//        String result = Long.toBinaryString( Integer.toUnsignedLong(sID) | 0x100000000L ).substring(1);
//        int s = sID << 2;
//        Register instructionRegister = new Register(0b01010111111000001111100000011111);
//        String instructionString = Long.toBinaryString(Integer.toUnsignedLong(instructionRegister.getValue()) | 0x100000000L).substring(1);
//        int jumpValue = Integer.parseInt(instructionString.substring(6,32),2);
//        Register PCRegister = new Register(0);
//        String PCString = Long.toBinaryString(Integer.toUnsignedLong(PCRegister.getValue()) | 0x100000000L).substring(1);
//        String jumpString = PCString.substring(0,4) + instructionString.substring(6,32) + "00";
//        Register A, B, immediate;
//        A = B = new Register(0b101010);
//        System.out.println(A.getValue());
//        immediate = new Register(0b100000);
//        ALU alu = new ALU();
//        ALUControlUnit aluControlUnit = new ALUControlUnit();
//        boolean ALUSrc = false;
//        int ALUOp = 0b000;
//        String function = Long.toBinaryString(Integer.toUnsignedLong(immediate.getValue()) | 0x100000000L).substring(1);
//        System.out.println(function.substring(26,32));
//        alu.compute(A.getValue(), ALUSrc ? immediate.getValue() : B.getValue() , aluControlUnit.compute(ALUOp, Integer.parseInt(function.substring(26,32),2)));
//        System.out.println(alu.result);
        int[] instructions = {0b00000011111001100000100000100000};
        InstructionMemory instructionMemory = new InstructionMemory(instructions);
        RegisterFile registerFile = new RegisterFile();
        ControlUnit controlUnit = new ControlUnit();
        Shifter shifter = new Shifter();
        ALU alu = new ALU();
        ALUControlUnit aluControlUnit = new ALUControlUnit();
        DataMemory dataMemory = new DataMemory(20);

        InstructionFetchStage instructionFetchStage = new InstructionFetchStage(instructionMemory);
        InstructionDecodeStage instructionDecodeStage = new InstructionDecodeStage(controlUnit, registerFile);
        ExecutionStage executionStage = new ExecutionStage(alu, aluControlUnit, shifter);
        MemoryStage memoryStage = new MemoryStage(dataMemory);
        WriteBackStage writeBackStage = new WriteBackStage(registerFile);

        System.out.println(Integer.toBinaryString(instructionDecodeStage.instructionRegister.getValue()));

        instructionFetchStage.run();
        instructionDecodeStage.run();
        executionStage.run();
        memoryStage.run();
        writeBackStage.run();

        //System.out.println(Integer.toBinaryString(instructionFetchStage.instructionRegister.getValue()));
        registerTransfer(instructionFetchStage, instructionDecodeStage, executionStage, memoryStage, writeBackStage);
        System.out.println(Integer.toBinaryString(instructionDecodeStage.instructionRegister.getValue()));

        instructionFetchStage.run();
        instructionDecodeStage.run();
        executionStage.run();
        memoryStage.run();
        writeBackStage.run();

        System.out.println(Integer.toBinaryString(instructionFetchStage.instructionRegister.getValue()));
        registerTransfer(instructionFetchStage, instructionDecodeStage, executionStage, memoryStage, writeBackStage);

        instructionFetchStage.run();
        instructionDecodeStage.run();
        executionStage.run();
        memoryStage.run();
        writeBackStage.run();

        System.out.println(executionStage.opcodeRegisterIn.getValue());
        registerTransfer(instructionFetchStage, instructionDecodeStage, executionStage, memoryStage, writeBackStage);
    }

    public static void registerTransfer(InstructionFetchStage instructionFetchStage, InstructionDecodeStage instructionDecodeStage,
                                 ExecutionStage executionStage, MemoryStage memoryStage, WriteBackStage writeBackStage){
        instructionFetchStage.registerTransfer(instructionDecodeStage);
        instructionDecodeStage.registerTransfer(executionStage);
        executionStage.registerTransfers(memoryStage, instructionFetchStage);
        memoryStage.registerTransfer(writeBackStage);
    }
}
