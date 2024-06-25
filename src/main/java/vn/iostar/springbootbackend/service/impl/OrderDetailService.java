package vn.iostar.springbootbackend.service.impl;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import vn.iostar.springbootbackend.embeddedId.IdOrderDetail;
import vn.iostar.springbootbackend.entity.Order;
import vn.iostar.springbootbackend.entity.OrderDetail;
import vn.iostar.springbootbackend.entity.User;
import vn.iostar.springbootbackend.model.BicycleProductResponseModel;
import vn.iostar.springbootbackend.model.OrderDetailModel;
import vn.iostar.springbootbackend.model.response.BaseResponse;
import vn.iostar.springbootbackend.repository.OrderDetailRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    public void saveOrderDetail(OrderDetail orderDetail) {
        orderDetailRepository.save(orderDetail);
    }

    public Page<OrderDetailModel> getAllOrderDetail(int page, int size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userService.getUserByEmail(email).get();
        List<Order> orders = orderService.getOrdersByUser(user);
        List<OrderDetailModel> orderDetails = new ArrayList<>();
        for (Order order : orders) {
            OrderDetailModel orderDetailModel = new OrderDetailModel();
            List<OrderDetail> orderDetailsFound = orderDetailRepository.findByIdOrderDetail_IdOrder(order.getIdOrder());
            List<BicycleProductResponseModel> models = new ArrayList<>();
            for (OrderDetail orderDetailFound : orderDetailsFound) {
                BicycleProductResponseModel bicycleProductResponseModel = BicycleProductResponseModel.builder()
                        .bicycleImage(orderDetailFound.getBicycleProduct().getBicycle().getImage())
                        .bicyclePrice(orderDetailFound.getBicycleProduct().getBicycle().getPrice())
                        .bicycleName(orderDetailFound.getBicycleProduct().getBicycle().getName())
                        .bicycleColorName(orderDetailFound.getBicycleProduct().getBicycleColor().getName())
                        .bicycleSizeName(orderDetailFound.getBicycleProduct().getBicycleSize().getName())
                        .totalQuantity(orderDetailFound.getQuantity())
                        .build();
                models.add(bicycleProductResponseModel);
            }
            orderDetailModel.setBicycleProductModels(models);
            orderDetailModel.setPayMethod(order.getPayMethod().getName());
            orderDetailModel.setOrderedAt(order.getDayOrdered());
            orderDetailModel.setIdOrder(order.getIdOrder());
            orderDetailModel.setOrderState(order.getOrderState());
            BeanUtils.copyProperties(order, orderDetailModel);
            orderDetails.add(orderDetailModel);
        }
        Pageable pageable = PageRequest.of(page, size);
        int start = (int) pageable.getOffset();
        int end = pageable.getOffset() + pageable.getPageSize() > orderDetails.size() ? orderDetails.size() : (int) pageable.getOffset() + pageable.getPageSize();
        List<OrderDetailModel> pagedOrderDetails = orderDetails.subList(start, end);
        return new PageImpl<OrderDetailModel>(pagedOrderDetails, pageable, orderDetails.size());
    }

    public List<OrderDetail> getOrderDetailsById(Long idOrder) {
        return orderDetailRepository.findByIdOrderDetail_IdOrder(idOrder);
    }
}
