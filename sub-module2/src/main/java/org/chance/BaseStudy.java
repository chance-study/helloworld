package org.chance;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gengchao on 16/5/2.
 */
public class BaseStudy {

    public static void main(String[] args) {
        Map<String,String> map = new HashMap<>();
        map.put("string","String");
        map.put("S2", "s2");
        map.forEach((key,value)->{
            System.out.println(key+","+value);
        });

        for(Map.Entry entry:map.entrySet()){
            System.out.println(entry.getKey()+","+entry.getValue());
        }

        for(String key:map.keySet()){
            System.out.println(key+","+map.get(key));
        }

    }
}
