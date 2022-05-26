package com.azka.demo.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.azka.demo.repository.OrderDetailRepository;
import com.azka.demo.service.OrderDetailService;
import com.azka.demo.service.dto.OrderDetailDTO;
import com.azka.demo.service.mapper.OrderDetailMapper;

@Service
@Transactional
public class OrderDetailServiceImpl implements OrderDetailService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private final OrderDetailRepository orderDetailRepository;
	
	private final OrderDetailMapper orderDetailMapper;
	
	public OrderDetailServiceImpl(OrderDetailRepository orderDetailRepository, OrderDetailMapper orderDetailMapper) {
		this.orderDetailRepository = orderDetailRepository;
		this.orderDetailMapper = orderDetailMapper; 
	}
	
	@Override
	public Page<OrderDetailDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Orders");
		return orderDetailRepository.findAll(pageable).map(orderDetailMapper::toDto);
	}
	
	@Override
	public List<OrderDetailDTO> findAllByOrderIdAndEmailAddress(String orderId, String emailAddress) {
		log.debug("Request to get all Orders by orderId and emailAddress: {}-{}", orderId, emailAddress);
		return orderDetailRepository.findAllByOrderIdAndEmailAddress(orderId, emailAddress)
				.stream().filter(Objects::nonNull).map(orderDetailMapper::toDto).collect(Collectors.toList());
	}
	
	@Override
	public List<OrderDetailDTO> findAllByOrderIdAndEmailAddressAndSku(String orderId, String emailAddress, String sku) {
		log.debug("Request to get all Orders by orderId and emailAddress: {}-{}", orderId, emailAddress);
		return orderDetailRepository.findAllByOrderIdAndEmailAddressAndSku(orderId, emailAddress, sku)
				.stream().filter(Objects::nonNull).map(orderDetailMapper::toDto).collect(Collectors.toList());
	}
	
	@Override
	public boolean isOrderDetailExistByOrderIdAndEmailAddress(String orderId, String emailAddress) {
		log.debug("Request to check whether any Order exist by orderId and emailAddress: {} - {}", orderId, emailAddress);
		boolean result = true; 
		List<OrderDetailDTO> orderDetails = this.findAllByOrderIdAndEmailAddress(orderId, emailAddress);
		if (orderDetails.isEmpty()) {
			result = false;
		}
		return result;
	}
	
	@Override
	public int getOrderDetailItemQuantity(String orderId, String emailAddress, String sku) {
		log.debug("Request to get quantity of Order Item by orderId and emailAddress: {} - {}", orderId, emailAddress);
		int result = 0;
		List<OrderDetailDTO> orderDetails = this.findAllByOrderIdAndEmailAddressAndSku(orderId, emailAddress, sku);
		for (OrderDetailDTO orderDetail : orderDetails) {
			result += orderDetail.getQuantity();
		}
		return result;
	}
	
	@Override
	public double getOrderDetailItemPrice(String orderId, String emailAddress, String sku) {
		log.debug("Request to get price of Order Item by orderId and emailAddress: {} - {}", orderId, emailAddress);
		List<OrderDetailDTO> orderDetails = this.findAllByOrderIdAndEmailAddressAndSku(orderId, emailAddress, sku);
		return orderDetails.get(0).getPrice();
	}
}
