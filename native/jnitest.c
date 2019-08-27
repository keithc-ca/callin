#include <jni.h>

/*
 * Class:     callin_JNITest
 * Method:    callNonvirtualImpl
 * Signature: (Ljava/lang/Object;Ljava/lang/Class;Ljava/lang/reflect/Method;)Ljava/lang/Object;
 */
JNIEXPORT jobject JNICALL Java_callin_JNITest_callNonvirtualImpl
  (JNIEnv * env, jclass testClazz, jobject receiver, jclass clazz, jobject method)
{
	jmethodID methodID = method ? (*env)->FromReflectedMethod(env, method) : NULL;
	jobject result = (*env)->CallNonvirtualObjectMethod(env, receiver, clazz, methodID);
	jobject exception = (*env)->ExceptionOccurred(env);

	if (NULL != exception) {
		result = exception;
		(*env)->ExceptionClear(env);
	}

	return result;
}

/*
 * Class:     callin_JNITest
 * Method:    callStaticImpl
 * Signature: (Ljava/lang/Class;Ljava/lang/reflect/Method;)Ljava/lang/Object;
 */
JNIEXPORT jobject JNICALL Java_callin_JNITest_callStaticImpl
  (JNIEnv * env, jclass testClazz, jclass clazz, jobject method)
{
	jmethodID methodID = method ? (*env)->FromReflectedMethod(env, method) : NULL;
	jobject result = (*env)->CallStaticObjectMethod(env, clazz, methodID);
	jobject exception = (*env)->ExceptionOccurred(env);

	if (NULL != exception) {
		result = exception;
		(*env)->ExceptionClear(env);
	}

	return result;
}

/*
 * Class:     callin_JNITest
 * Method:    callVirtualImpl
 * Signature: (Ljava/lang/Object;Ljava/lang/reflect/Method;)Ljava/lang/Object;
 */
JNIEXPORT jobject JNICALL Java_callin_JNITest_callVirtualImpl
  (JNIEnv * env, jclass testClazz, jobject receiver, jobject method)
{
	jmethodID methodID = method ? (*env)->FromReflectedMethod(env, method) : NULL;
	jobject result = (*env)->CallObjectMethod(env, receiver, methodID);
	jobject exception = (*env)->ExceptionOccurred(env);

	if (NULL != exception) {
		result = exception;
		(*env)->ExceptionClear(env);
	}

	return result;
}
