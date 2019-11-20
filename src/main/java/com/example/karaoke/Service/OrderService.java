package com.example.karaoke.Service;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.example.karaoke.Repository.OrderRepository;
import com.example.karaoke.dto.BillDTO;
import com.example.karaoke.entity.Order;

@Service
public class OrderService  {
	@Autowired
	OrderRepository bService;
	@Autowired
	OrderRepository repo;
	public List<Order> getAll() {
		return repo.findAll();
	}
	public Order save(Order o) {
		return repo.save(o);
	}
	public void remove(int id) {
		repo.deleteById(id);
	}
	public Order getById(int id) {
		Optional<Order> f = repo.findById(id);
		if(f.isPresent()) {
			return f.get();
		}
		return null;
	}
	public List<Order> findOrderOfRoomWithStatusIsactive(boolean status, int id_room) {
		return repo.findOrderOfRoomWithStatusIsactive(status, id_room);
	}

	public List<Order> findAllWithYearAndMonth(int year, int month){
    	return repo.findAllWithYearAndMonth(year, month);
    }
	public List<Order> findAllWithYear(int year){
    	return repo.findAllWithYear(year);
    } 
    public List<Order> findAllWithCreationDateTimeAfter(Date creationDateTime){
    	return repo.findAllWithCreationDateTimeAfter(creationDateTime);
    }
    public List<Order> findAllWithStartDateEnDate(Date start, Date end){
    	return repo.findAllWithStartDateEnDate(start, end);
    }
}
