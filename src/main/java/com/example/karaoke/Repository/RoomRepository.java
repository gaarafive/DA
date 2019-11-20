package com.example.karaoke.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.karaoke.entity.Room;


public interface RoomRepository extends JpaRepository<Room, Integer>{
	@Query("From Room r Where r.status = :status")
	public List<Room> findRoomWithIsActive(@Param("status") boolean status);
	List<Room> findByNameContaining(String term);
}
