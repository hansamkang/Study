package Search;
import java.util.ArrayList;

public class BST<K extends Comparable<K>, V> {
    protected Node<K, V> root;
    public int size() { return (root != null) ? root.N : 0;   }
    protected Node<K,V> treeSearch(K key){
        Node<K,V> x = root; // BST에 대한 모든 연산은 루트부터 식작
        while(true){
            int cmp = key.compareTo(x.key);
            if(cmp ==0) return x;   //순회 종료
            else if(cmp <0){    //x.key 보다 작을 경우, 왼쪽으로
                if(x.left == null) return x;
                else x = x.left;
            }
            else {
                if(x.right == null) return x;
                else x = x.right;
            }

        }
    }
    public V get(K key){
        if(root == null) return null;
        Node<K,V> x = treeSearch(key);
        if(key.equals(x.key))
            return x.value;
        else
            return null;
    }
    public void put(K key, V val){
        if(root == null) {  root = new Node<K,V>(key, val); return;    }
        Node<K,V> x = treeSearch(key);
        int cmp = key.compareTo(x.key);
        if(cmp == 0) x.value = val; // 키가 존재하므로, 값을 reset
        else{   // 없는 키: 새로운 노드를 생성하여 x의 자식으로 추가
            Node<K,V> newNode = new Node<K,V>(key, val);
            if(cmp <0) x.left = newNode;
            else       x.right = newNode;
            newNode.parent = x;
            rebalanceInsert(newNode);
        }
    }
    protected void rebalanceInsert(Node<K,V> x){
        resetSize(x.parent, 1); // root 까지 조상 노드들의 size 1 증가
    }
    protected void rebalanceDelete(Node<K,V> p, Node<K,V> deleted){
        resetSize(p, -1);   //root까지 조상 노드들의 size를 1 감소
    }
    private void resetSize(Node<K,V> x, int value){
        for(; x != null; x = x.parent)
            x.N += value;
    }
    public Iterable<K> keys(){
        if(root == null) return null;
        ArrayList<K> keyList = new ArrayList<K>(size());
        inorder(root, keyList);
        return keyList;
    }
    private void inorder(Node<K,V>x, ArrayList<K> keyList){
        if(x != null){
            inorder(x.left, keyList);
            keyList.add(x.key);
            inorder(x.right, keyList);
        }
    }
    public void delete(K key) {
        if (root == null) return;
        Node<K, V> x, y, p;
        x = treeSearch(key);

        // key가 없는 경우,
        if (!key.equals(x.key)) return;

        // 루트이거나 자식이 두 개인 노드
        if (x == root || isTwoNode(x)) {
            if (isLeaf(x)) {
                root = null;
                return;
            } // 루트가 리프
            else if (!isTwoNode(x)) { // fnxm
                root = (x.right == null) ? x.left : x.right;
                root.parent = null;
                return;
            } else {
                y = min(x.right); // inorder.successor
                x.key = y.key;
                x.value = y.value;
                p = y.parent;   // y의 자식을 p의 자식으로(Y 삭제)
                relink(p, y.right, y == p.left);
                // y의 조상 노드들의 size를 감소
                rebalanceDelete(p, y);
            }
        }
        else {   // 자식 <= 1이고, 루트 아님
            p = x.parent;
            if (x.right == null)
                relink(p, x.left, x == p.left);
            else if (x.left == null)
                relink(p, x.right, x == p.left);
            rebalanceDelete(p, x);
        }
    }
    boolean isLeaf(Node<K,V> x){    // custum
        if(root == null) return false;
        if(x.left == null && x.right == null)
        {
            return true;
        }
        else{
            return false;
        }
    }
    boolean isTwoNode(Node<K,V> x){
        if(root == null) return false;
        if(x.left != null && x.right != null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public K min(){
        if(root == null) return null;
        Node<K, V> x = root;
        while(x.left != null)
            x = x.left;
        return x.key;
    }
    public Node<K,V> min(Node<K,V> x){
        if(x == null) return null;
        Node<K, V> y = x;
        while(y.left != null)
            y = y.left;
        return y;
    }
    public K max(){
        if(root == null) return null;
        Node<K, V> x = root;
        while(x.right != null)
            x = x.right;
        return x.key;
    }
    protected void relink(Node<K,V> parent, Node <K,V> child, boolean makeLeft){
        if(child != null) child.parent = parent;
        if(makeLeft) parent.left = child;
        else parent.right = child;
    }
    public K floor(K key){
        if(root == null || key == null) return null;
        Node<K, V> x = floor(root, key);
        if(x == null) return null;
        else return x.key;
    }
    private Node<K, V> floor(Node<K,V>x, K key){    //key보다 작거나 같은 키들 중에서 제일 큰 키
        if(x == null) return null;
        int cmp = key.compareTo(x.key);
        if(cmp == 0) return x;  // key와 동일한 키를 가진 노드.
        if(cmp < 0) return floor(x.left, key);  // key보다 크다면 계속 왼쪽으로
        Node<K,V> t = floor(x.right, key);  // key가 클 경우 , 오른쪽으로
        if(t != null) return t; // 오른쪽에 적은 키가 있을 경우
        else return x;  // 오른쪽에 작은 키가 없을 경우
    }
    public int rank(K key){
        if(root == null || key == null) return 0;
        Node<K, V> x = root;
        int num =0;
        while(x != null){
            int cmp = key.compareTo(x.key);
            if(cmp <0) x = x.left;
            else if (cmp >0) {
                num += 1 + size(x.left);
                x = x.right;
            }
            else {
                num += size(x.left); break;
            }
        }
        return num;
    }
    private int size(Node<K,V> x) { return (x != null) ? x.N:0; }
    public K select(int rank){
        if (root == null || rank < 0 || rank >= size())
            return null;
        Node<K,V> x = root;
        while(true){
            int t = size(x.left);
            if(rank <t)
                x = x.left;
            else if(rank>t){
                rank = rank -t -1;
                x = x.right;
            }
            else return x.key;
        }
    }
}
