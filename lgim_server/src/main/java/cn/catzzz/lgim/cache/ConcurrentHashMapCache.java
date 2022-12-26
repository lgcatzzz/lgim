package cn.catzzz.lgim.cache;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Classname:   ConcurrentHashMapCache<br/>
 * Description: 基于ConcurrentHashMap的简单缓存系统<br/>
 * Date: 2022/12/20 19:43<br/>
 * Created by gql
 */
public class ConcurrentHashMapCache implements LocalCache {
    private static final int DEFAULT_SIZE = 3000;   // 默认缓存容量
    /**
     * 存储数据，key是userId，value是channelId
     */
    private final Map<String, Object> data;

    public ConcurrentHashMapCache() {
        data = new ConcurrentHashMap<>(DEFAULT_SIZE);
    }

    /**
     * 添加缓存
     */
    public void put(String key, Object value) {
        data.put(key, value);
        //todo 能设置缓存失效时间并清除缓存
    }

    /**
     * 获取key对应的缓存
     */
    public Object get(String key) {
        return data.get(key);
    }

    /**
     * 获取当前缓存中所有的key
     */
    public Set<String> cacheKeys() {
        return data.keySet();
    }

    /**
     * 删除key对应的缓存
     */
    public void removeByKey(String key) {
        data.remove(key);
    }

    public boolean containsValue(Object value) {
        return data.containsValue(value);
    }

    /**
     * 移除第一个值为value的缓存
     */
    @Override
    public Object removeFirstByValue(Object value) {
        String key = null;
        for (String getKey :
                data.keySet()) {
            if (data.get(getKey).equals(value)) {
                key = getKey;
                return data.remove(key);
            }
        }
        return null;
    }

    /**
     * 移除所有值为value的缓存
     */
    @Override
    public void removeAllByValue(Object value) {
        String key = null;
        for (String getKey :
                data.keySet()) {
            if (data.get(getKey).equals(value)) {
                key = getKey;
                data.remove(key);
            }
        }
    }

    /**
     * 清空所有缓存
     */
    public void clearAll() {
        if (data.size() > 0) {
            data.clear();
        }
    }
}
