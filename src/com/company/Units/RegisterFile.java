package com.company.Units;

public class RegisterFile {
    public Register[] registers = new Register[32];
    public RegisterFile(){
        initialSetup();
    }
    public void write(int value, int regNumber){
            registers[regNumber].setValue(value);
    }
    public int read(int regNumber){
        return registers[regNumber].getValue();
    }
    private void initialSetup(){
        registers[0] = new Register(0, "$zero");
        registers[1] = new Register("$at");
        registers[2] = new Register("$v0");
        registers[3] = new Register("$v1");
        registers[4] = new Register("$a0");
        registers[5] = new Register("$a1");
        registers[6] = new Register(1,"$a2");
        registers[7] = new Register("$a3");
        registers[8] = new Register(7,"$t0");
        registers[9] = new Register("$t1");
        registers[10] = new Register("$t2");
        registers[11] = new Register("$t3");
        registers[12] = new Register("$t4");
        registers[13] = new Register("$t5");
        registers[14] = new Register("$t6");
        registers[15] = new Register("$t7");
        registers[16] = new Register("$s0");
        registers[17] = new Register("$s1");
        registers[18] = new Register("$s2");
        registers[19] = new Register("$s3");
        registers[20] = new Register("$s4");
        registers[21] = new Register("$s5");
        registers[22] = new Register("$s6");
        registers[23] = new Register("$s7");
        registers[24] = new Register("$t8");
        registers[25] = new Register("$t9");
        registers[26] = new Register("$k0");
        registers[27] = new Register("$k1");
        registers[28] = new Register("$gp");
        registers[29] = new Register("$sp");
        registers[30] = new Register("$fp");
        registers[31] = new Register(16, "$ra");
    }
}
