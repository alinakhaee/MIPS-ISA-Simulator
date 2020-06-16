package com.company.Stages;

import com.company.Units.ControlUnit;
import com.company.Units.Register;
import com.company.Units.RegisterFile;

public class InstructionDecodeStage {
    Register instructionRegister, PCRegisterIn; //input
    Register A, B, immediate, regDst1, regDst2, jumpAddress, PCRegisterOut, opcodeRegister; //output

    public boolean jump, regWrite, ALUSrc, regDst, branch, memWrite, memRead, memToReg;     //control signals
    public int ALUOp = 0b000;

    ControlUnit controlUnit;
    RegisterFile registerFile;
    int opcode, firstRegister, secondRegister, immediateValue, regDst1Value, regDst2Value;
    String jumpString;

    public InstructionDecodeStage(ControlUnit controlUnit, RegisterFile registerFile) {
        this.controlUnit = controlUnit;
        this.registerFile = registerFile;
        A = B = immediate = regDst1 = regDst2 = jumpAddress = PCRegisterOut = opcodeRegister = new Register();
    }

    public void run(){
        String instructionString = Long.toBinaryString(Integer.toUnsignedLong(instructionRegister.getValue()) | 0x100000000L).substring(1);
        opcode = Integer.parseInt(instructionString.substring(0,6),2);
        firstRegister = Integer.parseInt(instructionString.substring(6,11),2);
        secondRegister = Integer.parseInt(instructionString.substring(11,16),2);
        immediateValue = Integer.parseInt(instructionString.substring(16,32),2);
        regDst1Value = Integer.parseInt(instructionString.substring(16,21),2);
        regDst2Value = secondRegister;
        String PCString = Long.toBinaryString(Integer.toUnsignedLong(PCRegisterIn.getValue()) | 0x100000000L).substring(1);
        jumpString = PCString.substring(0,4) + instructionString.substring(6,32) + "00";

        A.setValue(registerFile.read(firstRegister));
        B.setValue(registerFile.read(secondRegister));
        immediate.setValue(immediateValue);
        regDst1.setValue(regDst1Value);
        regDst2.setValue(regDst2Value);
        jumpAddress.setValue(Integer.parseInt(jumpString, 2));
        PCRegisterOut.setValue(PCRegisterIn.getValue());
        opcodeRegister.setValue(opcode);

        controlUnit.compute(opcode);
        controlUnit.fetchSignalControls(this);
    }


}
