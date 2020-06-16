package com.company.Units;

public class ALU {
    public int result;
    public boolean zero;
    public boolean ltzr; //less than zero

    public void compute(int a, int b, int opcode){
        if(opcode==0b000)
            result = a + b;
        else if(opcode==0b001)
            result = a - b;
        else if(opcode==0b010)
            result = a | b;
        else if(opcode==0b011)
            result = a & b;
        else if(opcode==0b100)
            result = a<b ? 1 : 0 ;
        zero = result==0;
        ltzr = result<0;
    }
}
