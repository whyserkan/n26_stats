package statistic.model;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReferenceArray;

import statistic.dto.StatisticsResponseDTO;
import statistic.dto.TransactionDTO;

public class TimeArray {
	private AtomicReferenceArray<StatOfSecond> stats =  new AtomicReferenceArray<StatOfSecond>(60);
	
	private final int EXPIRE_TIME_IN_MILLIS = 60 * 1000;
	
	private AtomicLong baseTime = new AtomicLong();
	
	private enum Result {
		MERGED,
		SHIFTED,
		INSERTED,
		ERROR
	};
	
	public boolean add(TransactionDTO transaction) {
		long now = System.currentTimeMillis();
		
		if (transactionOutOfRange(now,transaction.getTimestamp())) {
			return false;
		}
		
		if(transaction.getAmount()<0){
			return false;
		}
		
		if (newBaseTimeNeeded(transaction.getTimestamp())) {
			baseTime.set(now - EXPIRE_TIME_IN_MILLIS);
		}
		
		if(addNew(transaction)==Result.ERROR){
			return false;
		}
		
		return true;
	}
	
	private boolean transactionOutOfRange(long now, long trTime) {
		return now - trTime > EXPIRE_TIME_IN_MILLIS || now < trTime;
	}
	
	private boolean newBaseTimeNeeded(long newTrxTime) {
		return baseTime.get() == 0 || newTrxTime - baseTime.get() > EXPIRE_TIME_IN_MILLIS;
	}
	
	private synchronized Result addNew(TransactionDTO trx) {
		StatOfSecond newStat = StatOfSecond.byTransaction(trx);
		
		int slotForNewStat = newStat.calculateSlot(baseTime.get());
		
		if (mergeNeeded(slotForNewStat)) {
			stats.get(slotForNewStat).merge(newStat);
			return Result.MERGED;
		}
		
		if (moveNeeded(slotForNewStat)) {
			moveCollisions(slotForNewStat, newStat);
			return Result.SHIFTED;
		}
		
		if (okToInsert(slotForNewStat)) {
			stats.set(slotForNewStat, newStat);
			return Result.INSERTED;
		}
		return Result.ERROR;
	}
	
	private boolean mergeNeeded(int slotForNewStat) {
		return stats.get(slotForNewStat)!=null && stats.get(slotForNewStat).slotNotChanged(slotForNewStat, baseTime.get());
	}
	
	private boolean moveNeeded(int slot) {
		boolean moveNeeded = false;
		
		if (stats.get(slot)!=null) {
			int newSlot = stats.get(slot).calculateSlot(baseTime.get());
			moveNeeded = newSlot!=slot && newSlot >= 0; 
		}
		
		return moveNeeded;
	}
	
	private boolean okToInsert(int slot) {
		return stats.get(slot)==null || stats.get(slot).calculateSlot(baseTime.get()) < 0;
	}
	
	private synchronized void moveCollisions(int slot, StatOfSecond newStat) {
		StatOfSecond stat = stats.get(slot);
		
		if (stat!=null && stat.slotNotChanged(slot, baseTime.get())) {
			stat.merge(newStat);
		}else
			
		if (stat!=null && stat.calculatedSlotIntheRange(baseTime.get())) {
			moveCollisions(stat.calculateSlot(baseTime.get()), stat);
			stats.set(slot, newStat);
		}
		
		else{
			stats.set(slot, newStat);
		} 
	}
	
	public StatisticsResponseDTO calculate() {
		if (baseTime.get()==0) {
			return new StatisticsResponseDTO();
		}
		
		double min = Double.MAX_VALUE;
		double max = Double.MIN_VALUE;
		double avg = 0;
		long cnt = 0;
		double sum = 0;
		
		long now = System.currentTimeMillis();
		boolean statWillBeProgressed = false;
		
		StatOfSecond stat = null;
		
		for( int i=1; i<60 ; i++ ) { // <--- O(1)
			stat = stats.get(i);
			
			statWillBeProgressed = stat!=null && stat.calculatedSlotIntheRange(now-EXPIRE_TIME_IN_MILLIS);
			
			if (statWillBeProgressed) {
				min = Math.min(stat.getMin(), min);
				max = Math.max(stat.getMax(), max);
				cnt += stat.getCnt();
				sum += stat.getSum();
				avg = (double)(sum/cnt);
			}
		}
		
		if (cnt==0) {
			return new StatisticsResponseDTO();
		}
		
		return new StatisticsResponseDTO(sum,avg,max,min,cnt);
	}
}
