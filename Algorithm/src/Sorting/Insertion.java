package Sorting;

public class Insertion extends AbstractSort{
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

    public static void main(String[] args){
        Integer[] a = {10, 4, 5, 2, 1, 8, 3, 6 };
        String [] b = { "bbb", "ddd", "ggg", "iii", "lll", "hhh", "ppp"};
        Insertion.sort(b);
        Insertion.show(b);
    }
}
