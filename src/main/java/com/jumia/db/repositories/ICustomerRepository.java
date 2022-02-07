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
	
	@Query("SELECT C FROM Customer C WHERE C.code=:countryCode")
	public Page<Customer> findAllByCountry(Pageable pageable, @Param("countryCode") Integer countryCode);

	@Query("SELECT C from Customer C WHERE C.valid=:estate")
	public Page<Customer> findAllByEstate(Pageable pageable, @Param("estate") Integer estate);
	
	@Query("SELECT C from Customer C WHERE C.valid=:estate AND C.code=:countryCode")
	public Page<Customer> findAllByCountryAndEstate(Pageable pageable, @Param("countryCode") Integer countryCode, @Param("estate") Integer estate);	
	
}