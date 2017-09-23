package statistic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import statistic.dto.StatisticsResponseDTO;
import statistic.dto.TransactionDTO;
import statistic.model.DataQueue;


@Service
public class TransactionService {

	private DataQueue dataQueue;

	@Autowired
	public TransactionService(DataQueue dataQueue) {
		this.dataQueue = dataQueue;
	}

	public boolean push(TransactionDTO transaction) {
		return dataQueue.add(transaction);
	}

	public StatisticsResponseDTO getStats() {
		return dataQueue.getStats();
	}
}
