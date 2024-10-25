package com.tombay11.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AuctionPageDTO {

	private Integer page = 0;
	private Long totalElements = 0L;

	private List<AuctionDTO> auctions;
}





