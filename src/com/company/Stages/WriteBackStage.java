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
        readData = new Register();
        aluOut = new Register();
        regDst = new Register();
    }

    public void run(){
        int temp = opcodeRegister.getValue();
        if(temp==0b111111 || temp==0b000010 || temp==4 || temp==5 || temp==43 )
            return;
        if(regWrite) {
            registerFile.write(memToReg ? readData.getValue() : aluOut.getValue(), regDst.getValue());
            //System.out.println(memToReg);
            //System.out.println(" i am writing " + (memToReg ? readData.getValue() : aluOut.getValue()) + " in register number " + regDst.getValue() );
        }
    }
}
