package cn.com.gome.monitor.runtime;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/** 
* @ClassName: Instrumentor 
* @Description: TODO(class编辑器) 
* @author machaohui 
* @date 2017年6月30日 上午11:10:02  
*/
public class Instrumentor extends ClassVisitor {
	private boolean isInterface;

	private String className = "";

	public Instrumentor(final ClassVisitor cv) {
		super(Opcodes.ASM5, cv);
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		if (cv != null) {
			className = name;
			cv.visit(version, access, name, signature, superName, interfaces);
		}
		

		isInterface = (access & Opcodes.ACC_INTERFACE) != 0;
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
		if (!isInterface && desc != null && !name.equals("<init>") && desc.indexOf("cn/com/gome/bottom/") >= 0) {
			mv = new MethodInstrumentor(mv, name, desc, className);
		}
	
		return mv;

	}

	@Override
	public void visitEnd() {
		cv.visitEnd();
	}
}
