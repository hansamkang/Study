package HW;
import java.io.*;
import java.util.*;

class Node<K, V> {
    K key;
    V value;
    int N;
    int aux;

    Node<K, V> next, left, right;
    Node<K,V> parent;

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
class BST<K extends Comparable<K>, V> {
    protected Node<K, V> root;
    public int size() { return (root != null) ? root.N : 0;   }
    protected Node<K,V> treeSearch(K key){
        Node<K,V> x = root;
        while(true){
            int cmp = key.compareTo(x.key);
            if(cmp ==0) return x;
            else if(cmp <0){
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
        if(cmp == 0) x.value = val;
        else{
            Node<K,V> newNode = new Node<K,V>(key, val);
            if(cmp <0) x.left = newNode;
            else       x.right = newNode;
            newNode.parent = x;
            rebalanceInsert(newNode);
        }
    }
    protected void rebalanceInsert(Node<K,V> x){
        resetSize(x.parent, 1);
    }
    protected void rebalanceDelete(Node<K,V> p, Node<K,V> deleted){
        resetSize(p, -1);
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

        if (!key.equals(x.key)) return;

        if (x == root || isTwoNode(x)) {
            if (isLeaf(x)) {
                root = null;
                return;
            }
            else if (!isTwoNode(x)) {
                root = (x.right == null) ? x.left : x.right;
                root.parent = null;
                return;
            } else {
                y = min(x.right);
                x.key = y.key;
                x.value = y.value;
                p = y.parent;
                relink(p, y.right, y == p.left);

                rebalanceDelete(p, y);
            }
        }
        else {
            p = x.parent;
            if (x.right == null)
                relink(p, x.left, x == p.left);
            else if (x.left == null)
                relink(p, x.right, x == p.left);
            rebalanceDelete(p, x);
        }
    }
    boolean isLeaf(Node<K,V> x){
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
    private Node<K, V> floor(Node<K,V>x, K key){
        if(x == null) return null;
        int cmp = key.compareTo(x.key);
        if(cmp == 0) return x;
        if(cmp < 0) return floor(x.left, key);
        Node<K,V> t = floor(x.right, key);
        if(t != null) return t;
        else return x;
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

public class HW2 {
    public static void main(String [] args){
        String inputFileName1;
        String inputFileName2;
        System.out.print("Please Input First file name? : ");
        Scanner scan = new Scanner(System.in);
        inputFileName1 = scan.nextLine();
        System.out.print("Please Input Second file Name? : ");
        scan = new Scanner(System.in);
        inputFileName2 = scan.nextLine();

        BST<String, Integer> bst1 = createBST(inputFileName1);
        BST<String, Integer> bst2 = createBST(inputFileName2);
        Iterator<String> it1= bst1.keys().iterator();
        Iterator<String> it2= bst2.keys().iterator();

        String keyA = it1.next();
        String keyB = it2.next();

        int a,b;
        int and = 0, or = 0, shingleA =0, shingleB =0;

        while(it1.hasNext() && it2.hasNext()){
            int cmp = keyA.compareTo(keyB);

            if(cmp == 0){
                a = bst1.get(keyA);
                b = bst2.get(keyB);
                and += (a<= b) ? a : b;
                or +=  (a<= b) ? b : a;
                shingleA += a;
                shingleB += b;
                keyA = it1.next();
                keyB = it2.next();
            }
            else if(cmp <1){
                a = bst1.get(keyA);
                or += a;
                shingleA +=a;
                keyA= it1.next();
            }
            else{
                b = bst2.get(keyB);
                or += b;
                shingleB += b;
                keyB = it2.next();
            }
        }
        while(it1.hasNext()) {
            a = bst1.get(keyA);
            or += a;
            shingleA +=a;
            keyA= it1.next();
        }
        while(it2.hasNext()){
            b = bst2.get(keyB);
            or += b;
            shingleB += b;
            keyB = it2.next();
        }
        System.out.println("Single number of File " + inputFileName1 + " = "+ shingleA);
        System.out.println("Single number of File " + inputFileName2 + " = "+ shingleB);
        System.out.println("The common number of single files in two files = " + and);
        System.out.println("Similarity = "+ (double)and/or);
    }

    public static BST<String, Integer> createBST(String inputFileName) {
        BST<String, Integer> bst = new BST<String, Integer>();
        ArrayList<String> list = new ArrayList<String>(5);

        try {
            FileReader file = new FileReader(inputFileName);
            BufferedReader fileBuffer = new BufferedReader(file);
            StringBuilder sb= new StringBuilder();
            String line;
            while((line = fileBuffer.readLine()) != null){
                StringTokenizer stok = new StringTokenizer(line);
                while(stok.hasMoreTokens()){
                    if(list.size()<4)
                        list.add(stok.nextToken());
                    else {
                        list.add(stok.nextToken());
                        for (int i = 0; i < 5; i++) {
                            sb.append(list.get(i));
                            if (i == 4) break;
                            sb.append(" ");
                        }
                        if (bst.get(sb.substring(0)) == null)
                            bst.put(sb.substring(0), 1);
                        else
                            bst.put(sb.substring(0), bst.get(sb.substring(0)).intValue() + 1);
                        sb.setLength(0);
                        list.remove(0);
                    }
                }
            }
            fileBuffer.close();
            file.close();
        }catch(FileNotFoundException e){
            System.out.println("Not Found File or Wrong input file name. Program exit.");
            System.exit(0);
        }catch(IOException e){
            System.out.println(e);
        }
        return bst;
    }
}

