package utills;

import java.util.HashMap;
import java.util.Map;

public class Stash {
    private static final Map<String, Object> stash = new HashMap<>();
    public Stash(){
    }
    public static Map<String, Object> getInstance(){ return stash;}
}
