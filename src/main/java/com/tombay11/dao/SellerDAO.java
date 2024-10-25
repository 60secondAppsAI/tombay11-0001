package com.tombay11.dao;

import java.util.List;

import com.tombay11.dao.GenericDAO;
import com.tombay11.domain.Seller;





public interface SellerDAO extends GenericDAO<Seller, Integer> {
  
	List<Seller> findAll();
	






}


