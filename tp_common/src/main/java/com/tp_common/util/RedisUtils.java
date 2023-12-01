package com.tp_common.util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效，-2代表键不存在
     */
    public <K> long getExpireTime(K key) {
        Long expire = redisTemplate.getExpire(key, TimeUnit.SECONDS);
        if (expire != null) {
            return expire;
        }
        return -2;
    }

    /**
     * 指定缓存失效时间
     *
     * @param key        键
     * @param expireTime 时间(秒)
     */
    public <K> void setExpireTime(K key, long expireTime) {
        try {
            if (expireTime > 0) {
                redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 移除指定 key 的过期时间
     *
     * @param key 键
     */
    public <K> void removeExpireTime(K key) {
        redisTemplate.boundValueOps(key).persist();
    }

    /**
     * 获取缓存中所有的键
     *
     * @param key 键
     * @return 缓存中所有的键
     */
    public <K> Set<K> keys(K key) {
        return redisTemplate.keys(key);
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public <K> boolean hasKey(K key) {
        try {
            return Boolean.TRUE.equals(redisTemplate.hasKey(key));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key删除缓
     *
     * @param keys 键
     */
    public <K> void delete(Collection<K> keys) {
        redisTemplate.delete(keys);
    }

    /**
     * 设置分布式锁
     *
     * @param key     键，可以用用户主键
     * @param value   值，可以传requestId，可以保证锁不会被其他请求释放，增加可靠性
     * @param expire  锁的时间(秒)
     * @return 设置成功为 true
     */
    public <K, V> Boolean setNx(K key, V value, long expire) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, expire, TimeUnit.SECONDS);
    }

    /**
     * 设置分布式锁，有等待时间
     *
     * @param key     键，可以用用户主键
     * @param value   值，可以传requestId，可以保证锁不会被其他请求释放，增加可靠性
     * @param expire  锁的时间(秒)
     * @param timeout 在timeout时间内仍未获取到锁，则获取失败
     * @return 设置成功为 true
     */
    public <K, V> Boolean setNx(K key, V value, long expire, long timeout) {
        long start = System.currentTimeMillis();
        //在一定时间内获取锁，超时则返回错误
        for (; ; ) {
            // 获取到锁，并设置过期时间返回true
            if (Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, value, expire, TimeUnit.SECONDS))) {
                return true;
            }
            //否则循环等待，在timeout时间内仍未获取到锁，则获取失败
            if (System.currentTimeMillis() - start > timeout) {
                return false;
            }
        }
    }

    /**
     * 释放分布式锁
     * @param key 锁
     * @param value 值，可以传requestId，可以保证锁不会被其他请求释放，增加可靠性
     * @return 成功返回true, 失败返回false
     */
    public <K, V> boolean releaseNx(K key, V value) {
        Object currentValue = redisTemplate.opsForValue().get(key);
        if (String.valueOf(currentValue) != null && value.equals(currentValue)) {
            return Boolean.TRUE.equals(redisTemplate.opsForValue().getOperations().delete(key));
        }
        return false;
    }

    /***********      String 类型操作           **************/
    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     */
    public <K, V> void set(K key, V value) {
        try {
            redisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     */
    public <K, V> void set(K key, V value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                redisTemplate.opsForValue().set(key, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * value增加值
     *
     * @param key    键
     * @param number 增加的值
     * @return 返回增加后的值
     */
    public Long incrBy(String key, long number) {
        return (Long) redisTemplate.execute((RedisCallback<Object>) connection -> connection.incrBy(key.getBytes(), number));
    }

    /**
     * value减少值
     *
     * @param key    键
     * @param number 减少的值
     * @return 返回减少后的值
     */
    public Long decrBy(String key, long number) {
        return (Long) redisTemplate.execute((RedisCallback<Object>) connection -> connection.decrBy(key.getBytes(), number));
    }

    /**
     * 根据key获取value
     *
     * @param key 键
     * @return 返回值
     */
    public <K, V> V get(K key) {
        BoundValueOperations<K, V> boundValueOperations = redisTemplate.boundValueOps(key);
        return boundValueOperations.get();
    }

    /***********      list 类型操作           **************/

    /**
     * 将value从右边放入缓存
     *
     * @param key   键
     * @param value 值
     */
    public <K, V> void listRightPush(K key, V value) {
        ListOperations<K, V> listOperations = redisTemplate.opsForList();
        //从队列右插入
        listOperations.rightPush(key, value);
    }

    /**
     * 将value从左边放入缓存
     *
     * @param key   键
     * @param value 值
     */
    public <K, V> void listLeftPush(K key, V value) {
        ListOperations<K, V> listOperations = redisTemplate.opsForList();
        //从队列右插入
        listOperations.leftPush(key, value);
    }

    /**
     * 将list从右边放入缓存
     *
     * @param key   键
     * @param value 值
     */
    public <K, V> void listRightPushAll(K key, List<V> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将list从左边放入缓存
     *
     * @param key   键
     * @param value 值
     */
    public <K, V> void listLeftPushAll(K key, List<V> value) {
        try {
            redisTemplate.opsForList().leftPushAll(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return 返回列表中的值
     */
    public <K, V> V listGetWithIndex(K key, long index) {
        ListOperations<K, V> listOperations = redisTemplate.opsForList();
        return listOperations.index(key, index);
    }

    /**
     * 从list左边弹出一条数据
     *
     * @param key 键
     * @return 队列中的值
     */
    public <K, V> V listLeftPop(K key) {
        ListOperations<K, V> listOperations = redisTemplate.opsForList();
        return listOperations.leftPop(key);
    }

    /**
     * 从list左边定时弹出一条
     *
     * @param key     键
     * @param timeout 弹出时间
     * @param unit    时间单位
     * @return 队列中的值
     */
    public <K, V> V listLeftPop(K key, long timeout, TimeUnit unit) {
        ListOperations<K, V> listOperations = redisTemplate.opsForList();
        return listOperations.leftPop(key, timeout, unit);
    }

    /**
     * 从list右边弹出一条数据
     *
     * @param key 键
     * @return 队列中的值
     */
    public <K, V> V listRightPop(K key) {
        ListOperations<K, V> listOperations = redisTemplate.opsForList();
        return listOperations.rightPop(key);
    }

    /**
     * 从list左边定时弹出
     *
     * @param key     键
     * @param timeout 弹出时间
     * @param unit    时间单位
     * @return 队列中的值
     */
    public <K, V> V listRightPop(K key, long timeout, TimeUnit unit) {
        ListOperations<K, V> listOperations = redisTemplate.opsForList();
        return listOperations.leftPop(key, timeout, unit);
    }

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始下标
     * @param end   结束下标  0 到 -1 代表所有值
     * @return list内容
     */
    public <K, V> List<V> listRange(K key, long start, long end) {
        try {
            ListOperations<K, V> listOperations = redisTemplate.opsForList();
            return listOperations.range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return list长度
     */
    public <K> long listSize(K key) {
        Long size = redisTemplate.opsForList().size(key);
        return Objects.requireNonNullElse(size, 0).longValue();
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 下标
     * @param value 值
     */
    public <K, V> void listSet(K key, long index, V value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从lit中移除N个值为value的值
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public <K, V> long listRemove(K key, long count, V value) {
        Long count1 = redisTemplate.opsForList().remove(key, count, value);
        if (count1 != null) {
            return count1;
        }
        return 0;
    }

    /***********      hash 类型操作           **************/

    /**
     * 根据key和键获取value
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public <K, HK, HV> HV hashGet(K key, String item) {
        HashOperations<K, HK, HV> hashOperations = redisTemplate.opsForHash();
        return hashOperations.get(key, item);
    }

    /**
     * 获取key对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public <K, HK, HV> Map<HK, HV> hashMGet(K key) {
        HashOperations<K, HK, HV> hashOperations = redisTemplate.opsForHash();
        return hashOperations.entries(key);
    }

    /**
     * 添加map到hash中
     *
     * @param key 键
     * @param map 对应多个键值
     */
    public <K> void hashMSet(K key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加map到hash中，并设置过期时间
     *
     * @param key        键
     * @param map        对应多个键值
     * @param expireTime 时间(秒)
     */
    public <K> void hashMSet(K key, Map<String, Object> map, long expireTime) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (expireTime > 0) {
                redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 向hash表中放入一个数据
     *
     * @param key   键
     * @param hKey  map 的键
     * @param value 值
     */
    public <K, HK, HV> void hashPut(K key, HK hKey, HV value) {
        try {
            HashOperations<K, HK, HV> hashOperations = redisTemplate.opsForHash();
            hashOperations.put(key, hKey, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 向hash表中放入一个数据，并设置过期时间
     *
     * @param key        键
     * @param hKey       map 的键
     * @param value      值
     * @param expireTime 时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     */
    public <K, HK, HV> void hashPut(K key, HK hKey, HV value, long expireTime) {
        try {
            redisTemplate.opsForHash().put(key, hKey, value);
            if (expireTime > 0) {
                redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param hKey map 的键 不能为null
     * @return true 存在 false不存在
     */
    public <K, HK, HV> boolean hashHasKey(K key, HK hKey) {
        HashOperations<K, HK, HV> hashOperations = redisTemplate.opsForHash();
        return hashOperations.hasKey(key, hKey);
    }

    /**
     * 取出所有 value
     *
     * @param key 键
     * @return map 中所有值
     */
    public <K, HK, HV> List<HV> hashValues(K key) {
        HashOperations<K, HK, HV> hashOperations = redisTemplate.opsForHash();
        return hashOperations.values(key);
    }

    /**
     * 取出所有 hKey
     *
     * @param key 键
     * @return map 所有的键
     */
    public <K, HK, HV> Set<HK> hashHKeys(K key) {
        HashOperations<K, HK, HV> hashOperations = redisTemplate.opsForHash();
        return hashOperations.keys(key);
    }

    /**
     * 删除hash表中的键值，并返回删除个数
     *
     * @param key      键
     * @param hashKeys 要删除的值的键
     * @return 删除个数
     */
    public <K, HK, HV> Long hashDelete(K key, Object... hashKeys) {
        HashOperations<K, HK, HV> hashOperations = redisTemplate.opsForHash();
        return hashOperations.delete(key, hashKeys);
    }

    /***********      set 类型操作           **************/
    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     */
    public <K, V> void setAdd(K key, V... values) {
        redisTemplate.opsForSet().add(key, values);
    }

    /**
     * 将set数据放入缓存，并设置过期时间
     *
     * @param key        键
     * @param expireTime 时间(秒)
     * @param values     值 可以是多个
     */
    public <K, V> void setAdd(K key, long expireTime, V... values) {
        redisTemplate.opsForSet().add(key, values);
        if (expireTime > 0) {
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
        }
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return set缓存的长度
     */
    public <K> long setSize(K key) {
        Long size = redisTemplate.opsForSet().size(key);
        if (size != null) {
            return size;
        }
        return 0;
    }

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return Set中的所有值
     */
    public <K, V> Set<V> setValues(K key) {
        SetOperations<K, V> setOperations = redisTemplate.opsForSet();
        return setOperations.members(key);
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 要查询的值
     * @return true 存在 false不存在
     */
    public <K, V> boolean setHasKey(K key, V value) {
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(key, value));
    }

    /**
     * 根据value删除，并返回删除的个数
     *
     * @param key   键
     * @param value 要删除的值
     * @return 删除的个数
     */
    public <K, V> Long setDelete(K key, Object... value) {
        SetOperations<K, V> setOperations = redisTemplate.opsForSet();
        return setOperations.remove(key, value);
    }

    /***********      zset 类型操作           **************/
    /**
     * 在 zset中插入一条数据
     *
     * @param key   键
     * @param value 要插入的值
     * @param score 设置分数
     */
    public <K, V> void zSetAdd(K key, V value, long score) {
        ZSetOperations<K, V> zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.add(key, value, score);
    }

    /**
     * 得到分数在 score1，score2 之间的值
     *
     * @param key    键
     * @param score1 起始分数
     * @param score2 终止分数
     * @return 范围内所有值
     */
    public <K, V> Set<V> zSetValuesRange(K key, long score1, long score2) {
        ZSetOperations<K, V> zSetOperations = redisTemplate.opsForZSet();
        return zSetOperations.range(key, score1, score2);
    }

    /**
     * 根据value删除，并返回删除个数
     *
     * @param key   键
     * @param value 要删除的值，可传入多个
     * @return 删除个数
     */
    public <K, V> Long zSetDeleteByValue(K key, Object... value) {
        ZSetOperations<K, V> zSetOperations = redisTemplate.opsForZSet();
        return zSetOperations.remove(key, value);
    }

    /**
     * 根据下标范围删除，并返回删除个数
     *
     * @param key   键
     * @param size1 起始下标
     * @param size2 结束下标
     * @return 删除个数
     */
    public <K, V> Long zSetDeleteRange(K key, long size1, long size2) {
        ZSetOperations<K, V> zSetOperations = redisTemplate.opsForZSet();
        return zSetOperations.removeRange(key, size1, size2);
    }

    /**
     * 删除分数区间内元素，并返回删除个数
     *
     * @param key    键
     * @param score1 起始分数
     * @param score2 终止分数
     * @return 删除个数
     */
    public <K, V> Long zSetDeleteByScore(K key, long score1, long score2) {
        ZSetOperations<K, V> zSetOperations = redisTemplate.opsForZSet();
        return zSetOperations.removeRangeByScore(key, score1, score2);
    }

}


