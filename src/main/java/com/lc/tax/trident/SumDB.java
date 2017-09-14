package com.lc.tax.trident;

import storm.trident.state.State;

import java.util.List;

public class SumDB implements State {
    @Override
    public void beginCommit(Long a_txid) {
        long val = a_txid;

    }
    @Override
    public void commit(Long a_txid) {
        long val = a_txid;
    }

    public void save(List<String> a_data) {
        //此处可以将数据写入数据库
        List<String> a_items = a_data;
    }
}