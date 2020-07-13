package com.company.Stages;

import com.company.Units.ALU;
import com.company.Units.ALUControlUnit;
import com.company.Units.Register;
import com.company.Units.Shifter;

public class ExecutionStage {
    public ALU alu;
    public ALUControlUnit aluControlUnit;
    public Shifter shifter;

    public Register opcodeRegisterIn, A, B, immediate, regDst1, regDst2, jumpAddress, PCRegisterIn;  //input registers
    public Register aluOutput, finalRegDst, writeDataRegister, opcodeRegisterOut, PCRegisterOut;   //output registers

    public boolean jump, regWrite, ALUSrc, regDst, branch, memWrite, memRead, memToReg;     //control signals
    private boolean branchTaken = false;
    public int ALUOp;

    public ExecutionStage(ALU alu, ALUControlUnit aluControlUnit, Shifter shifter) {
        this.alu = alu;
        this.aluControlUnit = aluControlUnit;
        this.shifter = shifter;
        opcodeRegisterIn = new Register(0b111111);
        aluOutput = finalRegDst = writeDataRegister = opcodeRegisterOut = PCRegisterOut = new Register();
    }

    public void run(){
        opcodeRegisterOut.setValue(opcodeRegisterIn.getValue());
        if(opcodeRegisterIn.getValue() == 0b111111)  //stall
            return;
        if(jump){
            PCRegisterOut.setValue(jumpAddress.getValue());
            return;
        }
        String function = Long.toBinaryString(Integer.toUnsignedLong(immediate.getValue()) | 0x100000000L).substring(1);
        alu.compute(A.getValue(), ALUSrc ? immediate.getValue() : B.getValue() , aluControlUnit.compute(ALUOp, Integer.parseInt(function.substring(26,32),2)));
        if(branch){
            if(opcodeRegisterIn.getValue()==4 && alu.zero){   //beq
                PCRegisterOut.setValue(PCRegisterIn.getValue() + shifter.shiftLeft(immediate.getValue(),2));
                branchTaken = true;
                return;
            }
            else if(opcodeRegisterIn.getValue()==5 && !alu.zero){   //bne
                PCRegisterOut.setValue(PCRegisterIn.getValue() + shifter.shiftLeft(immediate.getValue(),2));
                branchTaken = true;
                return;
            }
        }
        aluOutput.setValue(alu.result);
        writeDataRegister.setValue(B.getValue());
        finalRegDst.setValue(regDst ? regDst1.getValue() : regDst2.getValue());
    }

    public void registerTransfers(MemoryStage memoryStage, InstructionFetchStage instructionFetchStage){
        if(jump || branchTaken)
            instructionFetchStage.PCRegisterIn = PCRegisterOut;
        memoryStage.aluOutput = aluOutput;
        memoryStage.finalRegDst = finalRegDst;
        memoryStage.writeData = writeDataRegister;
        memoryStage.opcodeRegisterIn = opcodeRegisterOut;

        memoryStage.memWrite = memWrite;
        memoryStage.memRead = memRead;
        memoryStage.memToReg = memToReg;
        memoryStage.regWrite = regWrite;
    }
}
