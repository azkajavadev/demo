package com.azka.demo.controller;

import java.net.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.azka.demo.service.TokenService;
import com.azka.demo.service.dto.TokenDTO;
import com.azka.demo.service.dto.TokenResultDTO;
import com.azka.demo.util.HeaderUtil;

@RestController
@RequestMapping(value = "/api")
public class TokenController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private TokenService tokenService;
	
	public TokenController(TokenService tokenService) {
		this.tokenService = tokenService;
	}
	
    @PostMapping("/pending-returns")
    public ResponseEntity<TokenResultDTO> createToken(@RequestBody TokenDTO tokenDTO) throws Exception {
        log.debug("REST request to get Token : {}", tokenDTO);
        if (tokenDTO.getId() != null) {
        	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new token cannot already have an ID", null);
        }
        if (!tokenService.isTokenRequestValid(tokenDTO)) {
        	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request", null);
        }
        TokenResultDTO result = tokenService.getToken(tokenDTO);
        return ResponseEntity.created(new URI("/api/pending-returns/"))
            .headers(HeaderUtil.createEntityCreationAlert("Token", result.getToken().toString()))
            .body(result);
    }
}
