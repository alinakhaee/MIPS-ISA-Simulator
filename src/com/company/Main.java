package com.company;


import com.company.Stages.*;
import com.company.Units.*;

public class Main {

    public static void main(String[] args) {
        int[] instructions = {0b000000_01000_11111_00000_00000_100000,  //S0 = S31 + S8
                              0b100011_11111_00110_0000000000000000};  //S6 = Mem(0 + S31)
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

//        System.out.println(Integer.toBinaryString(instructionDecodeStage.instructionRegister.getValue()));

        //System.out.println(registerFile.read(6));
        //System.out.println(instructionFetchStage.PCRegisterIn.getValue());
        for(int i=0 ; i<instructions.length+4 ; i++)
        {
            System.out.println("Clock Number : " + (i+1));
            instructionFetchStage.run();
            instructionDecodeStage.run();
            executionStage.run();
            memoryStage.run();
            writeBackStage.run();

            registerTransfer(instructionFetchStage, instructionDecodeStage, executionStage, memoryStage, writeBackStage);
            //System.out.println(instructionFetchStage.PCRegisterIn.getValue());
        }
        System.out.println(registerFile.read(6));
        System.out.println(registerFile.read(0));
        //System.out.println(registerFile.read(6));
    }

    public static void registerTransfer(InstructionFetchStage instructionFetchStage, InstructionDecodeStage instructionDecodeStage,
                                 ExecutionStage executionStage, MemoryStage memoryStage, WriteBackStage writeBackStage){
        memoryStage.registerTransfer(writeBackStage);
        executionStage.registerTransfers(memoryStage, instructionFetchStage);
        instructionDecodeStage.registerTransfer(executionStage);
        instructionFetchStage.registerTransfer(instructionDecodeStage);
    }
}
