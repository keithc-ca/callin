package callin;

import java.lang.reflect.Method;

public class JNITest {

	static final class JNITestEx extends JNITest {

		@Override
		Object instanceMethod() {
			System.out.println("    Ex.instanceMethod()");
			return "JNITestEx.instanceMethod";
		}

	}

	static {
		System.loadLibrary("test");
	}

	private static void callNonvirtual(Object receiver, Class<?> clazz, Method method) {
		System.out.printf("  testNonvirtual(%s, %s, %s)%n", receiver, clazz, method);
		Object object = callNonvirtualImpl(receiver, clazz, method);
		System.out.printf("    -> %s%n", object);
	}

	private native static Object callNonvirtualImpl(Object receiver, Class<?> clazz, Method method);

	private static void callStatic(Class<?> clazz, Method method) {
		System.out.printf("  testStatic(%s, %s)%n", clazz, method);
		Object object = callStaticImpl(clazz, method);
		System.out.printf("    -> %s%n", object);
	}

	private native static Object callStaticImpl(Class<?> clazz, Method method);

	private static void callVirtual(Object receiver, Method method) {
		System.out.printf("  testVirtual(%s, %s)%n", receiver, method);
		Object object = callVirtualImpl(receiver, method);
		System.out.printf("    -> %s%n", object);
	}

	private native static Object callVirtualImpl(Object receiver, Method method);

	public static void main(String[] args) throws Exception {
		Class<?> clazz = JNITest.class;
		Method staticMethod = clazz.getDeclaredMethod("staticMethod");
		Method instanceMethod = clazz.getDeclaredMethod("instanceMethod");

		Object receiver = new JNITest();
		Object receiverEx = new JNITestEx();

		// test normal uses
		System.out.println("Begin normal use-cases");

		callStatic(clazz, staticMethod);
		callStatic(null, staticMethod);

		callNonvirtual(receiver, clazz, staticMethod);
		callNonvirtual(receiver, null, staticMethod);
		callNonvirtual(receiver, clazz, instanceMethod);
		callNonvirtual(receiver, null, instanceMethod);
		callNonvirtual(receiverEx, clazz, instanceMethod);
		callNonvirtual(receiverEx, null, instanceMethod); // openj9 treats this like callVirtual()

		callVirtual(receiver, instanceMethod);
		callVirtual(receiverEx, instanceMethod);

		// test abuses
		System.out.println();
		System.out.println("Begin improper use-cases");

		callNonvirtual(null, clazz, instanceMethod); // bad: receiver needed
		callNonvirtual(null, null, instanceMethod); // bad: receiver needed

		callVirtual(null, instanceMethod); // bad: receiver needed
	}

	static Object staticMethod() {
		System.out.println("    staticMethod()");
		return "staticMethod";
	}

	Object instanceMethod() {
		System.out.println("    instanceMethod()");
		return "JNITest.instanceMethod";
	}

}
