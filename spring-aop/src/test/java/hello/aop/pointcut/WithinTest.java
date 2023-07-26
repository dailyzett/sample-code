package hello.aop.pointcut;

import static org.assertj.core.api.Assertions.assertThat;

import hello.aop.member.MemberServiceImpl;
import java.lang.reflect.Method;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

public class WithinTest {

	AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
	Method helloMethod;

	@BeforeEach
	void init() throws NoSuchMethodException {
		helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
	}

	@Test
	void withinTest() {
		pointcut.setExpression("within(hello.member.MemberServiceImpl)");
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void withinStar() {
		pointcut.setExpression("within(hello.member.*Service*)");
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void withinSubPackage() {
		pointcut.setExpression("within(hello.member..*)");
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}
}
