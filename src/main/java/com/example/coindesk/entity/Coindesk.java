package com.example.coindesk.entity;

import java.util.Date;

import org.joda.time.DateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class Coindesk {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String type;

    @Column
    String name;

    @Column
    Integer rate;
    
    @Column
    Date update_date;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

	public Date getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}
    
    
    
    
	
	

}
