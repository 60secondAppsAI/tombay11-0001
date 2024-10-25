package com.tombay11.dao;

import java.util.List;

import com.tombay11.dao.GenericDAO;
import com.tombay11.domain.Buyer;





public interface BuyerDAO extends GenericDAO<Buyer, Integer> {
  
	List<Buyer> findAll();
	






}


