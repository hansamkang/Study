package HW;

import java.io.*;
import java.util.*;


public class tests {
    public static void main(String args[]) {
        System.out.println("파일 이름 입력");

        Scanner sc  = new Scanner(System.in);
        String filename = sc.nextLine();
        File file = new File(filename);

        HashMap<String, Integer> map = new HashMap<String, Integer>();

        sc = null;

        try{
            sc = new Scanner(file);
            for(int i = 0 ; sc.hasNext(); i++){
                String key = sc.next();
                if(map.containsKey(key)){
                    map.put(key, map.get(key)+1);
                }
                else{
                    map.put(key, 1);
                }
            }
        }
        catch(FileNotFoundException e){     System.out.println(e + "파일오류났음여");  }

        ArrayList<Object> list = new ArrayList<Object>(map.keySet());

        Collections.sort(list, new Comparator<Object>(){
            public int compare(Object a, Object b){
                Object v1 = map.get(a);
                Object v2 = map.get(b);

                return ((Comparable)v2).compareTo(v1);
            }
        });

        System.out.println("top 10");
        for(int i = 0 ; i<10 ; i++)
        {
            System.out.println("top ["+ i+ "] ="+ "key = " + list.get(i) +" value = "+ map.get(list.get(i)) );
        }


    }
}
