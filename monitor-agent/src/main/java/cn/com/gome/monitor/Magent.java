package cn.com.gome.monitor;

import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.util.jar.JarFile;

import cn.com.gome.monitor.runtime.Transformer;

/** 
* @ClassName: Magent 
* @Description: TODO(整个监控程序的入口,代理加载到jvm中的应用) 
* @author machaohui 
* @date 2017年6月30日 上午11:04:11  
*/
public class Magent {
	public static void premain(String agentOps, Instrumentation inst) {
		inst.addTransformer(new Transformer()); 
//		try {
//			inst.appendToBootstrapClassLoaderSearch(new JarFile("E://montior-util-0.0.1.jar"));
//		} catch (IOException e) {
//			System.out.println(e);
//			e.printStackTrace();
//		}
//		
	}
}
