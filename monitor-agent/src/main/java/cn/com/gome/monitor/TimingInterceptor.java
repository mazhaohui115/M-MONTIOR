package cn.com.gome.monitor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;








import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

public class TimingInterceptor {
	@RuntimeType
	public static Object intercept(@Origin Method method, @SuperCall Callable<?> callable) {
	
		long start = System.currentTimeMillis();
		try {
			return callable.call();
		} catch (Exception e) {
			//后续考虑将堆栈信息推送走
			System.out.println("系统发生异常了："+getStackTrace(e));
		}  finally {
			System.out.println(Thread.currentThread().getId()+":"+method + " 执行耗时 " + (System.currentTimeMillis() - start)+"ms");
//			logger.info(method + " took " + (System.currentTimeMillis() - start));
		}
		return null;
	}
	
	private static String getStackTrace(Throwable t) {
	    StringWriter sw = new StringWriter();
	    PrintWriter pw = new PrintWriter(sw);
	    try {
	        t.printStackTrace(pw);
	        return sw.toString();
	    } finally {
	        pw.close();
	    }
	}
}
