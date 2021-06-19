package com.company;

public class S {
    String op = null;
    String arg1 = null;
    String arg2 = null;
    String res = null;

    public S(String op, String arg1, String arg2, String res) {
        this.op = op;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.res = res;
    }

    public void print()
    {
        System.out.print("[ "+this.op + " , " + this.arg1 + " , "+ this.arg2 + " , "+ this.res + " ]");

    }
}
