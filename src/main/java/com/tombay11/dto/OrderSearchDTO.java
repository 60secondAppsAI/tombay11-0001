package com.tombay11.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer orderId;
	
	private Integer quantity;
	
	private Float totalAmount;
	
	private Date orderDate;
	
}
