package cn.com.gome.monitor;

import static net.bytebuddy.matcher.ElementMatchers.isInterface;
import static net.bytebuddy.matcher.ElementMatchers.not;

import java.lang.instrument.Instrumentation;

import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType.Builder;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;








/** 
* @ClassName: Magent 
* @Description: TODO(整个监控程序的入口,代理加载到jvm中的应用) 
* @author machaohui 
* @date 2017年6月30日 上午11:04:11  
*/
public class Magent {
	//开发监控测试AgentBuilder.Listener.StreamWriting.toSystemOut()
	public static void premain(String agentOps, Instrumentation inst) {
		
	    new AgentBuilder.Default()
	    .type(ElementMatchers.nameContains("gome")).or(ElementMatchers.nameContains("gomeplus")).or(ElementMatchers.nameContains("rpc")).and(not(isInterface())).and(not(ElementMatchers.nameContains("common")))
	    .and(not(ElementMatchers.nameContains("monitor")))
	    .transform(new AgentBuilder.Transformer() {
			public Builder<?> transform(Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader,
					JavaModule module) {
				  return builder.method((not(ElementMatchers.nameStartsWith("set"))))
                          .intercept(MethodDelegation.to(TimingInterceptor.class));
				
			}
		})//.with(AgentBuilder.Listener.StreamWriting.toSystemOut())
	    .installOn(inst);
	
//		AgentBuilder build= new AgentBuilder.Default();
//		build.type(not(isInterface())).transform(new AgentBuilder.Transformer() {
//			
//			public Builder<?> transform(Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader,
//					JavaModule module) {
//				builder.method((ElementMatchers.any())).intercept(MethodDelegation.to(TimingInterceptor.class)).field(named("intercept"));
//			
//				return builder;
//			}
//		});
//
//		build.with(new AgentBuilder.Listener() {
//			
//			public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module,
//					boolean loaded, DynamicType dynamicType) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, boolean loaded) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			public void onError(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded, Throwable throwable) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			public void onDiscovery(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			public void onComplete(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
//
//	  
//		build.installOn(inst);
		
		
//		new AgentBuilder.Default()
//	      .type(ElementMatchers.nameEndsWith("Timed"))
//	      .transform((builder, type, classLoader, module);
//	          builder.method(ElementMatchers.any())
//	                 .intercept(MethodDelegation.to(TimingInterceptor.class))
//	      ).installOn(instrumentation);
		
//	 
		
		
//		  AgentBuilder agentBuilder = (AgentBuilder) new AgentBuilder.Default()
//		  .type(nameStartsWith("cn.com.gome").and(not(isInterface()).and(not(isStatic())))).transform(builder)
//		inst.addTransformer(new Transformer()); 

//		
	}
	
	public static void main(String[] args) {
	   premain("", ByteBuddyAgent.install());

//		Foo f=new Foo();
//		System.out.println(f.toString());

	}

}
