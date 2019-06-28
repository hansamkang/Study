package HW;

import java.util.*;
import java.io.*;

class GoodsSet implements Comparator<GoodsSet>{
    int a;
    int b;

    int getVA(){    return this.a;    }
    int getVB(){    return this.b;    }

    GoodsSet(){}
    GoodsSet(int a, int b){
        if(a<=b){
            this.a = a; this.b = b;
        }
        else {
            this.a = b; this.b = a;
        }
    }

    @Override
    public int compare(GoodsSet o1, GoodsSet o2) {
        if(o1.getVA() < o2.getVA()) return -1;
        else if(o1.getVA() > o2.getVA()) return 1;
        else {
            if(o1.getVB() < o2.getVB()){
                return -1;
            }
            else if(o1.getVB() > o2.getVB()){
                return 1;
            }
            else return 0;
        }
    }
}

public class  HW3 {
    public static void main(String [] args){
        System.out.print("Please Input(file name, k, Confidence, Support) : ");
        Scanner sc = new Scanner(System.in);
        String inputFileName = sc.next();
        int k = sc.nextInt();
        double inputConfidence = sc.nextDouble();
        double inputSupport = sc.nextDouble();
        sc.close();

        File file = new File(inputFileName);
        TreeMap<GoodsSet, Integer> map = new TreeMap<GoodsSet, Integer>(new GoodsSet());
        HashMap<Integer, Integer> goodsMap = new HashMap<Integer, Integer>();

        int totalCartNum = 0;

        sc = null;
        try{
            sc = new Scanner(file);
            totalCartNum = sc.nextInt();

            for(int i = 0 ; sc.hasNextLine(); i++){
                String line = sc.nextLine();
                StringTokenizer stok = new StringTokenizer(line);

                ArrayList<Integer> list = new ArrayList<Integer>();

                for(int j = 0; stok.hasMoreTokens(); j++)
                {
                    if(j<2) stok.nextToken();
                    else list.add(Integer.parseInt(stok.nextToken()));
                }

                for(int j = 0 ; j < list.size(); j++)
                {
                    if(goodsMap.containsKey(list.get(j))){
                        goodsMap.put(list.get(j), goodsMap.get(list.get(j))+1);
                    }else{
                        goodsMap.put(list.get(j), 1);
                    }

                    if(j < list.size()-1) {
                        for (int m = j + 1; m < list.size(); m++) {
                            GoodsSet key = new GoodsSet(list.get(j), list.get(m));
                            if (map.containsKey(key)) {
                                map.put(key, map.get(key) + 1);
                            } else {
                                map.put(key, 1);
                            }
                        }
                    }
                }
            }

        }catch(FileNotFoundException e){
            System.out.println(e);
        }

        ArrayList <GoodsSet> list = new ArrayList<GoodsSet>(map.keySet());

        Collections.sort(list, new Comparator<Object>(){
            public int compare(Object a, Object b){
                Object v1 = map.get(a);
                Object v2 = map.get(b);
                return ((Comparable<Object>) v2).compareTo(v1);
            }
        });

        for(int i=0 ; i<k && i<totalCartNum; i++)
        {
            System.out.println((i+1)+" : ["+ list.get(i).getVA()+", "+ list.get(i).getVB()+"] " + map.get(list.get(i)));
        }

        double test = map.get(list.get(0)) / (double)totalCartNum;

        for(int i = 0; (map.get(list.get(i)) / (double)totalCartNum) > inputSupport; i++) {
            double supportSet = map.get(list.get(i))/(double)totalCartNum;
            double supportA = goodsMap.get(list.get(i).getVA())/(double)totalCartNum;
            double supportB = goodsMap.get(list.get(i).getVB())/(double)totalCartNum;
            double confidence1 = supportSet/supportA;
            double confidence2 = supportSet/supportB;

            if(confidence1>inputConfidence)
                System.out.println(list.get(i).getVA() + " -> " + list.get(i).getVB() +" : Support = "+ supportSet + ", Confidence = " + confidence1);
            if(confidence2>inputConfidence)
                System.out.println(list.get(i).getVB() + " -> " + list.get(i).getVA() +" : Support = "+ supportSet + ", Confidence = " + confidence2);
        }
    }
}

