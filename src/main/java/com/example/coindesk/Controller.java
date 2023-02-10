package com.example.coindesk;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.coindesk.repository.CoindeskRepository;
import com.example.coindesk.util.CallCoinDesk;
import com.example.coindesk.entity.Coindesk;


@RestController
@RequestMapping("/myapp")
public class Controller {
	
	@Autowired
	CoindeskRepository cr;
	

	@GetMapping("/newCoindesk")
	public ResponseEntity<List<Coindesk>> getAllCoin(@RequestParam(required = false) String type) {
		try {
			List<Coindesk> cd = new ArrayList<Coindesk>();

			if (type == null) {
				cr.findAll().forEach(cd::add);
			}

			if (cd.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(cd, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/newCoindesk")
	public ResponseEntity<Coindesk> createCoin(@RequestBody Coindesk coindesk) {
		try {
			Coindesk newCoindesk = cr
					.save(new Coindesk(coindesk.getName(),
							coindesk.getRate(),
							coindesk.getType(),
							coindesk.getUpdateDate()));
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/newCoindesk/{id}")
	public ResponseEntity<Coindesk> updateCoin(@PathVariable("id") long id, @RequestBody Coindesk coindesk) {
		Optional<Coindesk> coinData = cr.findById(id);

		if (coinData.isPresent()) {
			Coindesk newCoindesk = coinData.get();
			newCoindesk.setName(coindesk.getName());
			newCoindesk.setRate(coindesk.getRate());
			newCoindesk.setType(coindesk.getType());
			newCoindesk.setUpdateDate(coindesk.getUpdateDate());
			cr.save(newCoindesk);
			
			return new ResponseEntity<>(newCoindesk, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/newCoindesk")
	public ResponseEntity<HttpStatus> deleteAllCoin() {
		try {
			cr.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@GetMapping("/originalCoindesk")
	private ResponseEntity<String> originalData() {
		try {
		String originalData = CallCoinDesk.callCoindeskAPI();
		
		System.out.print(originalData);
//		return originalData;
		
		return new ResponseEntity<>(originalData, HttpStatus.OK);
		} catch (Exception e) {
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	
	
	@GetMapping("/newCoindesk/{id}")
	public ResponseEntity<Coindesk> getCoinById(@PathVariable("id") long id) {
		Optional<Coindesk> coinData = cr.findById(id);

		if (coinData.isPresent()) {
			return new ResponseEntity<>(coinData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	




}
