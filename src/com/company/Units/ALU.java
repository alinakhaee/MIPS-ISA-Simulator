package com.company.Units;

public class ALU {
    int result;
    boolean zero;
    boolean ltzr; //less than zero

    public void compute(int a, int b, int opcode){
        if(opcode==0b00)
            result = a + b;
        else if(opcode==0b01)
            result = a - b;
        else if(opcode==0b10)
            result = a | b;
        else if(opcode==0b11)
            result = a & b;
        zero = result==0;
        ltzr = result<0;
    }
}
