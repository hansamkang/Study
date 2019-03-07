package Sorting;

public abstract class AbstractSort {
    public static void sort(Comparable[] a){};

    protected static boolean less(Comparable v, Comparable w)//v가 w다 작을때 true를 리턴
    {
        return v.compareTo(w) < 0;
    }

    protected static void exch(Comparable[] a , int i , int j) //Swap
    {
        Comparable t = a[i]; a[i] = a[j]; a[j] = t;
    }

    protected static void show(Comparable[] a)
    {
        for (int i = 0 ; i < a.length; i++) System.out.print(a[i] +" "); // 내용 출력
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
