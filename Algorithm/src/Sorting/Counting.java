package Sorting;

public class Counting {
    public static void sort(int[] A, int k){
        int n = A.length;
        int [] C = new int[k];
        int [] B = new int[n];

        for(int i = 0; i < n; i++)
            C[A[i]]++;
        for(int i = 1; i <k; i++)
            C[i] += C[i-1];
        for(int i = n-1; i>=0; i--)
            B[--C[A[i]]] = A[i];

        System.arraycopy(B,0, A,0, n);
    }

    public static void main(String [] args)
    {
        int [] A= {10, 4, 5, 2, 1, 8, 3, 6};
        sort(A, 11);
    }
}
