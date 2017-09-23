package statistic.dto;

/**
 * Created by serkanersoy1 on 21/09/17.
 */
public class StatisticsResponseDTO {
    private double sum = 0;
    private double avg = 0;
    private double max = 0;
    private double min = 0;
    private long count = 0;

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

	public StatisticsResponseDTO(double sum, double avg, double max,
			double min, long count) {
		this.sum = sum;
		this.avg = avg;
		this.max = max;
		this.min = min;
		this.count = count;
	}
    
    
}
