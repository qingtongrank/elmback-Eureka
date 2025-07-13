package ynu.elm.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import ynu.elm.entity.Business;

import java.util.List;
import java.util.Optional;

public interface BusinessService {

    @Cacheable(value = "business", key = "#businessId", unless = "#result == null")
    Optional<Business> getBusinessByIdByPass(String businessId, String password);

    @Cacheable(value = "business", key = "#businessId", unless = "#result == null")
    Optional<Business> getBusinessById(String businessId);

    @Cacheable(value = "businessAll", unless = "#result == null or #result.empty")
    List<Business> getAllBusinesses();

    @Cacheable(value = "businessByOrderType", key = "#orderTypeId", unless = "#result == null or #result.empty")
    List<Business> listBusinessByOrderTypeId(Integer orderTypeId);

    @CacheEvict(value = {"business", "businessAll", "businessByOrderType"}, allEntries = true)
    Business saveBusiness(Business business);
}