package com.azka.demo.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.azka.demo.model.Return;
import com.azka.demo.service.dto.ReturnDTO;

@Mapper(componentModel = "spring", uses = {})
public interface ReturnMapper extends EntityMapper<ReturnDTO, Return> {

	ReturnDTO toDto(Return returne);
	
	@Mapping(target = "createdBy", ignore = true)
	@Mapping(target = "createdDate", ignore = true)
	@Mapping(target = "lastModifiedBy", ignore = true)
	@Mapping(target = "lastModifiedDate", ignore = true)
	Return toEntity(ReturnDTO returnDTO);
}
