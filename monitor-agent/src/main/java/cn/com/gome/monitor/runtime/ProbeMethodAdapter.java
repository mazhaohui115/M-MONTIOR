package cn.com.gome.monitor.runtime;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

public class ProbeMethodAdapter  extends AdviceAdapter {
	private String methodName;

	private String desc;

	private String className;
	
	protected ProbeMethodAdapter(int api, MethodVisitor mv, int access, String methodName, String desc,String className) {
		super(api, mv, access, methodName, desc);
		this.methodName = methodName;
		this.desc = desc;
		this.className = className;
	}
	
	
	
	
	

	@Override
	protected void onMethodEnter() {
		// TODO Auto-generated method stub
		System.out.println("--");
		super.onMethodEnter();

	}






	@Override
	protected void onMethodExit(int opcode) {
		System.out.println("--");
		super.onMethodExit(opcode);
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

	
	
}
