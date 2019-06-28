package PracticalTest;

import java.util.*;
import java.io.*;

public class Strassen {
    public void strasen(int n, int a[][], int b[][], int c[][]){
        if (n<3){
            int sum;
            for(int i= 1; i<= n; i++){
                for(int j = 1; j <= n; j++){
                    sum = 0;
                    for(int k = 1; k <=n; k++)
                    {
                        c[i][j] += a[i][k] * b[k][j];
                    }
                }

            }
        }
        else {
            int mid = n/2;
            int P1[][] = new int[n/2][n/2];
            int P2[][] = new int[n/2][n/2];
            int P3[][] = new int[n/2][n/2];
            int P4[][] = new int[n/2][n/2];
            int P5[][] = new int[n/2][n/2];
            int P6[][] = new int[n/2][n/2];
            int P7[][] = new int[n/2][n/2];

            int temp1[][] = new int[n/2][n/2];
            int temp2[][] = new int[n/2][n/2];
        }
    }
    public static void main(String args[]){

    }
}


