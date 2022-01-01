package pongchamp.pongchamp.model;

import java.util.HashMap;
import java.util.Map;

public class Metadata {
    private final Map<String,Object> map;

    public Metadata(){
        map = new HashMap<>();
    }

    public void put (String key,Object data){
        map.put(key,data);
    }
    public Object get(String key){
        return map.get(key);
    }
    public Object remove(String key){
        return map.remove(key);
    }

}
