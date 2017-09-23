package statistic.model;

import java.util.concurrent.atomic.AtomicLong;

import statistic.dto.StatisticsResponseDTO;

import com.google.common.collect.MinMaxPriorityQueue;
import com.google.common.util.concurrent.AtomicDouble;

public class Stats {
	private AtomicDouble sum = new AtomicDouble();
	private AtomicDouble avg = new AtomicDouble();
	private AtomicLong count = new AtomicLong();
	private AtomicDouble min = new AtomicDouble();
	private AtomicDouble max = new AtomicDouble();
	
	
	public AtomicDouble getSum() {
		return sum;
	}
	public AtomicDouble getAvg() {
		return avg;
	}
	public AtomicLong getCount() {
		return count;
	}
	public AtomicDouble getMin() {
		return min;
	}
	public AtomicDouble getMax() {
		return max;
	}
	
	public StatisticsResponseDTO getDTO(){
		return  new StatisticsResponseDTO(sum.get(),avg.get(),max.get(),min.get(),count.get());  
	}
	
	public void addNew(double amount){
		count.incrementAndGet();
		sum.addAndGet(amount);
		min.set(Math.min(min.get(), amount));
		max.set(Math.max(max.get(), amount));
		avg.set(sum.get()/count.doubleValue());
		minMax.add(amount);
	}
	
	public void removeExpired(double amount){
		if(count.get()==1){
			sum = new AtomicDouble();
			avg = new AtomicDouble();
			count = new AtomicLong();
			min = new AtomicDouble();
			max = new AtomicDouble();			
		} else {
			count.addAndGet(-1);
			sum.addAndGet(amount * -1);
			min.set(Math.min(min.get(), amount));
			max.set(Math.max(max.get(), amount));
			avg.set(sum.get()/count.doubleValue());			
		}
	} 
	private void setNewMinMaxIfNeeded(double deletedAmount){
		//double maxFromQueue = minMax.peekLast();
		if(deletedAmount==maxFromQueue){
			minMax.removeLast();
			max = minMax.peekLast();
		}
	}

}
