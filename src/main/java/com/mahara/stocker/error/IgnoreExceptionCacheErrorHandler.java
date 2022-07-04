package com.mahara.stocker.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;

public class IgnoreExceptionCacheErrorHandler implements CacheErrorHandler {
  Logger logger = LoggerFactory.getLogger(IgnoreExceptionCacheErrorHandler.class);

  @Override
  public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
    logger.error(exception.getMessage(), exception);
  }

  @Override
  public void handleCachePutError(
      RuntimeException exception, Cache cache, Object key, Object value) {
    logger.error(exception.getMessage(), exception);
  }

  @Override
  public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
    logger.error(exception.getMessage(), exception);
  }

  @Override
  public void handleCacheClearError(RuntimeException exception, Cache cache) {
    logger.error(exception.getMessage(), exception);
  }
}
