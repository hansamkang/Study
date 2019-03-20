import java.io.*;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.Scanner;

abstract class AbstractSort {
    public static void sort(Comparable[] a){};

    protected static boolean less(Comparable v, Comparable w)
    {
        return v.compareTo(w) < 0;
    }

    protected static void exch(Comparable[] a , int i , int j)
    {
        Comparable t = a[i]; a[i] = a[j]; a[j] = t;
    }

    protected static void show(Comparable[] a)
    {
        for (int i = 0 ; i < a.length; i++) System.out.print(a[i] +" ");
    }

    protected static boolean isSorted(Comparable[] a)
    {
        for(int i = 1 ; i < a.length; i++)
        {
            if(less(a[i],a[i-1]))
                return false;
        }
        return true;
    }
}

class Selection extends AbstractSort{
    public static void sort(Comparable[] a)
    {
        int n = a.length;
        for(int i =0 ; i< n-1; i++){
            int min = i;
            for(int j = i+1; j < n; j++){
                if(less(a[j],a[min])){
                    min = j;
                }
            }
            exch(a, i, min);
        }
        assert isSorted(a);
    }
}

class Insertion extends AbstractSort{
    public static void sort(Comparable[] a){
        int n  = a.length;
        for(int i = 1; i< n; i++)
        {
            for (int j = i; j > 0 && less(a[j], a[j-1]); j--){
                exch(a, j, j-1);
            }
        }
        assert isSorted(a);
    }
}

class Shell extends AbstractSort{
    public static void sort(Comparable[] a){
        int n = a.length;
        int h = 1;
        while (h < n/3) h = 3 * h +1;

        while (h >= 1) {
            for(int i = h ; i< n; i++){
                for(int j = i; j >= h && less(a[j], a[j-h]);){
                    exch(a, j, j - h);
                    j -= h;
                }
            }
            h /= 3;
        }
    }
}

class TopDownMerge extends AbstractSort{
    private static void merge(Comparable[] list, Comparable[] aux, int low, int mid, int high)
    {
        for (int k = low; k <= high; k++)
        {
            aux[k] = list[k];
        }

        for (int i = low, k = low, q = mid+1; i <= high; i++) {
            if (q > high || k <= mid && aux[k].compareTo(aux[q]) <= 0)
                list[i] = aux[k++];
            else
                list[i] = aux[q++];
        }
    }

    public static void sort(Comparable[] list){
        Comparable[] aux = new Comparable[list.length];
        sort(list,aux,0,list.length-1);
    }

    private static void sort(Comparable[] list, Comparable[] aux, int low, int high)
    {
        if (high <= low) return;

        int mid = low + (high - low) / 2;
        sort(list, aux, low, mid);
        sort(list, aux, mid + 1, high);
        merge(list, aux, low, mid, high);
    }
}

class BottomUpMerge extends  AbstractSort{
    private  static void merge(Comparable[] in, Comparable[] out, int low, int mid, int high){
        for (int i = low, k = low, q = mid+1; i <= high; i++) {
            if (q > high || k <= mid && in[k].compareTo(in[q]) <= 0)
                out[i] = in[k++];
            else
                out[i] = in[q++];
        }
    }

    public static void sort(Comparable[] a){
        Comparable[] src = a, dst = new Comparable[a.length], tmp;
        int N = a.length;
        for(int n = 1; n <N; n*=2){
            for(int i = 0; i<N; i += 2*n)
                merge(src, dst, i, i+n-1, Math.min(i+2*n-1,N-1));
            tmp = src; src = dst; dst = tmp;
        }
        if (src != a) System.arraycopy(src, 0, a, 0, N);
    }
}

public class HW1 {
    public static void main(String [] args)  {
        Scanner scan = new Scanner(System.in);
        String inputFileName;
        System.out.println("Please Input file name");
        inputFileName = scan.nextLine();
        ArrayList<String> arrayList = new ArrayList<String>();

        try {
            FileReader file = new FileReader(inputFileName);
            BufferedReader fileBuffer = new BufferedReader(file);
            String line;
            while((line = fileBuffer.readLine()) != null){
                StringTokenizer stok = new StringTokenizer(line);
                while(stok.hasMoreTokens()){
                    arrayList.add(stok.nextToken());
                }
            }
            fileBuffer.close();
            file.close();
        }catch(FileNotFoundException e){
            System.out.println("Not Found File or Wrong input file name. Program exit.");
            System.exit(0);
        }catch(IOException e){
            System.out.println(e);
        }

        System.out.println("1. Number of Words = "+arrayList.size());

        long startTime, endTime;

        String [] Temp = new String[arrayList.size()];
        for(int i = 2 ; i<7; i++){
            for(int j =0 ; j<arrayList.size(); j++){
                Temp[j] = arrayList.get(j);
            }
            switch(i){
                case 2:
                    startTime = System.currentTimeMillis();
                    Selection.sort(Temp);
                    endTime = System.currentTimeMillis();
                    System.out.println("2. Selection Sort: Check Sorted = "+ AbstractSort.isSorted(Temp)+", Time = " + (endTime-startTime) +"ms");
                    break;
                case 3:
                    startTime = System.currentTimeMillis();
                    Insertion.sort(Temp);
                    endTime = System.currentTimeMillis();
                    System.out.println("3. Insertion Sort: Check Sorted = "+ AbstractSort.isSorted(Temp)+", Time = " + (endTime-startTime) +"ms");
                    break;
                case 4:
                    startTime = System.currentTimeMillis();
                    Shell.sort(Temp);
                    endTime = System.currentTimeMillis();
                    System.out.println("4. Shell Sort: Check Sorted = "+ AbstractSort.isSorted(Temp)+", Time = " + (endTime-startTime) +"ms");
                    break;
                case 5:
                    startTime = System.currentTimeMillis();
                    TopDownMerge.sort(Temp);
                    endTime = System.currentTimeMillis();
                    System.out.println("5. TopDownMerge Sort: Check Sorted = "+ AbstractSort.isSorted(Temp)+", Time = " + (endTime-startTime) +"ms");
                    break;
                case 6:
                    startTime = System.currentTimeMillis();
                    BottomUpMerge.sort(Temp);
                    endTime = System.currentTimeMillis();
                    System.out.println("6. BottomUpMerge Sort: Check Sorted = "+ AbstractSort.isSorted(Temp)+", Time = " + (endTime-startTime) +"ms");
                    break;
            }
        }
    }
}
