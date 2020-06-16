package com.company.Stages;

import com.company.Units.ALU;
import com.company.Units.ALUControlUnit;
import com.company.Units.Register;
import com.company.Units.Shifter;

public class ExecutionStage {
    ALU alu;
    ALUControlUnit aluControlUnit;
    Shifter shifter;

    Register opcodeRegisterIn, A, B, immediate, regDst1, regDst2, jumpAddress, PCRegister;  //input registers
    Register aluOutput, regDst;

}
