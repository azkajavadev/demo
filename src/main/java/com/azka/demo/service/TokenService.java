package com.azka.demo.service;

import java.util.List;

import com.azka.demo.service.dto.TokenDTO;
import com.azka.demo.service.dto.TokenResultDTO;

public interface TokenService {

	TokenDTO save(TokenDTO tokenDTO);

	boolean isTokenRequestValid(TokenDTO tokenDTO);

	TokenResultDTO getToken(TokenDTO tokenDTO);

	List<TokenDTO> findAllByToken(String token);

	List<TokenDTO> findAllByTokenAndIsUsed(String token, boolean isUsed);
	
	List<TokenDTO> findAllByOrderIdAndEmailAddress(String orderId, String emailAddress);

	void expireToken(String token);

}