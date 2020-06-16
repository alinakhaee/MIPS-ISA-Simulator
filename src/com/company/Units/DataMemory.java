package com.company.Units;

public class DataMemory {
    public int[] data ;

    public DataMemory(int size) {
        this.data = new int[size];
        initialSetup();
    }

    public int read(int i){
        return data[i];
    }
    public void write(int i, int value){
        data[i] = value;
    }

    private void initialSetup(){
        for(int i=0 ; i<data.length ; i++)
            data[i] = i ;
    }
}
