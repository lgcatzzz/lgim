package cn.catzzz.lgim.cache;

import java.util.Set;

/**
 * Classname:   LocalCache<br/>
 * Description: 缓存系统的公共接口<br/>
 * Date: 2022/12/20 20:08<br/>
 * Created by gql
 */
public interface LocalCache {
    void put(String key, Object value);

    Object get(String key);

    Set<String> cacheKeys();

    void removeByKey(String key);

    Object removeFirstByValue(Object value);

    boolean containsValue(Object value);

    void removeAllByValue(Object value);

    void clearAll();
}
