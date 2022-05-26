package com.azka.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.azka.demo.model.OrderDetail;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

	List<OrderDetail> findAllByOrderIdAndEmailAddress(String orderId, String emailAddress);
	
	List<OrderDetail> findAllByOrderIdAndEmailAddressAndSku(String orderId, String emailAddress, String sku);
}
