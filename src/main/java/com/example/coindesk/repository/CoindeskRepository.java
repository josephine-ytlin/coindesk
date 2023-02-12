package com.example.coindesk.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import com.example.coindesk.entity.Coindesk;

public interface CoindeskRepository extends JpaRepository<Coindesk, Long>{
	
	public Optional<Coindesk> findByType(String type);

	@Transactional
	void deleteByType(String type);	

}
