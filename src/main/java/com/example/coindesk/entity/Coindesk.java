package com.example.coindesk.entity;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "coindesk")
public class Coindesk implements Serializable {
	
    private static final long serialVersionUID = 1L;



//	private static final Long serialVersionUID = 1L;

	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "type")
	private String type;

    @Column(name = "name")
    private String name;

    @Column(name = "rate")
    private String rate;
    
    @Column(name = "updateDate")
    private String updateDate;
    
    
    
    public Coindesk(String name, String rate, String type, String updateDate) {
    	this.name = name;
    	this.rate = rate;
    	this.type = type;
    	this.updateDate =updateDate; 
    	
    }

	public Coindesk() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
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

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	
    
    
    
    
	
	

}
