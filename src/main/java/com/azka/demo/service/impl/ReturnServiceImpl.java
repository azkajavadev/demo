package com.azka.demo.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.azka.demo.model.Return;
import com.azka.demo.repository.ReturnRepository;
import com.azka.demo.service.OrderDetailService;
import com.azka.demo.service.ReturnService;
import com.azka.demo.service.TokenService;
import com.azka.demo.service.dto.ReturnDTO;
import com.azka.demo.service.dto.ReturnQCStatusDTO;
import com.azka.demo.service.dto.ReturnRequestDTO;
import com.azka.demo.service.dto.TokenDTO;
import com.azka.demo.service.mapper.ReturnMapper;

@Service
@Transactional
public class ReturnServiceImpl implements ReturnService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private final ReturnRepository returnRepository;
	
	private final ReturnMapper returnMapper;

	private final OrderDetailService orderDetailService;
	
	private final TokenService tokenService;
	
	public ReturnServiceImpl(ReturnRepository returnRepository, ReturnMapper returnMapper,
			OrderDetailService orderDetailService, TokenService tokenService) {
		this.returnRepository = returnRepository;
		this.returnMapper = returnMapper;
		this.orderDetailService = orderDetailService;
		this.tokenService = tokenService;
	}
	
	@Override
	public ReturnDTO save(ReturnRequestDTO returnRequestDTO) throws Exception {
        log.debug("Request to save Return : {}", returnRequestDTO);
        // validation
        List<TokenDTO> tokens = tokenService.findAllByToken(returnRequestDTO.getToken());
        if (tokens.isEmpty()) {
        	throw new Exception("Invalid token");
        }
        // check if token valid
        if (isTokenValid(returnRequestDTO)) {
    		tokenService.expireToken(returnRequestDTO.getToken());
        } else {
    		throw new Exception("Invalid token");
        }
        // check qty of item is still available for this return creation
        ReturnDTO returnDTO = returnRequestDTOToReturnDTO(returnRequestDTO, tokens.get(0));
        if (!isValidQuantity(tokens.get(0).getOrderId(), tokens.get(0).getEmailAddress(), returnDTO.getSku(), returnDTO.getQuantity())) {
        	throw new Exception("Invalid item quantity");
        }
        Return returne = returnMapper.toEntity(returnDTO);
        return returnMapper.toDto(returnRepository.save(returne));
	}
	
	@Override
	public List<ReturnDTO> findAllByTokenAndSku(String token, String sku) {
		log.debug("Request to get Return by token and sku : {} - {}", token, sku);
		return returnRepository.findAllByTokenAndSku(token, sku).stream().filter(Objects::nonNull).map(returnMapper::toDto)
				.collect(Collectors.toList());
	}
	
	private ReturnDTO returnRequestDTOToReturnDTO(ReturnRequestDTO returnRequestDTO, TokenDTO tokenDTO) {
		ReturnDTO returnDTO = new ReturnDTO();
		returnDTO.setToken(returnRequestDTO.getToken());
		returnDTO.setSku(returnRequestDTO.getSku());
		returnDTO.setQuantity(returnRequestDTO.getQuantity());
		double unitRefundAmount = orderDetailService.getOrderDetailItemPrice(tokenDTO.getOrderId(),
				tokenDTO.getEmailAddress(), returnRequestDTO.getSku());
		returnDTO.setUnitRefundAmount(unitRefundAmount);
		double totalRefundAmount = unitRefundAmount * returnRequestDTO.getQuantity();
		returnDTO.setTotalRefundAmount(totalRefundAmount);
		returnDTO.setStatus("AWAITING_APPROVAL");
		return returnDTO;
	}
	
	private boolean isTokenValid(ReturnRequestDTO returnRequestDTO) {
		boolean result = false;
		List<TokenDTO> tokens = tokenService.findAllByTokenAndIsUsed(returnRequestDTO.getToken(), false);
		if (!tokens.isEmpty()) {
			result = true;
		}
		return result;
	}
	
	private boolean isValidQuantity(String orderId, String emailAddress, String sku, int quantity) {
		boolean result = false;
		// get qty of order item (A)
		int orderItemQty = orderDetailService.getOrderDetailItemQuantity(orderId, emailAddress, sku);
		// get qty of returned item (if any) (B)
		int returnedItemQty = getReturnQuantityByOrderIdAndEmailAddress(orderId, emailAddress, sku);
		// if requested quantity <= (A-B) then it is valid 
		if (quantity <= (orderItemQty - returnedItemQty)) {
			result = true;
		}
		return result;
	}

	private int getReturnQuantityByOrderIdAndEmailAddress(String orderId, String emailAddress, String sku) {
		int result = 0;
		List<TokenDTO> tokens = tokenService.findAllByOrderIdAndEmailAddress(orderId, emailAddress);
		for (TokenDTO token : tokens) {
			List<ReturnDTO> returns = this.findAllByTokenAndSku(token.getToken(), sku);
			for (ReturnDTO returne : returns) {
				result += returne.getQuantity();
			}
		}
		return result;
	}
	
	@Override
    public Optional<ReturnDTO> findOne(Long id) {
        log.debug("Request to get Return : {}", id);
        return returnRepository.findById(id).map(returnMapper::toDto);
    }
	
	@Override
	public ReturnDTO updateQcStatus(Long id, Long itemId, ReturnQCStatusDTO returnQCStatusDTO) throws Exception {
		log.debug("Request to set QC Status on Return : {}", id);
		ReturnDTO returnDTO = new ReturnDTO();
		Optional<ReturnDTO> returnOptional = returnRepository.findById(id).map(returnMapper::toDto);
		if (returnOptional.isPresent()) {
			returnDTO = returnOptional.get();
		} else {
			throw new Exception("Invalid id");
		}
		if (null!=returnQCStatusDTO.getQcStatus() && !returnQCStatusDTO.getQcStatus().equals("")) {
			if (isQCStatus(returnQCStatusDTO.getQcStatus())) {
				returnDTO.setQcStatus(returnQCStatusDTO.getQcStatus());
			} else {
				throw new Exception("Invalid QC Status");
			}
		} else {
			throw new Exception("Invalid QC Status");
		}
		return returnDTO;
	}
	
	private boolean isQCStatus(String string) {
		String arr[] = {"ACCEPTED", "REJECTED"}; 
	    return Arrays.asList(arr).contains(string); 
	}
	
}
