package com.tombay11.dao;

import java.util.List;

import com.tombay11.dao.GenericDAO;
import com.tombay11.domain.Payment;





public interface PaymentDAO extends GenericDAO<Payment, Integer> {
  
	List<Payment> findAll();
	






}


