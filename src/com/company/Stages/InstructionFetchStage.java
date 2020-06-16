package com.company.Stages;

import com.company.Units.InstructionMemory;
import com.company.Units.Register;

public class InstructionFetchStage {
    public Register PCRegisterIn; //input
    public Register instructionRegister, PCRegisterOut; //output
    public InstructionMemory instructionMemory;

    public InstructionFetchStage(InstructionMemory instructionMemory) {
        this.instructionMemory = instructionMemory;
        PCRegisterIn = new Register();
        instructionRegister = new Register();
        PCRegisterOut = new Register();
    }

    public void run(){
        instructionRegister.setValue(instructionMemory.get(PCRegisterIn.getValue()/4));
        PCRegisterIn.setValue(PCRegisterIn.getValue() + 4);
        PCRegisterOut.setValue(PCRegisterIn.getValue());
    }
}
