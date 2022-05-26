package com.azka.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.azka.demo.service.dto.OrderDetailDTO;

public interface OrderDetailService {

	Page<OrderDetailDTO> findAll(Pageable pageable);
	
	List<OrderDetailDTO> findAllByOrderIdAndEmailAddress(String orderId, String emailAddress);

	List<OrderDetailDTO> findAllByOrderIdAndEmailAddressAndSku(String orderId, String emailAddress, String sku);
	
	boolean isOrderDetailExistByOrderIdAndEmailAddress(String orderId, String emailAddress);

	int getOrderDetailItemQuantity(String orderId, String emailAddress, String sku);

	double getOrderDetailItemPrice(String orderId, String emailAddress, String sku);

}