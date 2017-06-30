package cn.com.gome.monitor.runtime;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/** 
* @ClassName: MethodInstrumentor 
* @Description: TODO(方法拦截入口) 
* @author machaohui 
* @date 2017年6月30日 上午11:09:37  
*/
public class MethodInstrumentor  extends MethodVisitor{
	private String methodName;

	private String desc;

	private String className;

	public MethodInstrumentor(MethodVisitor mv, String methodName, String desc, String className) {
		super(Opcodes.ASM5, mv);
		this.methodName = methodName;
		this.desc = desc;
		this.className = className;

	}
	
	
	
	@Override
	public void visitCode() {		
		mv.visitLdcInsn(className+methodName+desc);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "cn/com/gome/monitor/util/TimeUtil", "setStartTime", "(Ljava/lang/String;)V", false);
		
		
		super.visitCode();
	}

	@Override
	public void visitInsn(int opcode) {
		if ((opcode >= Opcodes.IRETURN && opcode <= Opcodes.RETURN) || opcode == Opcodes.ATHROW) {			
			mv.visitLdcInsn(className+methodName+desc);
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, "cn/com/gome/monitor/util/TimeUtil", "setEndTime", "(Ljava/lang/String;)V", false);
			
			
			mv.visitLdcInsn(className);
			mv.visitLdcInsn(methodName);	
			mv.visitLdcInsn(desc);	
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, "cn/com/gome/monitor/util/TimeUtil", "getExclusiveTime", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)J", false);
			mv.visitInsn(Opcodes.POP2);
		}

		super.visitInsn(opcode);
	}

	@Override
	public void visitEnd() {
		super.visitEnd();
	}
}
