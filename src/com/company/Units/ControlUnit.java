package com.company.Units;

import com.company.Stages.InstructionDecodeStage;

public class ControlUnit {
    public int ALUOp;
    public boolean jump, regWrite, ALUSrc, regDst, branch, memWrite, memRead, memToReg;
    public void compute(int instruction){
        if(instruction == 0b000000){      // R type
            jump = false;
            regWrite = true;
            ALUSrc = false;
            regDst = true;
            branch = false;
            memWrite = false;
            memRead = false;
            memToReg = false;
            ALUOp = 0b000;
        }
        else if(instruction == 0b000010){   // jump
            jump = true;
            regWrite = false;
            ALUSrc = false;
            regDst = false;
            branch = false;
            memWrite = false;
            memRead = false;
            memToReg = false;
            ALUOp = 0b000;
        }
        else if(instruction == 43){      // sw
            jump = false;
            regWrite = false;
            ALUSrc = true;
            regDst = false;
            branch = false;
            memWrite = true;
            memRead = false;
            memToReg = false;
            ALUOp = 0b010;
        }
        else if(instruction == 35){     //lw
            jump = false;
            regWrite = true;
            ALUSrc = true;
            regDst = false;
            branch = false;
            memWrite = false;
            memRead = true;
            memToReg = true;
            ALUOp = 0b010;
        }
        else if(instruction == 8){     //addi
            jump = false;
            regWrite = true;
            ALUSrc = true;
            regDst = false;
            branch = false;
            memWrite = false;
            memRead = false;
            memToReg = false;
            ALUOp = 0b011;
        }
        else if(instruction == 10){    // slti
            jump = false;
            regWrite = true;
            ALUSrc = true;
            regDst = false;
            branch = false;
            memWrite = false;
            memRead = false;
            memToReg = false;
            ALUOp = 0b100;
        }
        else if(instruction == 12){   //andi
            jump = false;
            regWrite = true;
            ALUSrc = true;
            regDst = false;
            branch = false;
            memWrite = false;
            memRead = false;
            memToReg = false;
            ALUOp = 0b101;
        }
        else if(instruction == 13){   //ori
            jump = false;
            regWrite = true;
            ALUSrc = true;
            regDst = false;
            branch = false;
            memWrite = false;
            memRead = false;
            memToReg = false;
            ALUOp = 0b110;
        }
        else if(instruction == 4){    //beq
            jump = false;
            regWrite = false;
            ALUSrc = false;
            regDst = false;
            branch = true;
            memWrite = false;
            memRead = false;
            memToReg = false;
            ALUOp = 0b001;
        }
        else if(instruction == 5){    //bne
            jump = false;
            regWrite = false;
            ALUSrc = false;
            regDst = false;
            branch = true;
            memWrite = false;
            memRead = false;
            memToReg = false;
            ALUOp = 0b001;
        }
    }
    public void fetchSignalControls(InstructionDecodeStage instructionDecodeStage){
        instructionDecodeStage.jump = jump;
        instructionDecodeStage.regWrite = regWrite;
        instructionDecodeStage.ALUSrc = ALUSrc;
        instructionDecodeStage.regDst = regDst;
        instructionDecodeStage.branch = branch;
        instructionDecodeStage.memWrite = memWrite;
        instructionDecodeStage.memRead = memRead;
        instructionDecodeStage.memToReg = memToReg;
        instructionDecodeStage.ALUOp = ALUOp;
    }
}
