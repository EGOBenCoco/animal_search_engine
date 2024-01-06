package com.example.animal_search_engine.repository;

import com.example.animal_search_engine.model.Consumer;
import com.example.animal_search_engine.model.ContactInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ContactInfosRepository extends JpaRepository<ContactInfo, Integer> {

    List<ContactInfo> findByConsumerId( int id);
}
