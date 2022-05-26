package com.azka.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.azka.demo.model.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

	List<Token> findAllByOrderIdAndEmailAddress(String orderId, String emailAddress);
	
	List<Token> findAllByToken(String token);
	
	List<Token> findAllByTokenAndIsUsed(String token, boolean isUsed);
}
