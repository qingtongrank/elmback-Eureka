package ynu.elm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ynu.elm.entity.DeliveryAddress;
import ynu.elm.repository.DeliveryAddressRepository;
import ynu.elm.service.DeliveryAddressService;
import java.util.List;
import java.util.Optional;

@Service
public class DeliveryAddressServiceImpl implements DeliveryAddressService {

    @Autowired
    private DeliveryAddressRepository deliveryAddressRepository;

    @Override
    public List<DeliveryAddress> listAddressByUser(String userId) {
        return deliveryAddressRepository.findByUserId(userId);
    }

    @Override
    public Optional<DeliveryAddress> getAdderssBydaId(String daId) {
        return deliveryAddressRepository.findByDaId(daId);
    }

    @Override
    public DeliveryAddress saveAddress(DeliveryAddress address) {
        return deliveryAddressRepository.save(address);
    }

    @Override
    public void removeAddress(String daId) {
        deliveryAddressRepository.deleteById(daId);
    }
}