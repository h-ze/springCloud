package com.qdone.support.cache.redis.autoconfig;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.qdone.support.cache.redis.properties.CacheRedisProperties;
import com.qdone.support.cache.redis.properties.CacheRedisSingleItem;
import com.qdone.support.cache.redis.repository.CacheRedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @author ?????????
 * redis??????????????????????????????
 * ???????????????????????????????????????
 */
@Slf4j
@Configuration
@EnableCaching
@AutoConfigureAfter(RedisAutoConfiguration.class)
@EnableConfigurationProperties(CacheRedisProperties.class)
@ConditionalOnProperty(prefix="cache.redis",name = "enable", havingValue = "true")
public class CacheRedisAutoConfig extends CachingConfigurerSupport {

    @Autowired
    private CacheRedisProperties cacheRedisProperties;

    /**
     * ?????????SpringCache??????key
     */
    @Bean("keyGenerator")
    @Primary
    @ConditionalOnMissingBean(KeyGenerator.class)
    @Override
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                Map<String, Object> keyMap = new LinkedHashMap<String, Object>();//??????key??????
                keyMap.put("target", target.getClass().toGenericString());//??????target?????????
                keyMap.put("method", method.getName());//??????method?????????
                //????????????????????????
                if (!ObjectUtils.isEmpty(params)) {
                    for (int i = 0; i <params.length ; i++) {
                        keyMap.put("params-" + i, params[i]);
                    }
                }
                byte[] argsHash = null;
                String cacheKey = null;
                try {
                    argsHash = MessageDigest.getInstance("MD5").digest(new Gson().toJson(keyMap).getBytes("UTF-8"));
                    cacheKey= DigestUtils.md5Hex(argsHash).toUpperCase();//??????MD5????????????key
                } catch (NoSuchAlgorithmException e) {
                    log.error("AutoCacheRedisConfig init springCache keyGenerator error:",e);
                } catch (UnsupportedEncodingException e) {
                    log.error("AutoCacheRedisConfig init springCache keyGenerator error:",e);
                }
                return cacheKey;
            }
        };
    }

    /**
     * ??????????????????
     * @param redisConnectionFactory
     * @return redisTemplate
     */
    @Bean(name="mybatisCache")
    @Order(value =Ordered.HIGHEST_PRECEDENCE)
    @Primary
    @ConditionalOnClass({RedisConnectionFactory.class})
    public RedisTemplate<String, Object> mybatisCache(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        // ????????????value?????????????????????Jackson2JsonRedisSerializer???
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        // ????????????key?????????????????????StringRedisSerializer???
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // ????????????hashKey?????????????????????Jackson2JsonRedisSerializer???
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        // ????????????hashValue?????????????????????Jackson2JsonRedisSerializer???
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        redisTemplate.setEnableTransactionSupport(true);//??????????????????
        return redisTemplate;
    }

    /**
     * ???????????????SimpleCacheManager
     */
    @Bean
    @Order(value =Ordered.HIGHEST_PRECEDENCE)
    @ConditionalOnBean({RedisTemplate.class,RedisConnectionFactory.class})
    public SimpleCacheManager simpleCacheManager(@Qualifier("mybatisCache") RedisTemplate<String, Object> redisTemplate, RedisConnectionFactory factory) {
        SimpleCacheManager simple = new SimpleCacheManager();
        if(!CollectionUtils.isEmpty(cacheRedisProperties.getItems())){
            String keyPrefix=StringUtils.isEmpty(cacheRedisProperties.getPrefix())?"":cacheRedisProperties.getPrefix();
            long globalTimeOut=ObjectUtils.isEmpty(cacheRedisProperties.getTimeout())?60*60*24:cacheRedisProperties.getTimeout();
            //????????????????????????null
            List<CacheRedisSingleItem> items=new ArrayList<CacheRedisSingleItem>(new HashSet<CacheRedisSingleItem>(cacheRedisProperties.getItems()));
            Set<CacheRedisRepository> caches=new HashSet<CacheRedisRepository>();
            Iterator<CacheRedisSingleItem> it=items.iterator();
            while (it.hasNext()){
                CacheRedisSingleItem item=it.next();
                if(!ObjectUtils.isEmpty(item)){
                    //????????????
                    String allkeyPrefix=!StringUtils.isEmpty(item.getPrefix())?keyPrefix+item.getPrefix():keyPrefix+"";
                    //????????????
                    boolean isEnable=ObjectUtils.isEmpty(item.isEnable())?Boolean.TRUE:item.isEnable();
                    //????????????
                    if(!StringUtils.isEmpty(item.getName())){
                        //????????????????????????
                        if(ObjectUtils.isEmpty(item.getTimeout())){
                            caches.add(new CacheRedisRepository(item.getName(),globalTimeOut,isEnable,allkeyPrefix,redisTemplate,factory));
                        }else{
                            caches.add(new CacheRedisRepository(item.getName(),item.getTimeout(),isEnable,allkeyPrefix,redisTemplate,factory));
                        }
                    }
                }
            }
            simple.setCaches(caches);
        }
        return simple;
    }

}
