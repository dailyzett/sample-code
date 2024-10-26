package hello.aop.pointcut;

import static org.assertj.core.api.Assertions.assertThat;

import hello.aop.member.MemberServiceImpl;
import java.lang.reflect.Method;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

public class ArgsTest {

	Method helloMethod;

	@BeforeEach
	void init() throws NoSuchMethodException {
		helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
	}

	private AspectJExpressionPointcut pointcut(String expression) {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression(expression);
		return pointcut;
	}

	@Test
	void args() {
		assertThat(pointcut("args(String)").matches(helloMethod, MemberServiceImpl.class)).isTrue();
		assertThat(pointcut("args(Object)").matches(helloMethod, MemberServiceImpl.class)).isTrue();
		assertThat(pointcut("args()").matches(helloMethod, MemberServiceImpl.class)).isFalse();
		assertThat(pointcut("args(..)").matches(helloMethod, MemberServiceImpl.class)).isTrue();
		assertThat(pointcut("args(*)").matches(helloMethod, MemberServiceImpl.class)).isTrue();
		assertThat(pointcut("args(String, ..)").matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}
}
