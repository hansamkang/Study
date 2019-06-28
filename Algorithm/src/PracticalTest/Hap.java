package PracticalTest;

public class Hap {
    public static void maxSubList(double[] A){
        double [] B;
        double max;

        int n = A.length;
        B = new double[n];
        max = B[0] = A[0];
        int start,end,temp;
        temp = start = end = 0;

        for(int i = 1 ; i< n ; i++)
        {
            if(B[i-1]<=0){
                B[i] = A[i];
                temp = i;//
            }
            else{
                B[i] = B[i-1] + A[i];
            }

            if(B[i] > max)
            {
                start = temp;//
                max = B[i];
                end = i;//
            }
        }
        System.out.println("최대합 = " + max);
        System.out.println("인덱스들");
        for(int i = start; i<=end; i++){
            System.out.println("배열["+i+"] = " + A[i] + " | 누적 합" + B[i]);
        }
    }

    public static void main(String args[]){
        double [] acac = {2.2, 3.4, -2.1, 0.1, -7,1, 0.2, 0.7, 0.9, 3.1,-1.2, 2.2, 1.3, 0.1, 2.1,-20.1, 1.1, 2.2, 3.3 };
        maxSubList(acac);
    }
}
