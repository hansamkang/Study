package HW;

import java.util.*;
import java.io.*;

public class HW3E {
    public static void main(String [] args){
        System.out.print("파일이름 입력: ");
        Scanner sc = new Scanner(System.in);
        String fileName = sc.nextLine();
        File file = new File(fileName);

        HashMap<String, Integer> map = new HashMap<String,Integer>();

        sc = null;

        try {
            sc = new Scanner(file);
            for (int i = 0; sc.hasNext(); i++) {
                String key = sc.next();
                if (map.containsKey(key)) {   //만약 key가 map에 존재 할 시에
                    map.put(key, map.get(key) + 1);
                } else {                       // 만약 key가 map에 존재 안할 시에
                    map.put(key, 1);
                }
            }
        } catch(FileNotFoundException e){ System.out.println(e);}

        ArrayList<Object> list =  new ArrayList<Object>(map.keySet());

        Collections.sort(list, new Comparator<Object>(){
            public int compare(Object a, Object b){
                Object v1 = map.get(a);
                Object v2 = map.get(b);
                return ((Comparable<Object>) v2).compareTo(v1);
            }
        } );

        //System.out.println(list);   // test

        System.out.println("Keywards Top 10");
        for(int i = 0 ; i<10; i++){
            System.out.println("["+i+"] = " + list.get(i) + " | " + map.get(list.get(i)));
        }

    }
}
