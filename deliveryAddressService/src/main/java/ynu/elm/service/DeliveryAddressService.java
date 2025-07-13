package ynu.elm.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import ynu.elm.entity.DeliveryAddress;

import java.util.List;
import java.util.Optional;

public interface DeliveryAddressService {

    @Cacheable(value = "addressByUser", key = "#userId", unless = "#result == null or #result.empty")
    List<DeliveryAddress> listAddressByUser(String userId);

    @Cacheable(value = "addressById", key = "#daId", unless = "#result == null")
    Optional<DeliveryAddress> getAdderssBydaId(String daId);

    @CacheEvict(value = {"addressByUser", "addressById"}, allEntries = true)
    DeliveryAddress saveAddress(DeliveryAddress address);

    @CacheEvict(value = {"addressByUser", "addressById"}, allEntries = true)
    void removeAddress(String daId);
}