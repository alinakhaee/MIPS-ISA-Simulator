package com.company.Stages;

import com.company.Units.DataMemory;
import com.company.Units.Register;

public class MemoryStage {
    public DataMemory dataMemory;

    public Register aluOutput, writeData, finalRegDst, opcodeRegisterIn;  //input
    public Register opcodeRegisterOut, readDataRegister, aluOut, regDst;  //output

    public boolean memWrite, memRead, memToReg, regWrite;  //signals

    public MemoryStage(DataMemory dataMemory) {
        this.dataMemory = dataMemory;
        opcodeRegisterIn = new Register(0b111111);
        aluOut = new Register();
        readDataRegister = new Register();
        regDst = new Register();
        opcodeRegisterOut = new Register();
        aluOutput = new Register();
        writeData = new Register();
        finalRegDst = new Register();
    }

    public void run(){
        opcodeRegisterOut.setValue(opcodeRegisterIn.getValue());
        int temp = opcodeRegisterIn.getValue();
        if(temp==0b111111 || temp==0b000010 || temp==4 || temp==5)  //stall, jump, beq or bne
            return;
        aluOut.setValue(aluOutput.getValue());
        regDst.setValue(finalRegDst.getValue());
        //System.out.println(aluOut.getValue());
        if(memRead)
            readDataRegister.setValue(dataMemory.read(aluOutput.getValue()/4));
        if(memWrite)
            dataMemory.write(aluOutput.getValue()/4, writeData.getValue());
    }

    public void registerTransfer(WriteBackStage writeBackStage){
        writeBackStage.opcodeRegister.setValue(opcodeRegisterOut.getValue());
        writeBackStage.aluOut.setValue(aluOut.getValue());
        writeBackStage.readData.setValue(readDataRegister.getValue());
        writeBackStage.regDst.setValue(regDst.getValue());

        writeBackStage.memToReg = memToReg;
        writeBackStage.regWrite = regWrite;
    }
}
