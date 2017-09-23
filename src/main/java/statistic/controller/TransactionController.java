package statistic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import statistic.dto.TransactionDTO;
import statistic.service.TransactionService;

@RestController
public class TransactionController {
	@Autowired
	private TransactionService transactionService;
	
	public static final String TRANSACTIONS_URL = "/transactions";
	
    @RequestMapping(value = TRANSACTIONS_URL, method = RequestMethod.POST)
    public ResponseEntity<Object> transactions(@RequestBody TransactionDTO transaction) throws Exception {
    	if(transactionService.push(transaction)){
    		return ResponseEntity.status(HttpStatus.CREATED).build();	
    	} else {
    		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    	}
    }
}