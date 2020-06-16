package com.company.Units;

public class InstructionMemory {
    int[] instructions;

    public InstructionMemory(int[] instructions) {
        this.instructions = instructions;
    }

    public int get(int i){
        return instructions[i];
    }
}
