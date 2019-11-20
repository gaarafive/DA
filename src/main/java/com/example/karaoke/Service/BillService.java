package com.example.karaoke.Service;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.karaoke.Repository.BillRepository;
import com.example.karaoke.entity.Bill;

@Service
public class BillService {
	@Autowired
	BillRepository repo;
	public List<Bill> getAll() {
		return repo.findAll();
	}
	public Bill save(Bill food) {
		return repo.save(food);
	}
	public void remove(int id) {
		repo.deleteById(id);
	}
	public Bill getById(int id) {
		Optional<Bill> f = repo.findById(id);
		if(f.isPresent()) {
			return f.get();
		}
		return null;
	}
	public List<Bill> findAllWithStartDateEnDate(Date start, Date end){
    	return repo.findAllWithStartDateEnDate(start, end);
    }
}
