package Sorting;

import java.util.Comparator;

public class BottomUpMerge extends  AbstractSort{
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
