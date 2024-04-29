package com.knowledge.base.redis;

import com.knowledge.base.util.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author zhoulinwen
 * @title: RedisLockUtil
 * @description: redis分布式锁
 * @date 2024/2/23 11:53 AM
 */
@Component
@Slf4j
public class RedisLockUtil implements InitializingBean {

    /**
     * 分布式锁lua脚本（锁）
     */
    private static final String LOCK_SCRIPT = "if redis.call('GET', KEYS[1]) == ARGV[1] then\n" + 
            " redis.call('EXPIRE', KEYS[1], tonumber(ARGV[2]))\n" + 
            " return true\n" + 
            "elseif redis.call('SETNX', KEYS[1], ARGV[1]) == 1 then\n" + 
            " redis.call('EXPIRE', KEYS[1], tonumber(ARGV[2]))\n" + 
            " return true\n" + 
            "else\n" + 
            " return false\n" + 
            "end";

    /**
     * 分布式锁lua脚本（解锁）
     */
    private static final String UNLOCK_SCRIPT = "if redis.call('GET', KEYS[1]) == ARGV[1] then\n" + 
            " redis.call('DEL';, KEYS[1])\n" + 
            " return true\n" + 
            "else\n" + 
            " return false\n" + 
            "end";
    
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    
    private static StringRedisTemplate redisTemplate;
    
    private static final String REDIS_LOCAL_KEY_PREFIX = "redis_local_key";
    private static final String LOCK_PREFIX = "lock";
    private static final String  QUOTATION = ":";
    private String uuid = UUID.randomUUID().toString();
    /**
     * 上锁状态
     */
    private boolean isLocked;
    
    @Override
    public void afterPropertiesSet() throws Exception {
        redisTemplate = stringRedisTemplate;
    }

    /**
     * 锁的key
     */
    private String lockKey;
    private long expireAfter;

    private RedisLockUtil(String lockKey,long expireAfter) {
        if(StringUtils.isBlank(lockKey)){
            throw new IllegalArgumentException("lockKey can't be null");
        }
        this.lockKey = lockKey;
        this.expireAfter = expireAfter;
        ThreadLocalUtil.put(REDIS_LOCAL_KEY_PREFIX+lockKey, uuid);
    }

    /**
     * 实例化工具
     * @param lockKey
     * @param expireAfter
     * @return
     */
    public static RedisLockUtil init(String lockKey,long expireAfter){
        if(StringUtils.isBlank(lockKey)){
            throw new IllegalArgumentException("lockKey can't be null");
        }
        return new RedisLockUtil(lockKey,expireAfter);
    }
    
    private boolean obtainLock(){
        DefaultRedisScript<Boolean> rs = new DefaultRedisScript<>(LOCK_SCRIPT, Boolean.class);
        List<String> keysList = new ArrayList<>();
        keysList.add(LOCK_PREFIX + QUOTATION +lockKey);
        Object[] argvList = new Object[]{String.valueOf(this.uuid),String.valueOf(this.expireAfter)};
        Boolean isSuccess = redisTemplate.execute(rs, keysList, argvList);
        return Boolean.TRUE.equals(isSuccess);
    }

    private boolean releaseLock(){
        DefaultRedisScript<Boolean> rs = new DefaultRedisScript<>(UNLOCK_SCRIPT, Boolean.class);
        List<String> keysList = new ArrayList<>();
        keysList.add(LOCK_PREFIX + QUOTATION +lockKey);
        Object[] argvList = new Object[]{String.valueOf(this.uuid),String.valueOf(this.expireAfter)};
        Boolean isSuccess = redisTemplate.execute(rs, keysList, argvList);
        return Boolean.TRUE.equals(isSuccess);
    }
    
    private void checkTag(){
        String tag = ThreadLocalUtil.get(REDIS_LOCAL_KEY_PREFIX + this.lockKey);
        if(tag == null || !Objects.equals(this.uuid, tag)){
            throw new IllegalThreadStateException(this.getClass().getSimpleName()+"can not run in different thread!");
        }
    }

    /**
     * 锁
     */
    public void lock(){
        this.checkTag();
        while (true){
            try {
                while (!obtainLock()){
                    Thread.sleep(100);
                }
                break;
            }catch (InterruptedException e){
                log.info("lock error!");
            }
        }
    }
    /**
     * 尝试上锁
     */
    public boolean tryLock() {
        return tryLock(0,TimeUnit.MILLISECONDS);
    }
    
    /**
     * 尝试上锁(尝试时间内不断尝试)
     * @param time
     * @param unit
     */
    public boolean tryLock(long time, TimeUnit unit) {
        this.checkTag();
        boolean result = false;
        try{
            long now = System.currentTimeMillis();
            long expire = now + TimeUnit.MILLISECONDS.convert(time, unit);
            while (!(result = obtainLock()) && System.currentTimeMillis() < expire){
                Thread.sleep(100);
            }
            if(result){
                this.isLocked = true;
            }
            log.info("tryLock key {},{}",this.lockKey,result?"success":"timeout");
        }catch (InterruptedException e){
            log.info("lock key {} was interrupted",lockKey);
        }
        return result;

    }
    
    
    /**
     * 解锁
     */
    public void unlock(){
        this.checkTag();
        if(!isLocked){
            log.info("key {} did not exists!",lockKey);
            return;
        }
        boolean result = releaseLock();
        this.isLocked = false;
        if(result){
            log.info("unlock key lockKey:{},{}",lockKey,"success");
        }else {
            log.info("key {} was released!",lockKey);
        }
    }
    
    
    
}
