package cn.com.gome.monitor.runtime;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

/** 
* @ClassName: Transformer 
* @Description: TODO(javaagent 类加载拦截器入口) 
* @author machaohui 
* @date 2017年6月30日 上午11:06:24  
*/
public class Transformer implements ClassFileTransformer{
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
		ClassReader classReader = new ClassReader(classfileBuffer);
		ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
		ClassVisitor cv = new Instrumentor(classWriter);
		
		classReader.accept(cv, Opcodes.ASM5);
		//将加载到jvm中的class输出到指定路径，测试使用
		try {

        	String path="f:\\daima\\"+className+".class";
//        	File f=new File(path.substring(0,path.lastIndexOf("\\")));
        	File f=new File(path);
        	f.getParentFile().mkdirs();
        	
        
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(classWriter.toByteArray());  
		    fos.close();  
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  

		return classWriter.toByteArray();

	}
}
