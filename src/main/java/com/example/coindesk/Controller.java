package com.example.coindesk;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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
import org.springframework.web.client.RestTemplate;

import com.example.coindesk.repository.CoindeskRepository;
import com.example.coindesk.util.CoinDeskAPI;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.example.coindesk.entity.Coindesk;
import com.example.coindesk.pojo.constant.ResponseConstant;

@RestController
@RequestMapping("/myapp")
public class Controller {

	@Autowired
	CoindeskRepository cr;

	@Autowired
	static RestTemplate restTemplate = new RestTemplate();

	@GetMapping("/newCoindesk/{id}")
	private ResponseEntity<Coindesk> getCoinById(@PathVariable("id") long id) {
		Optional<Coindesk> coinData = cr.findById(id);
				

		if (coinData.isPresent()) {
			return new ResponseEntity<>(coinData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/newCoindesk")
	private ResponseEntity<Coindesk> createCoin(@RequestBody Coindesk coindesk) {
		try {
			Coindesk newCoindesk = cr.save(
					new Coindesk(coindesk.getName(), coindesk.getRate(), coindesk.getType(), coindesk.getUpdateDate()));
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/newCoindesk/{id}")
	private ResponseEntity<Coindesk> updateCoin(@PathVariable("id") long id, @RequestBody Coindesk coindesk) {
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
	private ResponseEntity<HttpStatus> deleteAllCoin() {
		try {
			cr.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/originalCoindesk")
	private ResponseEntity<String> originalData() {
		String objects = new String();
        ArrayList<Coindesk> allCoin = new ArrayList<Coindesk>();
        
		try {
        	String url = "https://api.coindesk.com/v1/bpi/currentprice.json";
        	objects = restTemplate.getForObject(url, String.class);
        	
            JsonObject json = new Gson().fromJson(objects, JsonObject.class);

            Coindesk usCoin= CoinDeskAPI.setCoin(json, ResponseConstant.USD, ResponseConstant.USD_tw);
            Coindesk gbpCoin= CoinDeskAPI.setCoin(json, ResponseConstant.GBP, ResponseConstant.GBP_tw);
            Coindesk eurCoin= CoinDeskAPI.setCoin(json, ResponseConstant.EUR, ResponseConstant.EUR_tw);

        	List<Coindesk> coinData = cr.findAll();
        	
        	if (coinData.isEmpty()) {
        	
				allCoin.add(usCoin);
				allCoin.add(gbpCoin);
				allCoin.add(eurCoin);
				cr.saveAll(allCoin);
				
        	}else {
        		coinData.get(0).setName(usCoin.getName());
        		coinData.get(0).setRate(usCoin.getRate());
        		coinData.get(0).setType(usCoin.getType());
        		coinData.get(0).setUpdateDate(usCoin.getUpdateDate());
        		
        		coinData.get(1).setName(gbpCoin.getName());
        		coinData.get(1).setRate(gbpCoin.getRate());
        		coinData.get(1).setType(gbpCoin.getType());
        		coinData.get(1).setUpdateDate(gbpCoin.getUpdateDate());
        		
        		coinData.get(2).setName(eurCoin.getName());
        		coinData.get(2).setRate(eurCoin.getRate());
        		coinData.get(2).setType(eurCoin.getType());
        		coinData.get(2).setUpdateDate(eurCoin.getUpdateDate());
        		
        		cr.saveAll(coinData);
        		
            }
           
        	
		return new ResponseEntity<>(objects, HttpStatus.OK);
		} catch (Exception e) {
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/newCoindesk")
	private ResponseEntity<List<Coindesk>> getAllCoin(@RequestParam(required = false) String type) {
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

}
