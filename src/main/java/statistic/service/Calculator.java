package statistic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import statistic.model.Data;
import statistic.model.DataQueue;

@Component
public class Calculator {
	private DataQueue dataQueue;

	@Autowired
	public Calculator(DataQueue dataQueue) {
		this.dataQueue = dataQueue;
	}
	@Scheduled(fixedRate=1000)
	public void calculate(){
		long now = System.currentTimeMillis();
		boolean last60sec = false;
		while(dataQueue.getQueueSize()!=0 && !last60sec){
			Data pulledData = dataQueue.peek();
			last60sec = now - pulledData.getTime() < dataQueue.EXPIRE_TIME_IN_MILLIS;
			
			if(!last60sec){
				System.out.println("one is removing >> "+pulledData.getAmount()+" - "+pulledData.getTime());
				dataQueue.log();
				dataQueue.removeFromAmounts(dataQueue.remove().getAmount());
				System.out.println("after remove -----");
				dataQueue.log();
			}
		}
	}
}
