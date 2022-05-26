package com.azka.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azka.demo.service.OrderDetailService;
import com.azka.demo.service.dto.OrderDetailDTO;
import com.azka.demo.util.PaginationUtil;

@RestController
@RequestMapping(value = "/api")
public class OrderDetailController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private OrderDetailService orderDetailService;
	
	public OrderDetailController(OrderDetailService orderDetailService) {
		this.orderDetailService = orderDetailService;
	}
	
    @GetMapping(value = "/order-details", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllOrder(Pageable pageable, boolean isunpaged) {
    	log.debug("REST request to get all OrderDetails");
    	if (isunpaged) {
    		pageable = Pageable.unpaged();
    	}
    	Page<OrderDetailDTO> page = orderDetailService.findAll(pageable);
    	HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/order-details");
    	return new ResponseEntity<>(page, headers, HttpStatus.OK);
    }
}
