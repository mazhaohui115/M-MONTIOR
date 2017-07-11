package cn.com.gome.monitor.util;

import cn.com.gome.monitor.entity.TimeEntity;

/** 
* @ClassName: TimeUtil 
* @Description: TODO(系统操作时间记录处理工具类) 
* @author machaohui 
* @date 2017年6月30日 上午11:10:31  
*/
public class TimeUtil {
	private static ThreadLocal<TimeEntity> threadLocal =new ThreadLocal<TimeEntity>();
	
	private TimeUtil() {
	}


	public static void setStartTime() {
		threadLocal.set(new TimeEntity());
	}



	public static void setEndTime() {
		if(threadLocal.get() !=null){
			threadLocal.get().setEndTime(System.currentTimeMillis());
		}
	

	}

	public static long getExclusiveTime(String className, String methodName, String methodDesc) {
		if(threadLocal.get()==null){
			return 0;

		}
//		
		StackTraceElement[] trace = Thread.currentThread().getStackTrace();
		  StackTraceElement tmp = trace[2];
		long exclusive = threadLocal.get().getEndTime() - threadLocal.get().getStartTime();
		threadLocal.remove();
		System.out.println(tmp.getLineNumber()+":"+className.replace("/", ".") + "." + methodName + " exclusive:" + exclusive + "ms");
		return exclusive;
	}
}
