package com.azka.demo.controller;

import java.net.URI;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.azka.demo.service.ReturnService;
import com.azka.demo.service.dto.ReturnDTO;
import com.azka.demo.service.dto.ReturnQCStatusDTO;
import com.azka.demo.service.dto.ReturnRequestDTO;
import com.azka.demo.util.HeaderUtil;

@RestController
@RequestMapping(value = "/api")
public class ReturnController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private ReturnService returnService;
	
	public ReturnController(ReturnService returnService) {
		this.returnService = returnService;
	}
	
    @PostMapping("/returns")
    public ResponseEntity<ReturnDTO> createReturn(@RequestBody ReturnRequestDTO returnRequestDTO) throws Exception {
        log.debug("REST request to save Return : {}", returnRequestDTO);
        if (null == returnRequestDTO.getToken() || returnRequestDTO.getToken().equals("")) {
        	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid token", null);
        }
        if (null == returnRequestDTO.getSku() || returnRequestDTO.getSku().equals("")) {
        	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid item sku", null);
        }
        if (null == returnRequestDTO.getQuantity() || returnRequestDTO.getQuantity() == 0) {
        	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid item quantity", null);
        }
        ReturnDTO result = new ReturnDTO();
        try {
        	result = returnService.save(returnRequestDTO);
        } catch (Exception e) {
        	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
        return ResponseEntity.created(new URI("/api/returns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("Return", result.getId().toString()))
            .body(result);
    }
    
    @GetMapping("/returns/{id}")
    public ResponseEntity<ReturnDTO> getReturn(@PathVariable Long id) {
    	log.debug("REST request to get Return : {}", id);
    	Optional<ReturnDTO> returnDTO = returnService.findOne(id);
    	return ResponseEntity.ok().body(returnDTO.get());
    }
    
	@PutMapping("/returns/{id}/items/{itemId}/qc/status")
	public ResponseEntity<ReturnDTO> updateQcStatus(@PathVariable Long id, @PathVariable Long itemId,
			@RequestBody ReturnQCStatusDTO returnQCStatusDTO) {
		log.debug("REST request to set QC Status on Return : {}", id);
		ReturnDTO returnDTO;
		try {
			returnDTO = returnService.updateQcStatus(id, itemId, returnQCStatusDTO);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), null);
		}
		return ResponseEntity.ok().body(returnDTO);
	}
}
