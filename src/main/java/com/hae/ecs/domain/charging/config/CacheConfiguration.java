package com.hae.ecs.domain.charging.config;

import com.github.benmanes.caffeine.jcache.configuration.CaffeineConfiguration;
import java.util.OptionalLong;
import java.util.concurrent.TimeUnit;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Caffeine caffeine = jHipsterProperties.getCache().getCaffeine();

        CaffeineConfiguration<Object, Object> caffeineConfiguration = new CaffeineConfiguration<>();
        caffeineConfiguration.setMaximumSize(OptionalLong.of(caffeine.getMaxEntries()));
        caffeineConfiguration.setExpireAfterWrite(OptionalLong.of(TimeUnit.SECONDS.toNanos(caffeine.getTimeToLiveSeconds())));
        caffeineConfiguration.setStatisticsEnabled(true);
        jcacheConfiguration = caffeineConfiguration;
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.hae.ecs.domain.charging.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.hae.ecs.domain.charging.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.hae.ecs.domain.charging.domain.User.class.getName());
            createCache(cm, com.hae.ecs.domain.charging.domain.Authority.class.getName());
            createCache(cm, com.hae.ecs.domain.charging.domain.User.class.getName() + ".authorities");
            createCache(cm, com.hae.ecs.domain.charging.domain.Connector.class.getName());
            createCache(cm, com.hae.ecs.domain.charging.domain.Pricing.class.getName());
            createCache(cm, com.hae.ecs.domain.charging.domain.Evse.class.getName());
            createCache(cm, com.hae.ecs.domain.charging.domain.Evse.class.getName() + ".connectors");
            createCache(cm, com.hae.ecs.domain.charging.domain.Evse.class.getName() + ".pricings");
            createCache(cm, com.hae.ecs.domain.charging.domain.Charger.class.getName());
            createCache(cm, com.hae.ecs.domain.charging.domain.Meta.class.getName());
            createCache(cm, com.hae.ecs.domain.charging.domain.Meta.class.getName() + ".sockets");
            createCache(cm, com.hae.ecs.domain.charging.domain.Socket.class.getName());
            createCache(cm, com.hae.ecs.domain.charging.domain.Picture.class.getName());
            createCache(cm, com.hae.ecs.domain.charging.domain.Station.class.getName());
            createCache(cm, com.hae.ecs.domain.charging.domain.Station.class.getName() + ".evses");
            createCache(cm, com.hae.ecs.domain.charging.domain.Station.class.getName() + ".chargers");
            createCache(cm, com.hae.ecs.domain.charging.domain.Station.class.getName() + ".pictures");
            createCache(cm, com.hae.ecs.domain.charging.domain.ChargingFavoritesSpot.class.getName());
            createCache(cm, com.hae.ecs.domain.charging.domain.ChargingUseHistory.class.getName());
            // jhipster-needle-caffeine-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
