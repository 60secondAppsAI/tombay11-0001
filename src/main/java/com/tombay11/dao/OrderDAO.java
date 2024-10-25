package com.tombay11.dao;

import java.util.List;

import com.tombay11.dao.GenericDAO;
import com.tombay11.domain.Order;





public interface OrderDAO extends GenericDAO<Order, Integer> {
  
	List<Order> findAll();
	






}


