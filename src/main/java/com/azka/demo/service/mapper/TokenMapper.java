package com.azka.demo.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.azka.demo.model.Token;
import com.azka.demo.service.dto.TokenDTO;

@Mapper(componentModel = "spring", uses = {})
public interface TokenMapper extends EntityMapper<TokenDTO, Token> {

	TokenDTO toDto(Token token);
	
	@Mapping(target = "createdBy", ignore = true)
	@Mapping(target = "createdDate", ignore = true)
	@Mapping(target = "lastModifiedBy", ignore = true)
	@Mapping(target = "lastModifiedDate", ignore = true)
	Token toEntity(TokenDTO tokenDTO);
}
