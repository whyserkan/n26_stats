package statistic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import statistic.dto.StatisticsResponseDTO;
import statistic.dto.TransactionDTO;
import statistic.model.TimeArray;


@Service
public class TransactionService {

	private TimeArray timeArray;

	@Autowired
	public TransactionService(TimeArray timeArray) {
		this.timeArray = timeArray;
	}

	public boolean push(TransactionDTO transaction) {
		return timeArray.add(transaction);
	}

	public StatisticsResponseDTO getStats() {
		return timeArray.calculate();
	}
}
