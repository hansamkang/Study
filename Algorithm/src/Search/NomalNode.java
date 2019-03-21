package Search;

public class NomalNode<K, V> {
    K key;
    V value;
    NomalNode<K, V> next;

    public NomalNode(K key, V value, NomalNode<K,V> next){
        this.key = key;
        this.value = value;
        this.next = next;
    }

}
