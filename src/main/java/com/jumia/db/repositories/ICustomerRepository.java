package com.jumia.db.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.jumia.db.model.Customer;

public interface ICustomerRepository extends PagingAndSortingRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
	public Page<Customer> findAll(Pageable pageable);
	
	@Query("select c from Customer c where c.phone like :countryCode%")
	public Page<Customer> findAllByCountry(Pageable pageable, @Param("countryCode") String countryCode);

}