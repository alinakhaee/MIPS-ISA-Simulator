package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

public class Assembler {

    public void convert() throws Exception{
        File file = new File("assembly.txt");
        FileWriter fileWriter = new FileWriter("machineCode.txt");
        PrintWriter printWriter = new PrintWriter(fileWriter);
        HashMap<String, Integer> variables = new HashMap<>();
        Scanner scanner = new Scanner(file);
        String line;

        for(int i=1 ; scanner.hasNextLine() ; i++) {
            line = scanner.nextLine();
            if(line.contains(":"))
                variables.put(line.substring(0,line.indexOf(":")), i);
        }

        System.out.println(variables);
        scanner.close();
        scanner = new Scanner(file);

        for(int i=1 ; scanner.hasNextLine() ; i++) {
            line = scanner.nextLine();
            if(line.contains(":")) {
                line = line.substring(line.indexOf(" ") + 1);
            }
            String instruction = line.substring(0,line.indexOf(" "));
            if(instruction.equals("add") || instruction.equals("sub") || instruction.equals("or") || instruction.equals("and") || instruction.equals("slt")) {
                System.out.println(RtypeAnalyze(line, instruction) + "    " + line);
                printWriter.println(RtypeAnalyze(line, instruction));
            }
            else if(instruction.equals("addi") || instruction.equals("ori") || instruction.equals("andi") || instruction.equals("slti")) {
                System.out.println(ItypeAnalyze(line, instruction) + "    " + line);
                printWriter.println(ItypeAnalyze(line, instruction));
            }
            else if(instruction.equals("lw") || instruction.equals("sw")) {
                System.out.println(memoryInstAnalyze(line, instruction) + "    " + line);
                printWriter.println(memoryInstAnalyze(line, instruction));
            }
            else if(instruction.equals("j")){
                System.out.println(jumpAnalyze(line, variables) + "    " + line);
                printWriter.println(jumpAnalyze(line, variables));
            }
            else if(instruction.equals("beq") || instruction.equals("bne")){
                System.out.println(branchAnalyze(line, instruction, variables, i) + "    " + line);
                printWriter.println(branchAnalyze(line, instruction, variables, i));
            }
        }
        printWriter.close();
    }

    private String RtypeAnalyze(String line, String instructionType){
        String output = "000000";
        int indexOfSpace = line.indexOf(" ");
        int indexOfComma = line.indexOf(",");
        String rd = registerNumber(line.substring(indexOfSpace+1, indexOfComma));
        indexOfSpace = line.indexOf(" ", indexOfComma);
        indexOfComma = line.indexOf(",", indexOfSpace);
        String rs = registerNumber(line.substring(indexOfSpace+1, indexOfComma));
        indexOfSpace = line.indexOf(" ", indexOfComma);
        String rt = registerNumber(line.substring(indexOfSpace+1));
        output = output + rs + rt + rd + "00000";
        if(instructionType.equals("add"))
            output += "100000";
        else if(instructionType.equals("sub"))
            output +=  "100010";
        else if(instructionType.equals("or"))
            output += "100101";
        else if(instructionType.equals("and"))
            output += "100100";
        else if(instructionType.equals("slt"))
            output += "101010";
        return output;
    }

    private String ItypeAnalyze(String line, String instructionType){
        String output = "";
        if(instructionType.equals("addi"))
            output += "001000";
        else if(instructionType.equals("ori"))
            output += "001101";
        else if(instructionType.equals("andi"))
            output += "001100";
        else if(instructionType.equals("slti"))
            output += "001010";
        int indexOfSpace = line.indexOf(" ");
        int indexOfComma = line.indexOf(",");
        String rt = registerNumber(line.substring(indexOfSpace+1, indexOfComma));
        indexOfSpace = line.indexOf(" ", indexOfComma);
        indexOfComma = line.indexOf(",", indexOfSpace);
        String rs = registerNumber(line.substring(indexOfSpace+1, indexOfComma));
        indexOfSpace = line.indexOf(" ", indexOfComma);
        String immediate = Integer.toBinaryString(0x10000 | Integer.parseInt(line.substring(indexOfSpace+1))).substring(1);
        output = output + rs + rt + immediate;
        return output;
    }

