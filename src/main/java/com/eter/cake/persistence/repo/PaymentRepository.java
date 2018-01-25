package com.eter.cake.persistence.repo;

import com.eter.cake.persistence.entity.Payment;
import com.eter.cake.persistence.entity.Product;
import com.eter.cake.persistence.entity.SellPrice;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PaymentRepository extends PagingAndSortingRepository<Payment, String>{
	Payment findById(String id);
}
