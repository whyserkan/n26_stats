package statistic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import statistic.dto.StatisticsResponseDTO;
import statistic.service.TransactionService;

@RestController
public class StatisticController {
	
	@Autowired
	private TransactionService transactionService;
	
	public static final String STATISTIC_URL = "/statistics";
	
    @RequestMapping(value = STATISTIC_URL)
    public StatisticsResponseDTO getStatistics(){
    	return transactionService.getStats();
    }
}