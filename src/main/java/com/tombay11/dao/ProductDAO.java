package com.tombay11.dao;

import java.util.List;

import com.tombay11.dao.GenericDAO;
import com.tombay11.domain.Product;





public interface ProductDAO extends GenericDAO<Product, Integer> {
  
	List<Product> findAll();
	






}


