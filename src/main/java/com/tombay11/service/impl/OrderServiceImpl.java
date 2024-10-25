package com.tombay11.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;



import com.tombay11.dao.GenericDAO;
import com.tombay11.service.GenericService;
import com.tombay11.service.impl.GenericServiceImpl;
import com.tombay11.dao.OrderDAO;
import com.tombay11.domain.Order;
import com.tombay11.dto.OrderDTO;
import com.tombay11.dto.OrderSearchDTO;
import com.tombay11.dto.OrderPageDTO;
import com.tombay11.dto.OrderConvertCriteriaDTO;
import com.tombay11.dto.common.RequestDTO;
import com.tombay11.dto.common.ResultDTO;
import com.tombay11.service.OrderService;
import com.tombay11.util.ControllerUtils;





@Service
public class OrderServiceImpl extends GenericServiceImpl<Order, Integer> implements OrderService {

    private final static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Autowired
	OrderDAO orderDao;

	


	@Override
	public GenericDAO<Order, Integer> getDAO() {
		return (GenericDAO<Order, Integer>) orderDao;
	}
	
	public List<Order> findAll () {
		List<Order> orders = orderDao.findAll();
		
		return orders;	
		
	}

	public ResultDTO addOrder(OrderDTO orderDTO, RequestDTO requestDTO) {

		Order order = new Order();

		order.setOrderId(orderDTO.getOrderId());


		order.setQuantity(orderDTO.getQuantity());


		order.setTotalAmount(orderDTO.getTotalAmount());


		order.setOrderDate(orderDTO.getOrderDate());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		order = orderDao.save(order);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Order> getAllOrders(Pageable pageable) {
		return orderDao.findAll(pageable);
	}

	public Page<Order> getAllOrders(Specification<Order> spec, Pageable pageable) {
		return orderDao.findAll(spec, pageable);
	}

	public ResponseEntity<OrderPageDTO> getOrders(OrderSearchDTO orderSearchDTO) {
	
			Integer orderId = orderSearchDTO.getOrderId(); 
 			Integer quantity = orderSearchDTO.getQuantity(); 
    			String sortBy = orderSearchDTO.getSortBy();
			String sortOrder = orderSearchDTO.getSortOrder();
			String searchQuery = orderSearchDTO.getSearchQuery();
			Integer page = orderSearchDTO.getPage();
			Integer size = orderSearchDTO.getSize();

	        Specification<Order> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, orderId, "orderId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, quantity, "quantity"); 
			
			
 			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

		));}
		
		Sort sort = Sort.unsorted();
		if (sortBy != null && !sortBy.isEmpty() && sortOrder != null && !sortOrder.isEmpty()) {
			if (sortOrder.equalsIgnoreCase("asc")) {
				sort = Sort.by(sortBy).ascending();
			} else if (sortOrder.equalsIgnoreCase("desc")) {
				sort = Sort.by(sortBy).descending();
			}
		}
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<Order> orders = this.getAllOrders(spec, pageable);
		
		//System.out.println(String.valueOf(orders.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(orders.getTotalPages()));
		
		List<Order> ordersList = orders.getContent();
		
		OrderConvertCriteriaDTO convertCriteria = new OrderConvertCriteriaDTO();
		List<OrderDTO> orderDTOs = this.convertOrdersToOrderDTOs(ordersList,convertCriteria);
		
		OrderPageDTO orderPageDTO = new OrderPageDTO();
		orderPageDTO.setOrders(orderDTOs);
		orderPageDTO.setTotalElements(orders.getTotalElements());
		return ResponseEntity.ok(orderPageDTO);
	}

	public List<OrderDTO> convertOrdersToOrderDTOs(List<Order> orders, OrderConvertCriteriaDTO convertCriteria) {
		
		List<OrderDTO> orderDTOs = new ArrayList<OrderDTO>();
		
		for (Order order : orders) {
			orderDTOs.add(convertOrderToOrderDTO(order,convertCriteria));
		}
		
		return orderDTOs;

	}
	
	public OrderDTO convertOrderToOrderDTO(Order order, OrderConvertCriteriaDTO convertCriteria) {
		
		OrderDTO orderDTO = new OrderDTO();
		
		orderDTO.setOrderId(order.getOrderId());

	
		orderDTO.setQuantity(order.getQuantity());

	
		orderDTO.setTotalAmount(order.getTotalAmount());

	
		orderDTO.setOrderDate(order.getOrderDate());

	

		
		return orderDTO;
	}

	public ResultDTO updateOrder(OrderDTO orderDTO, RequestDTO requestDTO) {
		
		Order order = orderDao.getById(orderDTO.getOrderId());

		order.setOrderId(ControllerUtils.setValue(order.getOrderId(), orderDTO.getOrderId()));

		order.setQuantity(ControllerUtils.setValue(order.getQuantity(), orderDTO.getQuantity()));

		order.setTotalAmount(ControllerUtils.setValue(order.getTotalAmount(), orderDTO.getTotalAmount()));

		order.setOrderDate(ControllerUtils.setValue(order.getOrderDate(), orderDTO.getOrderDate()));



        order = orderDao.save(order);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public OrderDTO getOrderDTOById(Integer orderId) {
	
		Order order = orderDao.getById(orderId);
			
		
		OrderConvertCriteriaDTO convertCriteria = new OrderConvertCriteriaDTO();
		return(this.convertOrderToOrderDTO(order,convertCriteria));
	}







}
