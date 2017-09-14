package com.lc.tax.trident;

import storm.trident.state.map.IBackingMap;

import java.util.List;

public class MyState<T> implements IBackingMap<T> {

    public List<T> multiGet(List<List<Object>> keys){
        return  null;
    }
    public void multiPut(List<List<Object>> keys, List<T> vals){

    }

}
