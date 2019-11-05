package com.saili.hzz.core.util;

import java.util.Comparator;
import com.google.gson.JsonObject;

public class SortComparator implements Comparator<JsonObject> {
    
    private String sortItem;
    private String sortType;
    private String sortDire;
    
    public SortComparator(String sortItem, String sortType, String sortDire) {
        this.sortItem = sortItem;
        this.sortType = sortType;
        this.sortDire = sortDire;
    }
    
    @Override
    public int compare(JsonObject o1, JsonObject o2) {
        String value1 = o1.getAsJsonObject().get(sortItem).getAsString();
        String value2 = o2.getAsJsonObject().get(sortItem).getAsString();
        if ("int".equalsIgnoreCase(this.sortType)) { // int sort
            int int1 = Integer.parseInt(value1);
            int int2 = Integer.parseInt(value2);
            if ("asc".equalsIgnoreCase(this.sortDire)) {
                return int1 - int2;
            } else if ("desc".equalsIgnoreCase(this.sortDire)) {
                return int2 - int1;
            } else {
                return 0;
            }      
        } else if ("string".equalsIgnoreCase(this.sortType)) { // string sort
            if ("asc".equalsIgnoreCase(this.sortDire)) {
                return value1.compareTo(value2);
            } else if ("desc".equalsIgnoreCase(this.sortDire)) {
                return value2.compareTo(value1);
            } else {
                return 0;
            }
        } else { // nothing sort
            return 0;
        }        
    }
}