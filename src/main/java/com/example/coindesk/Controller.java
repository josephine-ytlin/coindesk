package com.example.coindesk;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.coindesk.util.CallCoinDesk;

@RestController
@RequestMapping("/myapp")
public class Controller {
	

	
	@GetMapping(value = "/originalCoindesk")
	private String originalData() {
		String originalData = CallCoinDesk.callCoindeskAPI();
		System.out.print(originalData);
		return originalData;
	}


}
