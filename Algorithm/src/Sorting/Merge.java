package Sorting;

public class Merge extends AbstractSort{
    private static void merge(Comparable[] list, Comparable[] aux, int low, int mid, int high)
    {
        for (int k = low; k <= high; k++)
        {
            aux[k] = list[k];
        }

        // aux[] 배열을 비교하여 병합된 결과를 a[] 배열에 다시 저장

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
        if (high <= low) return; //데이터 리스트의 원소가 1개라면 return

        int mid = low + (high - low) / 2;
        sort(list, aux, low, mid);
        sort(list, aux, mid + 1, high);
        merge(list, aux, low, mid, high);
    }

    public static void main(String[] args)
    {
        Integer[]A = { 21,6,16,9,3,30,18,25 };
        sort(A);
    }
}
