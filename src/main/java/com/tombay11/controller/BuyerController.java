package com.tombay11.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.ArrayList;


import com.tombay11.util.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.Date;

import com.tombay11.domain.Buyer;
import com.tombay11.dto.BuyerDTO;
import com.tombay11.dto.BuyerSearchDTO;
import com.tombay11.dto.BuyerPageDTO;
import com.tombay11.service.BuyerService;
import com.tombay11.dto.common.RequestDTO;
import com.tombay11.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/buyer")
@RestController
public class BuyerController {

	private final static Logger logger = LoggerFactory.getLogger(BuyerController.class);

	@Autowired
	BuyerService buyerService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Buyer> getAll() {

		List<Buyer> buyers = buyerService.findAll();
		
		return buyers;	
	}

	@GetMapping(value = "/{buyerId}")
	@ResponseBody
	public BuyerDTO getBuyer(@PathVariable Integer buyerId) {
		
		return (buyerService.getBuyerDTOById(buyerId));
	}

 	@RequestMapping(value = "/addBuyer", method = RequestMethod.POST)
	public ResponseEntity<?> addBuyer(@RequestBody BuyerDTO buyerDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = buyerService.addBuyer(buyerDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/buyers")
	public ResponseEntity<BuyerPageDTO> getBuyers(BuyerSearchDTO buyerSearchDTO) {
 
		return buyerService.getBuyers(buyerSearchDTO);
	}	

	@RequestMapping(value = "/updateBuyer", method = RequestMethod.POST)
	public ResponseEntity<?> updateBuyer(@RequestBody BuyerDTO buyerDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = buyerService.updateBuyer(buyerDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
