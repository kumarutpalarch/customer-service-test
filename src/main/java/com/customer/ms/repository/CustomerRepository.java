package com.customer.ms.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.customer.ms.model.CustomerM;

@Repository
public interface CustomerRepository extends MongoRepository<CustomerM, String> {

	@Override
	List<CustomerM> findAll();

}
