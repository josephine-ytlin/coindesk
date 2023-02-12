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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.coindesk.repository.CoindeskRepository;
import com.example.coindesk.util.CoinDeskAPI;
import com.google.gson.Gson;
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

	@GetMapping("/newCoindesk/{type}")
	private ResponseEntity<Coindesk> getCoin(@PathVariable("type") String type) {
		Optional<Coindesk> coinData = cr.findByType(type);
				

		if (coinData.isPresent()) {
			return new ResponseEntity<>(coinData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/newCoindesk")
	private ResponseEntity<Coindesk> createCoin(@RequestBody Coindesk coindesk) {
		try {
			cr.save(new Coindesk(coindesk.getName(), coindesk.getRate(), coindesk.getType(), coindesk.getUpdateDate()));
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/newCoindesk/{type}")
	private ResponseEntity<Coindesk> updateCoin(@PathVariable("type") String type, @RequestBody Coindesk coindesk) {
		Optional<Coindesk> coinData = cr.findByType(type);

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

	@DeleteMapping("/newCoindesk/{type}")
	private ResponseEntity<HttpStatus> deleteCoin(@PathVariable("type") String type) {
		try {

			cr.deleteByType(type);
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

            Coindesk usdCoin= CoinDeskAPI.setCoin(json, ResponseConstant.USD, ResponseConstant.USD_tw);
            Coindesk gbpCoin= CoinDeskAPI.setCoin(json, ResponseConstant.GBP, ResponseConstant.GBP_tw);
            Coindesk eurCoin= CoinDeskAPI.setCoin(json, ResponseConstant.EUR, ResponseConstant.EUR_tw);

        	List<Coindesk> coinData = cr.findAll();
        	
        	if (coinData.isEmpty()) {
        	
				allCoin.add(usdCoin);
				allCoin.add(gbpCoin);
				allCoin.add(eurCoin);
				cr.saveAll(allCoin);
				
        	}else {
        		Optional<Coindesk> oldCoinUSD = cr.findByType(ResponseConstant.USD);
        		Optional<Coindesk> oldCoinGBP = cr.findByType(ResponseConstant.GBP);
        		Optional<Coindesk> oldCoinEUR = cr.findByType(ResponseConstant.EUR);
        		
        		if (oldCoinUSD.isPresent()) {
        			
        			Coindesk newCoindesk = oldCoinUSD.get();
        			newCoindesk.setName(usdCoin.getName());
        			newCoindesk.setRate(usdCoin.getRate());
        			newCoindesk.setType(usdCoin.getType());
        			newCoindesk.setUpdateDate(usdCoin.getUpdateDate());
        		}else {
        			cr.save(usdCoin);
        		}
        		
        		if (oldCoinGBP.isPresent()) {
        			
        			Coindesk newCoindesk = oldCoinGBP.get();
        			newCoindesk.setName(gbpCoin.getName());
        			newCoindesk.setRate(gbpCoin.getRate());
        			newCoindesk.setType(gbpCoin.getType());
        			newCoindesk.setUpdateDate(gbpCoin.getUpdateDate());
        		}else {
        			cr.save(gbpCoin);
        		}
        		
        		if (oldCoinEUR.isPresent()) {
        			
        			Coindesk newCoindesk = oldCoinEUR.get();
        			newCoindesk.setName(eurCoin.getName());
        			newCoindesk.setRate(eurCoin.getRate());
        			newCoindesk.setType(eurCoin.getType());
        			newCoindesk.setUpdateDate(eurCoin.getUpdateDate());
        		}else {
        			cr.save(eurCoin);
        		}
        		
            }
           
        	
		return new ResponseEntity<>(objects, HttpStatus.OK);
		} catch (Exception e) {
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/newCoindesk")
	private ResponseEntity<List<Coindesk>> getAllCoin() {
		try {
			List<Coindesk> cd = new ArrayList<Coindesk>();

				cr.findAll().forEach(cd::add);
			if (cd.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}else {

			return new ResponseEntity<>(cd, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
