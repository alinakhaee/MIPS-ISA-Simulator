package com.company;


import com.company.Stages.*;
import com.company.Units.*;

public class Main {

    public static void main(String[] args) throws Exception {
        Assembler assembler = new Assembler();
        assembler.convert();
        int[] instructions = assembler.getInstructions();
        System.out.println(instructions);
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

        for(int i=0 ; i<instructions.length+4 ; i++)
        {
            instructionFetchStage.run();
            instructionDecodeStage.run();
            executionStage.run();
            memoryStage.run();
            writeBackStage.run();

            System.out.println("Clock Number : " + (i+1));
            System.out.println();
            printStatus(instructionFetchStage, instructionDecodeStage, executionStage, memoryStage, writeBackStage);
            System.out.println("\n\n****************************\n\n");

            registerTransfer(instructionFetchStage, instructionDecodeStage, executionStage, memoryStage, writeBackStage);
        }
    }

    public static void registerTransfer(InstructionFetchStage instructionFetchStage, InstructionDecodeStage instructionDecodeStage,
                                 ExecutionStage executionStage, MemoryStage memoryStage, WriteBackStage writeBackStage){
        memoryStage.registerTransfer(writeBackStage);
        executionStage.registerTransfers(memoryStage, instructionFetchStage);
        instructionDecodeStage.registerTransfer(executionStage);
        instructionFetchStage.registerTransfer(instructionDecodeStage);
    }

    public static void printStatus(InstructionFetchStage instructionFetchStage, InstructionDecodeStage instructionDecodeStage,
                                   ExecutionStage executionStage, MemoryStage memoryStage, WriteBackStage writeBackStage){
        System.out.println("IF : " + getInstruction(instructionFetchStage.instructionRegister.getFirst6Bits()) );

        System.out.println("ID : " + getInstruction(instructionDecodeStage.opcodeRegister.getValue()));

        System.out.println("EX : " + getInstruction(executionStage.opcodeRegisterIn.getValue()));

        System.out.println("MEM : " + getInstruction(memoryStage.opcodeRegisterIn.getValue()));

        System.out.println("WB : " + getInstruction(writeBackStage.opcodeRegister.getValue()));
    }

    public static String getInstruction(int opcode){
        switch (opcode){
            case 0b000010 :
                return "jump";
            case 0b101011 :
                return "sw";
            case 0b100011 :
                return "lw";
            case 0b001000 :
                return "addi";
            case 0b001010 :
                return "slti";
            case 0b001100 :
                return "andi";
            case 0b001101 :
                return "ori";
            case 0b000100 :
                return "beq";
            case 0b000101 :
                return "bne";
            case 0b000000 :
                return "R type";
            default:
                return "nothing";
        }

    }
}
