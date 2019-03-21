package Search;
import java.util.ArrayList;

public class SequentialSearchST <K, V>{
    private NomalNode<K, V> first;  // 첫 번째 노드에 대한 참조를 유지. 초기값 = null
    int n;  // 연결 리스트의 노드 수를 유지. 초기값 0

    public V get(K key){
        for(NomalNode<K,V> x = first; x != null; x = x.next)
        {
            if(key.equals(x.key))
                return x.value; // 검색 성공
            // 같은 값 없다면 Pass
        }
        return null;    //검색한 값 없음
    }
    public void put(K key, V value){
        for(NomalNode<K, V> x = first; x!= null; x = x.next)
            if(key.equals(x.key)){  // 키가 잇을 경우, 값만 변경
                x.value = value;
                return;
            }
        first = new NomalNode<K,V>(key, value, first);
            n++;
    }
    public void delete(K key){
        if(key.equals(first.key)) {
            first = first.next;
            n--;
            return;
        }
        for(NomalNode<K, V> x = first; x.next != null; x = x.next){ // 삭제할 노드를 검색
            if(key.equals(x.next.key)){
                x.next  = x.next.next;
                return;
            }
        }
    }
    public Iterable<K> keys(){
        ArrayList<K> keyList = new ArrayList<K>(n);
        for(NomalNode<K, V> x = first; x!= null; x = x.next )
            keyList.add(x.key);
        return keyList;
    }
    public boolean contains(K key){ return get(key) != null;    }
    public boolean isEmpty() {  return n == 0;  }
    public int size()   {   return n;}

    public static void main (String [] args)
    {
        SequentialSearchST<String,String> card = new SequentialSearchST<String,String>();

        card.put("푸른눈의백룡", "000-001");
        card.put("블랙매지션", "000-002");
        card.put("붉은눈의흑룡", "000-003");

        System.out.println(card.get("푸른눈의백룡"));
        System.out.println(card.isEmpty());
        System.out.println(card.size());
        card.delete("푸른눈의백룡");
        System.out.println(card.contains("푸른눈의백룡"));
    }
}
