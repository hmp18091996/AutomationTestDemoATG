package retryTest;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

public class RetryListener implements IAnnotationTransformer {

	@SuppressWarnings("rawtypes")
	@Override
	public void transform(ITestAnnotation anotation, Class testClass, Constructor testConstructor, Method testMethod) {
		anotation.setRetryAnalyzer(RetryTestFailed.class);
	}

	
	
}
