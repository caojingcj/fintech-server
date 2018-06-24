package com.fintech.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.fintech.service.RedisService;
import com.fintech.util.GsonUtil;

@Service("redisService")
@Transactional(rollbackFor = Exception.class)
public class RedisServiceImpl implements RedisService {

     private static int seconds=3600*24;

        @Autowired
        private StringRedisTemplate stringRedisTemplate;
        @Autowired
        private RedisTemplate<String, Object>  redisTemplate;
        
        @Override
        public boolean set(final String key, final String value) throws Exception {
            Assert.hasText(key,"Key is not empty.");
            boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                    RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                    connection.set(serializer.serialize(key), serializer.serialize(value));
                    return true;
                }
            });
            return result;
        }
        
        /** 
        * @Title: RedisServiceImpl.java 
        * @author qierkang xyqierkang@163.com   
        * @date 2018年5月23日 下午11:38:06  
        * @param @param key
        * @param @param value
        * @param @param timeout
        * @param @throws Exception    设定文件 
        * @Description: TODO[ 这里用一句话描述这个方法的作用 ]
        * @throws 
        */
        @Override
        public void setVal(final String key, final String value,final long timeout) throws Exception {
//          MICROSECONDS    微秒   一百万分之一秒（就是毫秒/1000）
//          MILLISECONDS    毫秒   千分之一秒    
//          NANOSECONDS   毫微秒  十亿分之一秒（就是微秒/1000）
//          SECONDS          秒
//          MINUTES     分钟
//          HOURS      小时
//          DAYS      天
            Assert.hasText(key,"Key is not empty.");
            redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
        }
        
        /** 
        * @Title: RedisServiceImpl.java 
        * @author qierkang xyqierkang@163.com   
        * @date 2018年5月23日 下午11:38:04  
        * @param @param key
        * @param @return
        * @param @throws Exception    设定文件 
        * @Description: TODO[ 这里用一句话描述这个方法的作用 ]
        * @throws 
        */
        @Override
        public String getVal(final String key) throws Exception {
            Assert.hasText(key,"Key is not empty.");
            return redisTemplate.opsForValue().get(key).toString();
        }

        public String get(final String key) throws Exception {
                Assert.hasText(key,"Key is not empty。");
                String result = redisTemplate.execute(new RedisCallback<String>() {
                    @Override
                    public String doInRedis(RedisConnection connection) throws DataAccessException {
                        RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                        byte[] value =  connection.get(serializer.serialize(key));
                        return serializer.deserialize(value);
                    }
                });
                return result;
        }

        public void del(final String key) throws Exception {
            Assert.hasText(key,"Key is not empty.");
            redisTemplate.execute(new RedisCallback<Long>() {
                @Override
                public Long doInRedis(RedisConnection conn) throws DataAccessException {
                    RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                    return conn.del(serializer.serialize(key));
                }
            });
        }



        @Override
        public boolean expire(final String key, long expire) {
            return redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }

        @Override
        public <T> boolean setList(String key, List<T> list) throws Exception {
            Assert.hasText(key,"Key is not empty.");
            String value = GsonUtil.getJsonString(list);
            return set(key,value);
        }

        @Override
        public <T> List<T> getList(String key,Class<T> clz)  throws Exception{

            Assert.hasText(key,"Key is not empty.");

            String json = get(key);
            if(json!=null){
                List<T> list = GsonUtil.readJson2Array(json,clz);
                return list;
            }
            return null;
        }

        @Override
        public long lpush(final String key, Object obj)throws Exception {
            Assert.hasText(key,"Key is not empty.");

            final String value = GsonUtil.getJsonString(obj);
            long result = redisTemplate.execute(new RedisCallback<Long>() {
                @Override
                public Long doInRedis(RedisConnection connection) throws DataAccessException {
                    RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                    long count = connection.lPush(serializer.serialize(key), serializer.serialize(value));
                    return count;
                }
            });
            return result;
        }

        @Override
        public long rpush(final String key, Object obj) throws Exception{
            Assert.hasText(key,"Key is not empty.");

            final String value = GsonUtil.getJsonString(obj);
            long result = redisTemplate.execute(new RedisCallback<Long>() {
                @Override
                public Long doInRedis(RedisConnection connection) throws DataAccessException {
                    RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                    long count = connection.rPush(serializer.serialize(key), serializer.serialize(value));
                    return count;
                }
            });
            return result;
        }

        @Override
        public void hmset(String key, Object obj)  throws Exception{
            Assert.hasText(key,"Key is not empty.");
            Map<byte[], byte[]> data=GsonUtil.readJsonByteMap(GsonUtil.getJsonString(obj));
            redisTemplate.execute(new RedisCallback<String>() {
                @Override
                public String doInRedis(RedisConnection connection) throws DataAccessException {
                    RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                    connection.hMSet(serializer.serialize(key),data);
                    return "";
                }
            });
        }

        @Override
        public <T> T hget(String key, Class<T> clz)  throws Exception{
            Assert.hasText(key,"Key is not empty.");

            return redisTemplate.execute(new RedisCallback<T>() {

                @Override
                public T doInRedis(RedisConnection connection) throws DataAccessException {
                    RedisSerializer<String> serializer = redisTemplate.getStringSerializer();

                    Map<String,Object> result;

                    Map<byte[],byte[]> data=connection.hGetAll(serializer.serialize(key));
                    result= new HashMap<>();
                    for (Map.Entry<byte[], byte[]> entry: data.entrySet()) {
                        result.put(serializer.deserialize(entry.getKey()),serializer.deserialize(entry.getValue()));
                    }

                    return GsonUtil.json2Obj(GsonUtil.getJsonString(result),clz);
                }
            });
        }

        @Override
        public<T> List<T>  hmGetAll(String key,Class<T> clz) throws Exception{
            Assert.hasText(key,"Key is not empty.");

            List<Map<String,Object>> dataList= new ArrayList<>();
            return redisTemplate.execute(new RedisCallback<List<T>>() {
                @Override
                public List<T> doInRedis(RedisConnection connection) throws DataAccessException {
                    RedisSerializer<String> serializer = redisTemplate.getStringSerializer();

                    Set<String> keysSet=redisTemplate.keys(key);
                    Map<byte[],byte[]> data;
                    Map<String,Object> result;
                    for(String newKey:keysSet) {
                        data=connection.hGetAll(serializer.serialize(newKey));
                        result= new HashMap<>();
                        for (Map.Entry<byte[], byte[]> entry: data.entrySet()) {
                            result.put(serializer.deserialize(entry.getKey()),serializer.deserialize(entry.getValue()));
                        }
                        dataList.add(result);
                    }
                    return GsonUtil.readJson2Array(GsonUtil.getJsonString(dataList),clz);
                }
            });
        }

        @Override
        public String lpop(final String key) throws Exception{
            Assert.hasText(key,"Key is not empty.");

            String result = redisTemplate.execute(new RedisCallback<String>() {
                @Override
                public String doInRedis(RedisConnection connection) throws DataAccessException {
                    RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                    byte[] res =  connection.lPop(serializer.serialize(key));
                    return serializer.deserialize(res);
                }
            });
            return result;
        }
        
        public static void main(String[] args) {
            String key="";
            Assert.hasText(key,"查无此用户Key ");
            System.out.println(123123);
        }
}
