package cn.com.gome.monitor.entity;

public class TimeEntity {
	public TimeEntity(){
		startTime=System.currentTimeMillis();
	}
	
	
	private long startTime;

	
	private long endTime;
	
	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	
	
}
