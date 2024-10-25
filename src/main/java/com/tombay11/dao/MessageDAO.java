package com.tombay11.dao;

import java.util.List;

import com.tombay11.dao.GenericDAO;
import com.tombay11.domain.Message;





public interface MessageDAO extends GenericDAO<Message, Integer> {
  
	List<Message> findAll();
	






}


