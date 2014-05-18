package com.woodle.pojo;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 通用查询接口的返回信息.  [count , recordMap]
 * User: wuqingchao
 * Time: 14-5-9 下午9:50
 */
public class ResultMap<K extends Serializable, V extends Serializable> implements Serializable {

    private static  final long serialVersionUID = 36298820761265L;
    private Map<K, V> map;

    private int count = 0;

    public ResultMap(){
        this(new HashMap<K, V>());
    }

    public ResultMap(Map<K, V> map){
        this(0, map);
    }

    public ResultMap(int count){
        this(count, new HashMap<K, V>());
    }

    public ResultMap(int count, Map<K,V> map){
        if(map == null)
            map = Collections.emptyMap();
        this.map = map;
        this.count = Math.max(count, map.size());
    }

    private void add(Map.Entry<K, V> e) {
         this.map.put(e.getKey(), e.getValue());
    }

    public void add(Map<K, V> map) {
        for (Map.Entry<K, V> e : map.entrySet()) {
            add(e);
        }
    }

    public Map<K, V> getMap() {
        return map;
    }

    public void setMap(Map<K, V> map) {
        this.map = map;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
