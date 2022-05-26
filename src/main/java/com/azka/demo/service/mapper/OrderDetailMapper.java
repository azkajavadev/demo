package com.azka.demo.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.azka.demo.model.OrderDetail;
import com.azka.demo.service.dto.OrderDetailDTO;

@Mapper(componentModel = "spring", uses = {})
public interface OrderDetailMapper extends EntityMapper<OrderDetailDTO, OrderDetail> {

	OrderDetailDTO toDto(OrderDetail orderDetail);
	
	@Mapping(target = "createdBy", ignore = true)
	@Mapping(target = "createdDate", ignore = true)
	@Mapping(target = "lastModifiedBy", ignore = true)
	@Mapping(target = "lastModifiedDate", ignore = true)
	OrderDetail toEntity(OrderDetailDTO orderDetailDTO);
	
}
