package ynu.elm.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import ynu.elm.entity.DeliveryDriver;

import java.util.List;
import java.util.Optional;

public interface DeliveryDriverService {

    @Cacheable(value = "driver", key = "#driverId", unless = "#result == null")
    Optional<DeliveryDriver> getDriverByIdByPass(String driverId, String password);

    @Cacheable(value = "driverAll", unless = "#result == null or #result.empty")
    List<DeliveryDriver> getAllDeliveryDriver();

    @Cacheable(value = "driverExist", key = "#driverId")
    boolean isDriverExist(String driverId);

    @CacheEvict(value = {"driver", "driverAll", "driverExist"}, allEntries = true)
    DeliveryDriver saveDriver(DeliveryDriver driver);
}