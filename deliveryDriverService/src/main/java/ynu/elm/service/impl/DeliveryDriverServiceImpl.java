package ynu.elm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ynu.elm.entity.DeliveryDriver;
import ynu.elm.repository.DeliveryDriverRepository;
import ynu.elm.service.DeliveryDriverService;

import java.util.List;
import java.util.Optional;

@Service
public class DeliveryDriverServiceImpl implements DeliveryDriverService {

    @Autowired
    private DeliveryDriverRepository deliveryDriverRepository;

    @Override
    public Optional<DeliveryDriver> getDriverByIdByPass(String driverId, String password) {
        return deliveryDriverRepository
                .findByDeliveryDriverIdAndDeliveryDriverPasswordAndDelTag(driverId, password, 1);
    }

    @Override
    public List<DeliveryDriver> getAllDeliveryDriver() {
        return deliveryDriverRepository.findAll();
    }

    @Override
    public boolean isDriverExist(String driverId) {
        return deliveryDriverRepository.existsByDeliveryDriverIdAndDelTag(driverId, 1);
    }

    @Override
    public DeliveryDriver saveDriver(DeliveryDriver driver) {
        return deliveryDriverRepository.save(driver);
    }
}