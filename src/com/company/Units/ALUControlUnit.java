package com.company.Units;

public class ALUControlUnit {
    public int compute(int ALUOp, int function){
        if(ALUOp == 0b000){ //R type
            if(function == 0b100000) //add
                return 0b00;
            else if(function == 0b100010) //sub
                return 0b01;
            else if(function == 0b100101) //or
                return 0b10;
            else if(function == 0b100100) //and
                return 0b11;
            else if(function == 0b101010) //slt
                return 0b01;
        }
        else if( ALUOp == 0b001)  //bne , beq
            return 0b01;
        else if( ALUOp == 0b010)  //sw  , lw
            return 0b00;
        else if( ALUOp == 0b011)  // addi
            return 0b00;
        else if( ALUOp == 0b100)  // slti
            return 0b01;
        else if( ALUOp == 0b101)  // andi
            return 0b11;
        else if( ALUOp == 0b110)  // ori
            return 0b10;
        return 0b00;
    }
}
