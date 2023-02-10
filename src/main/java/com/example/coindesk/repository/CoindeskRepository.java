package com.example.coindesk.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.coindesk.entity.Coindesk;


public interface CoindeskRepository extends JpaRepository<Coindesk, Long>{

}
