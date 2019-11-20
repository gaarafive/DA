package com.example.karaoke.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.karaoke.entity.Order;




public interface OrderRepository extends JpaRepository<Order, Integer>{
	@Query("Select o from Order o INNER JOIN o.bill b INNER JOIN o.room r Where b.status = :status and r.id = :id_room")
	public List<Order> findOrderOfRoomWithStatusIsactive(@Param("status") boolean status,@Param("id_room") int id_room);
	
	@Query("select o from Order o where year(o.orderdate) = ?1 and month(o.orderdate) = ?2 and o.bill.status = 1")
	List<Order> findAllWithYearAndMonth(int year, int month);
	
	@Query("select o from Order o where year(o.orderdate) = ?1 and o.bill.status = 1")
	List<Order> findAllWithYear(int year);
	
    @Query("select o from Order o where o.orderdate >= :creationDateTime")
    List<Order> findAllWithCreationDateTimeAfter(@Param("creationDateTime") Date creationDateTime);
    
    @Query("select o from Order o where o.orderdate >= :startDate and  o.orderdate <= :endDate and o.bill.status = 1")
    List<Order> findAllWithStartDateEnDate(Date startDate, Date endDate);
}
