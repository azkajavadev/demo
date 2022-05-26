package com.azka.demo.service;

import java.util.List;
import java.util.Optional;

import com.azka.demo.service.dto.ReturnDTO;
import com.azka.demo.service.dto.ReturnQCStatusDTO;
import com.azka.demo.service.dto.ReturnRequestDTO;

public interface ReturnService {

	ReturnDTO save(ReturnRequestDTO returnRequestDTO) throws Exception;

	List<ReturnDTO> findAllByTokenAndSku(String token, String sku);

	Optional<ReturnDTO> findOne(Long id);

	ReturnDTO updateQcStatus(Long id, Long itemId, ReturnQCStatusDTO returnQCStatusDTO) throws Exception;

}