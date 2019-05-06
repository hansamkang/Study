package HW;

import java.io.*;
import java.util.*;

class MyComp implements Comparator<String>{
    public int compare(String aStr, String bStr){
        return aStr.compareTo(bStr);
    }
}
public class tests {
    public static void main(String args[]) {
        MyComp mc = new MyComp();
        TreeSet<String> ts = new TreeSet<String>(mc.reversed());

        ts.add("C"); ts.add("A"); ts.add("B"); ts.add("E"); ts.add("F"); ts.add("D");

        System.out.println(ts);
    }
}
