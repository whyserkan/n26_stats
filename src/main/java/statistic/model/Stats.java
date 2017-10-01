package statistic.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.boot.context.annotation.DeterminableImports;

import statistic.dto.StatisticsResponseDTO;

import com.google.common.collect.MinMaxPriorityQueue;
import com.google.common.util.concurrent.AtomicDouble;
import com.google.common.util.concurrent.AtomicDoubleArray;

public class Stats {
	private AtomicDouble sum = new AtomicDouble();
	private AtomicDouble avg = new AtomicDouble();
	private AtomicLong count = new AtomicLong();
	private AtomicDouble min = new AtomicDouble();
	private AtomicDouble max = new AtomicDouble();
	private List<Double> allAmountsArray = Collections.synchronizedList(new ArrayList<Double>());
	private boolean minMaxCreated = false;
	
	
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
		return new StatisticsResponseDTO(sum.get(),avg.get(),max.get(),min.get(),count.get());  
	}
	
	public void addNew(double amount){
		sum.addAndGet(amount);
		if(count.get()==0){
			min.set(amount);
			max.set(amount);
		} else {
			min.set(Math.min(min.get(), amount));
			max.set(Math.max(max.get(), amount));
		}
		count.incrementAndGet();
		avg.set(sum.get()/count.doubleValue());
		allAmountsArray.add(amount);
	}
	
	public void removeExpired(double amount){
		if(count.get()==1) {
			sum = new AtomicDouble();
			avg = new AtomicDouble();
			count = new AtomicLong();
			min = new AtomicDouble();
			max = new AtomicDouble();			
		} else {
			count.addAndGet(-1);
			sum.addAndGet(amount * -1);
			avg.set(sum.get()/count.doubleValue());
			findNewMinMaxIfNeeded(amount);
		}
	} 
	
	private void findNewMinMaxIfNeeded(double deletedAmount){
		boolean isMin = min.get() == deletedAmount;
		boolean isMax = max.get() == deletedAmount;
		boolean needToSort = isMin || isMax;
		boolean sameValues = isMin && isMax;
		
		if(needToSort){
			Collections.sort(allAmountsArray);
		}
		
		if(sameValues){
			allAmountsArray.remove(allAmountsArray.size()-1);
		}
		
		if(isMin && !sameValues){
			min.set(allAmountsArray.get(1));
			allAmountsArray.remove(0);
		}
		if(isMax && !sameValues){
			max.set(allAmountsArray.get(allAmountsArray.size()-2));
			allAmountsArray.remove(allAmountsArray.size()-1);
		}
		
		if(!isMax && !isMax){
			allAmountsArray.remove(deletedAmount);
		}
	}

}
