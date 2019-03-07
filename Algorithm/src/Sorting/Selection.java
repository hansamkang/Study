package Sorting;

public class Selection extends AbstractSort{
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

    public static void main(String[] args){
        Integer[] a = {10, 4, 5, 2, 1, 8, 3, 6 };
        Selection.sort(a);
        Selection.show(a);
    }
}
