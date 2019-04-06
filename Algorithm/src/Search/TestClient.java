package Search;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class TestClient {
    public static void main(String[] args){
        BST<String, Integer> st = new BST<String, Integer>();
        File file;
        final JFileChooser fc = new JFileChooser();
        if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            file = fc.getSelectedFile();
        else{
            JOptionPane.showMessageDialog(null, "파일을 선택하세요>", "오류", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Scanner sc = null;  // file 에 있는 단어들을 키로 해서 테이블에 차례대로 저장
        try{
            sc = new Scanner(file);
            for(int i=0 ; sc.hasNext(); i++) {String key = sc.next(); st.put(key, i);}
            for(String s: st.keys()) System.out.println(s +" " + st.get(s));
        } catch(FileNotFoundException e) { e.printStackTrace();}
        if(sc != null)
            sc.close();
    }
}
