package com.azka.demo.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.azka.demo.model.Token;
import com.azka.demo.repository.TokenRepository;
import com.azka.demo.service.OrderDetailService;
import com.azka.demo.service.TokenService;
import com.azka.demo.service.dto.TokenDTO;
import com.azka.demo.service.dto.TokenResultDTO;
import com.azka.demo.service.mapper.TokenMapper;
import com.azka.demo.util.CommonUtil;

@Service
@Transactional
public class TokenServiceImpl implements TokenService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private final TokenRepository tokenRepository;
	
	private final TokenMapper tokenMapper;
	
	private final OrderDetailService orderDetailService;

	public TokenServiceImpl(TokenRepository tokenRepository, TokenMapper tokenMapper, OrderDetailService  orderDetailService) {
		this.tokenRepository = tokenRepository;
		this.tokenMapper = tokenMapper;
		this.orderDetailService = orderDetailService;
	}
	
	@Override
	public TokenDTO save(TokenDTO tokenDTO) {
        log.debug("Request to save Token : {}", tokenDTO);
        Token token = tokenMapper.toEntity(tokenDTO);
        token.setCreatedBy("admin");
        if (null==tokenDTO.getId()) {
            token.setToken(CommonUtil.randomString());	
        }
        return tokenMapper.toDto(tokenRepository.save(token));
	}
	
	@Override
	public boolean isTokenRequestValid(TokenDTO tokenDTO) {
		boolean result = true;
		// whether orderId & emailAddress exist in order list
		if (!orderDetailService.isOrderDetailExistByOrderIdAndEmailAddress(tokenDTO.getOrderId(), tokenDTO.getEmailAddress())) {
			result = false;
		}
		return result;
	}
	
	@Override
	public TokenResultDTO getToken(TokenDTO tokenDTO) {
		TokenDTO sourceDTO = this.save(tokenDTO);
		TokenResultDTO resultDTO = new TokenResultDTO();
		resultDTO.setToken(sourceDTO.getToken());
		return resultDTO;
	}

	@Override
	public List<TokenDTO> findAllByToken(String token) {
		return tokenRepository.findAllByToken(token).stream().filter(Objects::nonNull).map(tokenMapper::toDto)
				.collect(Collectors.toList());
	}
	
	@Override
	public List<TokenDTO> findAllByTokenAndIsUsed(String token, boolean isUsed) {
		return tokenRepository.findAllByTokenAndIsUsed(token, isUsed).stream().filter(Objects::nonNull).map(tokenMapper::toDto)
				.collect(Collectors.toList());
	}
	
	@Override
	public List<TokenDTO> findAllByOrderIdAndEmailAddress(String orderId, String emailAddress) {
		return tokenRepository.findAllByOrderIdAndEmailAddress(orderId, emailAddress).stream().filter(Objects::nonNull)
				.map(tokenMapper::toDto).collect(Collectors.toList());
	}
	
	@Override
	public void expireToken(String token) {
		List<TokenDTO> tokens = this.findAllByToken(token);
		if (!tokens.isEmpty()) {
			TokenDTO tokenDTO = tokens.get(0);
			tokenDTO.setUsed(true);
			this.save(tokenDTO);
		}
	}
}
