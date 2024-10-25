package com.tombay11.dao;

import java.util.List;

import com.tombay11.dao.GenericDAO;
import com.tombay11.domain.Bid;





public interface BidDAO extends GenericDAO<Bid, Integer> {
  
	List<Bid> findAll();
	






}


