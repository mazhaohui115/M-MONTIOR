package cn.com.gome.test;

public class T {
	public static void main(String[] args) throws Exception {
		try {
			String a=null;
			a.toString();
		} catch (Exception e) {
			throw e;
		}

	}
}
