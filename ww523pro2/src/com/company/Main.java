package com.company;



import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

import static java.lang.String.*;

public class Main {
    public static int tempt;
    public static int tempr;
    public static void main(String[] args) throws IOException {
        String input = readFileContent("in");
        ArrayList<Node> word = new ArrayList<>();



        transfer(input, word);
        System.out.println(input);
        for (int i = 0; i < word.size(); i++) {
            System.out.println(word.get(i).type + " ||| " + word.get(i).content);
        }

        int table[][] = new int[][]{
                {-1,-1,-1,-1,-1,-1,-1,-1,2,-1,1,-1},
                {-1,-1,-1,-1,-1,-1,-1,-1,-1,-2,-1,-1},
                {3,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                {-1,5,-1,-1,-1,-1,6,-1,7,-1,-1,4},
                {-1,-1,8,9,10,11,-1,-1,-1,101,-1,-1},
                {-1,5,-1,-1,-1,-1,6,-1,7,-1,-1,12},
                {-1,5,-1,-1,-1,-1,6,-1,7,-1,-1,13},
                {-1,-1,108,108,108,108,-1,108,-1,108,-1,-1},
                {-1,5,-1,-1,-1,-1,6,-1,7,-1,-1,14},
                {-1,5,-1,-1,-1,-1,6,-1,7,-1,-1,14},
                {-1,5,-1,-1,-1,-1,6,-1,7,-1,-1,16},
                {-1,5,-1,-1,-1,-1,6,-1,7,-1,-1,17},
                {-1,-1,102,102,102,102,-1,102,-1,102,-1,-1},
                {-1,-1,8,9,10,11,-1,18,-1,-1,-1,-1},
                {-1,-1,103,103,10,11,-1,103,-1,103,-1,-1},
                {-1,-1,104,104,10,11,-1,104,-1,104,-1,-1},
                {-1,-1,105,105,105,105,-1,105,-1,105,-1,-1},
                {-1,-1,106,106,106,106,-1,106,-1,106,-1,-1},
                {-1,-1,107,107,107,107,-1,107,-1,107,-1,-1}
        };
        ArrayList<String> wenfa = new ArrayList<>();
        wenfa.add("#");
        wenfa.add("A>i=E");  //1
        wenfa.add("E>@E");  //2
        wenfa.add("E>E+E"); //3
        wenfa.add("E>E-E"); //4
        wenfa.add("E>E*E"); //5
        wenfa.add("E>E/E"); //6
        wenfa.add("E>(E)"); //7
        wenfa.add("E>i"); //8
        Stack<Integer> status = new Stack<Integer>();
        Stack<String> sym = new Stack<String>();
        Stack<String> t = new Stack<String>();
        t.push("0");
        ArrayList<S> s = new ArrayList<>();
        HashMap<String, String> hash = new HashMap<>();
        status.push(0);
        sym.push("#");
        String ts;
        int index = 0;
        tempt = -1;
        tempr = -1;
        String huibian = "";
        String h = "";
        int snum = 1;
        int x = 1 ;
        String res = "";
            while (true) {
                begin:
                {
                    if(x >=1) {
                        res = word.get(index).content;
                        x--;
                    }
                    int row = status.peek();
                    ts = word.get(index).type;
                    int action = getnum(table, row, ts);
                    if (action == -1) {
                        System.out.println("error");
                        break;
                    } else if (action == -2) {
                        System.out.println("acc");
                        if (index < word.size() - 1) {
                            sym.pop();
                            status.pop();
                            index++;
                            x = 1;
                            break begin;
                        }
                        break;
                    } else if (action <= 100 && action > 0) {
                        status.push(action);
                        t.push("0");
                        if (word.get(index).type == "4" || word.get(index).type == "2") {
                            sym.push("i");
                        } else {
                            sym.push(word.get(index).content);
                        }
                        System.out.println(sym);
                        System.out.println(status);
                        System.out.println(t);
                        for (int j = 0; j < s.size(); j++) {
                            s.get(j).print();
                        }
                        System.out.println(hash);
                        System.out.println();
                        index++;
                    } else {
                        action -= 100;
                        String tempw = wenfa.get(action);
                        if (tempw.equals("A>i=E")) //1
                        {
                            sym.pop();
                            sym.pop();
                            sym.pop();
                            status.pop();
                            status.pop();
                            status.pop();
                            String t1 = t.pop();
                            t.pop();
                            t.pop();
                            s.add(new S("=", t1, "", res, snum));
                            snum++;
                            String r0 = getnewr();
                            huibian += "MOV " + r0 + "," + t1 + "\n";
                            huibian += "MOV " + res + "," + r0 + "\n";
                            h += res + " DW ? \n";
                        } else if (tempw.equals("E>@E")) //2
                        {
                            String tempt = t.peek();
                            String t1 = getnewt();
                            sym.pop();
                            sym.pop();
                            status.pop();
                            status.pop();
                            t.pop();
                            t.pop();
                            t.push(t1);
                            s.add(new S("@", tempt, "", t1, snum));
                            snum++;
                            String r0 = getnewr();
                            huibian += "MOV " + r0 + "," + tempt + "\n";
                            huibian += "NEG " + r0 + "\n";
                            huibian += "MOV " + tempt + "," + r0 + "\n";
                            h += t1 + " DW ? \n";
                        } else if (tempw.equals("E>E+E")) //3
                        {
                            sym.pop();
                            sym.pop();
                            sym.pop();
                            status.pop();
                            status.pop();
                            status.pop();
                            String t1 = t.pop();
                            t.pop();
                            String t2 = t.pop();
                            String t3 = getnewt();
                            s.add(new S("+", t2, t1, t3, snum));
                            snum++;
                            t.push(t3);
                            String r0 = getnewr();
                            huibian += "MOV " + r0 + "," + t1 + "\n";
                            huibian += "ADD " + r0 + "," + t2 + "\n";
                            huibian += "MOV " + t3 + "," + r0 + "\n";
                            h += t3 + " DW ? \n";
                        } else if (tempw.equals("E>E-E")) //4
                        {
                            sym.pop();
                            sym.pop();
                            sym.pop();
                            status.pop();
                            status.pop();
                            status.pop();
                            String t1 = t.pop();
                            t.pop();
                            String t2 = t.pop();
                            String t3 = getnewt();
                            s.add(new S("-", t2, t1, t3, snum));
                            snum++;
                            t.push(t3);
                            String r0 = getnewr();
                            huibian += "MOV " + r0 + "," + t2 + "\n";
                            huibian += "SUB " + r0 + "," + t1 + "\n";
                            huibian += "MOV " + t3 + "," + r0 + "\n";
                            h += t3 + " DW ? \n";
                        } else if (tempw.equals("E>E*E")) //5
                        {
                            sym.pop();
                            sym.pop();
                            sym.pop();
                            status.pop();
                            status.pop();
                            status.pop();
                            String t1 = t.pop();
                            t.pop();
                            String t2 = t.pop();
                            String t3 = getnewt();
                            s.add(new S("*", t2, t1, t3, snum));
                            snum++;
                            t.push(t3);
                            String r0 = getnewr();
                            huibian += "MOV " + r0 + "," + t2 + "\n";
                            huibian += "MUL " + " " + t1 + "\n";
                            huibian += "MOV " + t3 + "," + r0 + "\n";
                            h += t3 + " DW ? \n";
                        } else if (tempw.equals("E>E/E")) //6
                        {
                            sym.pop();
                            sym.pop();
                            sym.pop();
                            status.pop();
                            status.pop();
                            status.pop();
                            String t1 = t.pop();
                            t.pop();
                            String t2 = t.pop();
                            String t3 = getnewt();
                            s.add(new S("/", t2, t1, t3, snum));
                            snum++;
                            t.push(t3);
                            String r0 = getnewr();
                            huibian += "MOV " + r0 + "," + t1 + "\n";
                            huibian += "DIV " + r0 + "," + t2 + "\n";
                            huibian += "MOV " + t3 + "," + r0 + "\n";
                            h += t3 + " DW ? \n";
                        } else if (tempw.equals("E>(E)")) // 7
                        {
                            sym.pop();
                            sym.pop();
                            sym.pop();
                            status.pop();
                            status.pop();
                            status.pop();
                            t.pop();
                            String t1 = t.pop();
                            t.pop();
                            t.push(t1);
                        } else  // 8
                        {
                            String tempt = sym.peek();
                            sym.pop();
                            status.pop();
                            t.pop();
                            hash.put(word.get(index - 1).content, tempt);
                            t.push(word.get(index - 1).content);
                            h += word.get(index - 1).content + " DW 2 \n";
                        }
                        System.out.println(sym);
                        System.out.println(status);
                        System.out.println(t);
                        for (int j = 0; j < s.size(); j++) {
                            s.get(j).print();
                        }
                        System.out.println(hash);
                        System.out.println();


                        int tempi = status.peek();
                        char l = tempw.charAt(0);
                        int tempp;
                        if (l == 'E') {
                            sym.push("E");
                            tempp = table[tempi][11];
                        } else {
                            sym.push("A");
                            tempp = table[tempi][10];
                        }
                        if (tempp != -1) {
                            System.out.println();
                            status.push(tempp);
                        } else {
                            System.out.println("error1");
                        }
                        System.out.println(sym);
                        System.out.println(status);
                        System.out.println(t);
                        for (int j = 0; j < s.size(); j++) {
                            s.get(j).print();
                        }
                        System.out.println(hash);
                        System.out.println();
                    }
                }
            }

        System.out.println(huibian);
        System.out.println(h);
        String total = "DATAS SEGMENT \n";
        total += h ;
        total += "DATAS ENDS\n" +
                "\n" +
                "STACKS SEGMENT\n" +
                "    ;此处输入堆栈段代码\n" +
                "STACKS ENDS\n" +
                "\n" +
                "CODES SEGMENT\n" +
                "    ASSUME CS:CODES,DS:DATAS,SS:STACKS\n" +
                "START:\n" +
                "    MOV AX,DATAS\n" +
                "    MOV DS,AX \n";
        total += huibian;
        total += " MOV AH,4CH\n" +
                "    INT 21H\n" +
                "CODES ENDS\n" +
                "    END START";
        File f = new File("C:\\Users\\ww\\Desktop\\ww523pro2\\Out2");
        FileOutputStream fos1 = new FileOutputStream(f);
        OutputStreamWriter dos1 = new OutputStreamWriter(fos1);
        dos1.write(total);
        dos1.close();



    }

    public static void gen(ArrayList<S> s , String op , String arg1 , String arg2 , String res)
    {
        s.add(new S(op,arg1,arg2,res));
    }


    public static String getnewt(){
        tempt++;
        return "t"+tempt;
    }

    public static String getnewr(){
        tempr++;
        switch (tempr)
        {
            case 0:
                return "BX";
            case 1:
                return "CX";
            case 2:
                return "AX";
            case 3:
                return "DX";

        }
          int x = tempr - 3 ;
        return "R"+x;
    }

    public static int getnum(int[][] table,int row,String ts)
    {
        switch(ts)
        {
            case "18":
                return table[row][0];
            case "40":
                return table[row][1];
            case "5":
                return table[row][2];
            case "6":
                return table[row][3];
            case "7":
                return table[row][4];
            case "8":
                return table[row][5];
            case "19":
                return table[row][6];
            case "20":
                return table[row][7];
            case "4":
                return table[row][8];
            case "end":
                return table[row][9];
            case "26":
                return table[row][9];
            case "2":
                return table[row][8];
        }
        return 1;
    }

    public static int getrnum(String s)
    {
        return s.length()-s.indexOf(">")-1;
    }

    public static String readFileContent(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr + "\n");
            }
            reader.close();
            return sbf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return sbf.toString();
    }
    public static void transfer(String input, ArrayList<Node> word) {
        String[] keyword = {"case", "char", "string", "long", "else", "if", "break", "int", "return", "void", "while", "for", "cout","do","main"};
        String[] symbol = {"<", ">", "<=", ">=", "=", "==", "!", "!=", "+", "-", "*", "/", "/*", "*/", "[", "]", "{", "}", "(", ")", ";","@"};
        String[] output = {"keyword", "symbol", "id", "num", "op", "end"};
        int index = 0;
        int row = 1;
        int bLeft = 0;
        int bRight = 0;
        char temp = input.charAt(0);
        int length = input.length();
        String tempWord = "";
        while (index < length) {
            while (input.charAt(index) == ' ' || input.charAt(index) == '\n') {
                if (index == length - 1) {
                    break;
                }

                if (input.charAt(index) == ' ') {
                    index++;
                    temp = input.charAt(index);
                } else {
                    row++;
                    index++;
                    temp = input.charAt(index);
                }
            }
            if ((temp >= 'A' && temp <= 'Z') || (temp >= 'a' && temp <= 'z')) {
                tempWord = "";
                while ((temp >= 'A' && temp <= 'Z') || (temp >= 'a' && temp <= 'z') || (temp >= '0' && temp <= '9')) {
                    tempWord += temp;
                    index++;
                    temp = input.charAt(index);
                }
                if (Arrays.asList(keyword).contains(tempWord)) {
                    switch (tempWord) {
                        case "void":
                            word.add(new com.company.Node("28", tempWord));
                            break;
                        case "int":
                            word.add(new com.company.Node("29", tempWord));
                            break;
                        case "float":
                            word.add(new com.company.Node("30", tempWord));
                            break;
                        case "char":
                            word.add(new com.company.Node("31", tempWord));
                            break;
                        case "if":
                            word.add(new com.company.Node("32", tempWord));
                            break;
                        case "else":
                            word.add(new com.company.Node("33", tempWord));
                            break;
                        case "while":
                            word.add(new com.company.Node("34", tempWord));
                            break;
                        case "do":
                            word.add(new com.company.Node("35", tempWord));
                            break;
                        case "for":
                            word.add(new com.company.Node("36", tempWord));
                        case "main":
                            word.add(new com.company.Node("37", tempWord));
                            break;
                    }
                }
                else {
                    word.add(new com.company.Node("4", tempWord));
                }
            } else if (temp >= '0' && temp <= '9') {
                tempWord = "";
                boolean one = true;
                while ((temp >= '0' && temp <= '9') || ((temp == '.') && one )) {
                    if(temp >= '0' && temp <= '9') {
                        tempWord += temp;
                        temp = input.charAt(++index);
                    }
                    else {
                        one = false;
                        tempWord += temp;
                        temp = input.charAt(++index);
                    }
                }
                if(one)
                {
                    word.add(new com.company.Node("2", tempWord));
                }
                else
                {
                    word.add(new com.company.Node("3", tempWord));
                }
            } else if (temp == '>' || temp == '<' || temp == '=' || temp == '!'|| temp == '@') {
                tempWord = "";
                tempWord += temp;
                if (input.charAt(index + 1) == '=') {
                    tempWord += '=';
                    index++;
                }
                index++;
                temp = input.charAt(index);
                switch (tempWord) {
                    case ">=":
                        word.add(new com.company.Node("14", tempWord));
                        break;
                    case "<=":
                        word.add(new com.company.Node("10", tempWord));
                        break;
                    case "==":
                        word.add(new com.company.Node("11", tempWord));
                        break;
                    case "!=":
                        word.add(new com.company.Node("12", tempWord));
                        break;
                    case ">":
                        word.add(new com.company.Node("13", tempWord));
                        break;
                    case "<":
                        word.add(new com.company.Node("9", tempWord));
                        break;
                    case "=":
                        word.add(new com.company.Node("18", tempWord));
                        break;
                    case "!":
                        word.add(new com.company.Node("op", tempWord));
                        break;
                    case "@":
                        word.add(new com.company.Node("40", tempWord));
                        break;
                }
            } else if (temp == '*') {
                tempWord = "";
                tempWord += temp;
                if (input.charAt(index + 1) == '/') {
                    tempWord += '=';
                    index++;
                }
                index++;
                temp = input.charAt(index);
                switch (tempWord) {
                    case "*/":
                        word.add(new com.company.Node("14", tempWord));
                        break;
                    case "*":
                        word.add(new com.company.Node("7", tempWord));
                        break;
                }
            } else if (temp == '/' && input.charAt(index + 1) == '*') {
                tempWord = "";
                index++;
                while (true) {
                    index++;
                    temp = input.charAt(index);
                    tempWord += temp;
                    if (input.charAt(index + 1) == '*' && input.charAt(index + 2) == '/') {
                        index += 3;
                        temp = input.charAt(index);
                        break;
                    }
                    if (index == length) {
                        break;
                    }
                }
                word.add(new com.company.Node("comments", tempWord));
            } else if (temp == '/') {
                index++;
                temp = input.charAt(index);
                word.add(new com.company.Node("8", "/"));
            } else if (temp == '+' || temp == '-') {
                tempWord = "";
                tempWord += temp;
                index++;
                temp = input.charAt(index);
                switch (tempWord) {
                    case "+":
                        word.add(new com.company.Node("5", tempWord));
                        break;
                    case "-":
                        word.add(new com.company.Node("6", tempWord));
                        break;
                }
            }
            else if (temp == '(' || temp == ')' || temp == '{' || temp == '}' || temp == '[' || temp == ']' || temp == ';' || temp == '.' || temp == ',')
            {
                tempWord = "";
                tempWord += temp;
                index++;
                temp = input.charAt(index);
                switch (tempWord) {
                    case "(":
                        word.add(new com.company.Node("19", tempWord));
                        break;
                    case ")":
                        word.add(new com.company.Node("20", tempWord));
                        break;
                    case "{":
                        word.add(new com.company.Node("23", tempWord));
                        break;
                    case "}":
                        word.add(new com.company.Node("24", tempWord));
                        break;
                    case "[":
                        word.add(new com.company.Node("21", tempWord));
                        break;
                    case "]":
                        word.add(new com.company.Node("22", tempWord));
                        break;
                    case ";":
                        word.add(new com.company.Node("26", tempWord));
                        break;
                    case ".":
                        word.add(new com.company.Node("271", tempWord));
                        break;
                    case ",":
                        word.add(new com.company.Node("272", tempWord));
                        break;
                    case ":":
                        word.add(new com.company.Node("25", tempWord));
                        break;
                }
            }
            else if (temp == '#') {
                temp = input.charAt(index);
                word.add(new com.company.Node("end", "#"));
                break;
            }
            else if (temp == '"') {
                tempWord = "";
                tempWord += temp;
                word.add(new com.company.Node("symbol", tempWord));
                tempWord = "";
                while (true) {
                    index++;
                    temp = input.charAt(index);
                    tempWord += temp;
                    if (input.charAt(index + 1) == '"') {
                        index += 2;
                        temp = input.charAt(index);
                        break;
                    }
                    if (index == length) {
                        break;
                    }
                }
                word.add(new com.company.Node("output", tempWord));
                tempWord = "";
                char temp1 = '"';
                tempWord += temp1;
                word.add(new Node("symbol", tempWord));

            }
            else {
                System.out.println("error" + index);
                index++;
            }
        }

    }

}

