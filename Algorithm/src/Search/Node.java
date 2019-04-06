package Search;

public class Node<K, V> {
    K key;
    V value;
    int N;  // 자손노드 +1 (ordered 연산)
    int aux;    // AVL 트리나 RB 트리에 사용


    Node<K, V> next, left, right;
    Node<K,V> parent;   // AVL or RB

    public Node(K key, V val){
        this.key = key;
        this.value = val;
        this.N =1;
    }
    public Node(K key, V value, Node<K,V> next){
        this.key = key;
        this.value = value;
        this.next = next;
    }
    public int getAux(){    return aux;    }
    public void setAUx(int value) {     aux = value;    }
}
