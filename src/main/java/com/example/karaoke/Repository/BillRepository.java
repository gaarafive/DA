package com.example.karaoke.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.karaoke.entity.Bill;


public interface BillRepository extends JpaRepository<Bill, Integer>{
	@Query("select b from Bill b where b.order.orderdate >= :startDate and  b.order.orderdate <= :endDate")
    List<Bill> findAllWithStartDateEnDate(Date startDate, Date endDate);
}
