package ynu.elm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ynu.elm.entity.Business;
import ynu.elm.repository.BusinessRepository;
import ynu.elm.service.BusinessService;
import java.util.List;
import java.util.Optional;

@Service
public class BusinessServiceImpl implements BusinessService {

    @Autowired
    private BusinessRepository businessRepository;

    @Override
    public Optional<Business> getBusinessByIdByPass(String businessId, String password) {
        return businessRepository.findByBusinessIdAndBusinessPasswordAndDelTag(businessId, password, 1);
    }

    @Override
    public List<Business> getAllBusinesses() {
        return businessRepository.findAll();
    }

    @Override
    public Optional<Business> getBusinessById(String businessId){
        return businessRepository.findByBusinessIdAndDelTag(businessId, 1);
    }

    @Override
    public List<Business> listBusinessByOrderTypeId(Integer orderTypeId) {
        return businessRepository.findByOrderTypeIdAndDelTag(orderTypeId, 1);
    }

    @Override
    public Business saveBusiness(Business business) {
        return businessRepository.save(business);
    }
}