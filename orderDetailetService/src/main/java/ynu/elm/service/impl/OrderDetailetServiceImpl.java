package ynu.elm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ynu.elm.entity.OrderDetailet;
import ynu.elm.repository.OrderDetailetRepository;
import ynu.elm.service.OrderDetailetService;
import java.util.List;

@Service
public class OrderDetailetServiceImpl implements OrderDetailetService {

    @Autowired
    private OrderDetailetRepository orderDetailetRepository;

    @Override
    public List<OrderDetailet> listDetailetByOrder(String orderId) {
        return orderDetailetRepository.findByOrderId(orderId);
    }

    @Override
    public OrderDetailet saveDetailet(OrderDetailet detailet) {
        return orderDetailetRepository.save(detailet);
    }

    @Override
    public void removeDetailet(String odId) {
        orderDetailetRepository.deleteById(odId);
    }
}