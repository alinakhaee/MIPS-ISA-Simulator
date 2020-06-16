package com.company.Stages;

import com.company.Units.Register;
import com.company.Units.RegisterFile;

public class WriteBackStage {
    public RegisterFile registerFile;

    public Register opcodeRegister, readData, aluOut, regDst; //input

    public boolean memToReg, regWrite;   //signals

    public WriteBackStage(RegisterFile registerFile) {
        this.registerFile = registerFile;
        opcodeRegister = new Register(0b111111);
    }

    public void run(){
        int temp = opcodeRegister.getValue();
        if(temp==0b111111 || temp==0b000010 || temp==4 || temp==5 || temp==43 )
            return;
        registerFile.write(memToReg ? readData.getValue() : aluOut.getValue() , regDst.getValue());
    }
}
