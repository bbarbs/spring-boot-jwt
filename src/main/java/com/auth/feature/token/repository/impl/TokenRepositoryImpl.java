package com.auth.feature.token.repository.impl;

import com.auth.core.jwt.JwtModel;
import com.auth.feature.token.repository.TokenRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.Date;

@Repository
public class TokenRepositoryImpl implements TokenRepository {

    @Inject
    RedisTemplate<String, Object> redisTemplate;

    @Override
    public void setSecretKey(String key, JwtModel model) {
        this.redisTemplate.opsForValue().set(key, model);
    }

    @Override
    public Boolean setKeyExpiration(String key, Date exp) {
        return this.redisTemplate.expireAt(key, exp);
    }

    @Override
    public void delete(String key) {
        this.redisTemplate.delete(key);
    }

    @Override
    public Object get(String key) {
        return this.redisTemplate.opsForValue().get(key);
    }
}
