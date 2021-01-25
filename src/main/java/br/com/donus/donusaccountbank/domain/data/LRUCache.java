package br.com.donus.donusaccountbank.domain.data;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K, V> extends LinkedHashMap<K, V> {

    private final int maxEntries;

    public LRUCache(int maxEntries) {
        super();
        this.maxEntries = maxEntries;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        return super.size() > this.maxEntries;
    }
}
