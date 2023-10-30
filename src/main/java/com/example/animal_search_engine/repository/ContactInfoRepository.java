package com.example.animal_search_engine.repository;

import com.example.animal_search_engine.model.ContactInfo;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactInfoRepository extends JpaRepository<ContactInfo,Integer> {

    //@EntityGraph(attributePaths = "consumer.announcements")
  //  @Query("select c from ContactInfo  c JOIN FETCH c.consumer")
    List<ContactInfo> findAllByConsumerId(int id);


    @Query("select c from ContactInfo  c JOIN FETCH c.consumer cus JOIN FETCH  cus.announcements")
    List<ContactInfo> findAll();
}
