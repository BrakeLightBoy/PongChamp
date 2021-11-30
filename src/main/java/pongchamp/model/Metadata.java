package pongchamp.model;

import java.util.Map;
import java.util.HashMap;

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