    private String memoryInstAnalyze(String line, String instructionType){
        String output = "";
        if(instructionType.equals("lw"))
            output += "100011";
        else if(instructionType.equals("sw"))
            output += "101011";
        int indexOfSpace = line.indexOf(" ");
        int indexOfComma = line.indexOf(",");
        int indexOfOpPar = line.indexOf("(");
        int indexOfClPar = line.indexOf(")");
        String rt = registerNumber(line.substring(indexOfSpace+1, indexOfComma));
        indexOfSpace = line.indexOf(" ", indexOfComma);
        String immediate = Integer.toBinaryString(0x10000 | Integer.parseInt(line.substring(indexOfSpace+1, indexOfOpPar))).substring(1);
        String rs = registerNumber(line.substring(indexOfOpPar+1, indexOfClPar));
        output = output + rs + rt + immediate;
        return output;
    }

    private String jumpAnalyze(String line, HashMap<String, Integer> variables){
        String output = "000010";
        String variableName = line.substring(2);
        String address = String.format("%26s", Integer.toBinaryString(variables.get(variableName)-1)).replaceAll(" ", "0");
        output = output + address;
        return output;
    }

    private String branchAnalyze(String line, String instructionType, HashMap<String, Integer> variables, int lineNumber){
        String output = "";
        if(instructionType.equals("beq"))
            output = "000100";
        else if(instructionType.equals("bne"))
            output = "000101";
        int indexOfSpace = line.indexOf(" ");
        int indexOfComma = line.indexOf(",");
        String rs = registerNumber(line.substring(indexOfSpace+1, indexOfComma));
        indexOfSpace = line.indexOf(" ", indexOfComma);
        indexOfComma = line.indexOf(",", indexOfSpace);
        String rt = registerNumber(line.substring(indexOfSpace+1, indexOfComma));
        indexOfSpace = line.indexOf(" ", indexOfComma);
        String variableName = line.substring(indexOfSpace+1);
        int difference = variables.get(variableName) - lineNumber - 1;
        String address = String.format("%16s", (Integer.toBinaryString(difference))).replaceAll(" ", "0");
        address = address.substring(address.length()-16);
        output = output + rs + rt + address;
        return output;
    }

    private String registerNumber(String s){
        if (s.equals("$zero")) return "00000";
        if (s.equals("$at")) return "00001";
        if (s.equals("$v0")) return "00010";
        if (s.equals("$v1")) return "00011";
        if (s.equals("$a0")) return "00100";
        if (s.equals("$a1")) return "00101";
        if (s.equals("$a2")) return "00110";
        if (s.equals("$a3")) return "00111";
        if (s.equals("$t0")) return "01000";
        if (s.equals("$t1")) return "01001";
        if (s.equals("$t2")) return "01010";
        if (s.equals("$t3")) return "01011";
        if (s.equals("$t4")) return "01100";
        if (s.equals("$t5")) return "01101";
        if (s.equals("$t6")) return "01110";
        if (s.equals("$t7")) return "01111";
        if (s.equals("$s0")) return "10000";
        if (s.equals("$s1")) return "10001";
        if (s.equals("$s2")) return "10010";
        if (s.equals("$s3")) return "10011";
        if (s.equals("$s4")) return "10100";
        if (s.equals("$s5")) return "10101";
        if (s.equals("$s6")) return "10110";
        if (s.equals("$s7")) return "10111";
        if (s.equals("$t8")) return "11000";
        if (s.equals("$t9")) return "11001";
        if (s.equals("$k0")) return "11010";
        if (s.equals("$k1")) return "11011";
        if (s.equals("$gp")) return "11100";
        if (s.equals("$sp")) return "11101";
        if (s.equals("$fp")) return "11110";
        if (s.equals("$ra")) return "11111";
        return "00000";
    }

    public int[] getInstructions()throws Exception{
        File file = new File("machineCode.txt");
        Scanner scanner = new Scanner(file);
        int lines = 0;
        while (scanner.hasNext()){
            lines++;
            String temp = scanner.next();
        }
        scanner.close();
        scanner = new Scanner(file);
        int[] instructions = new int[lines];
        for(int i=0 ; i<lines ; i++)
            instructions[i] = (int) Long.parseLong(scanner.next(), 2);
        return instructions;
    }
}
