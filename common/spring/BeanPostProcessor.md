# 목차

# 1. 빈 후처리기 - 소개

_@Bean_ 이나 컴포넌트 스캔으로 스프링 빈을 등록하면, 스프링은 대상 오브젝트를 생성하고
스프링 컨테이너나 빈 저장소에 등록한다. 그리고 이후에는 스프링 컨테이너를 통해 등록한 스프링 빈을
조회해서 사용하면 된다.

스프링이 빈 저장소에 등록할 목적으로 생성한 오브젝트를 **빈 저장소에 등록하기 직전에
조작**하고 싶다면 빈 후처리기(BeanPostProcessor)를 사용하면 된다.

- 빈 후처리기의 기능

1. 오브젝트를 조작할 수 있다.
2. 완전히 다른 오브젝트로 바꿔치기 하는 것도 가능하다.

![img.png](beanpostprocessorimage1.png)

1. **생성**: 스프링 빈 대상이 되는 오브젝트를 생성한다.
2. **전달**: 생성된 오브젝트를 빈 저장소에 등록하기 직전에 빈 후처리기에 전달한다.
3. **후 처리 작업**: 빈 후처리기는 전달된 빈 오브젝트를 조작하거나 다른 오브젝트로 대체할 수 있다.
4. **등록**: 빈 후처리기는 빈을 반환한다. 전달된 빈을 그대로 반환하면 해당 빈이 등록되고, 바꿔치기하면
   다른 오브젝트가 빈 저장소에 등록된다.

# 2. 빈 후처리기 - 예제

```java
@Slf4j
public class BasicTest {

	@Test
	void basicConfig() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
			BasicConfig.class);

		//B는 빈으로 등록
		B b = applicationContext.getBean("beanA", B.class);
		b.helloB();

		//A는 빈으로 등록 X
		assertThatThrownBy(() -> {
			applicationContext.getBean(A.class);
		}).isInstanceOf(NoSuchBeanDefinitionException.class);
	}

	@Slf4j
	@Configuration
	static class BasicConfig {

		@Bean(name = "beanA")
		public A a() {
			return new A();
		}

		@Bean
		public AToBPostProcessor helloPostProcessor() {
			return new AToBPostProcessor();
		}
	}

	@Slf4j
	static class A {

		public void helloA() {
			log.info("hello A");
		}
	}

	@Slf4j
	static class B {

		public void helloB() {
			log.info("hello B");
		}
	}

	@Slf4j
	static class AToBPostProcessor implements BeanPostProcessor {

		@Override
		public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
			log.info("beanName={} bean={}", beanName, bean);
			if(bean instanceof A) {
				return new B();
			}
			return bean;
		}
	}
}
```

실행 결과를 보면 _beanA_ 라는 스프링 빈 이름에 A 오브젝트 대신 B 오브젝트가 등록된다.

**정리**

- 빈 후처리기는 빈을 조작하고 변경할 수 있는 후킹 포인트이다.
- 빈 오브젝트를 조작하거나 심지어 다른 오브젝트로 바꾸어 버릴 수 있을 정도로 막강하다.

> **참고.**
> _@PostConstruct_ 는 스프링 빈 생성 이후에 빈을 초기화하는 역할을 한다.
> 빈의 초기화라는 것은 단순히 _@PostConstruct_ 애노테이션이 붙은 초기화 메서드를 한 번 호출만 하면 된다.
> 즉, 이것도 생성된 빈을 한 번 조작하는 것이다.
> 스프링은 *CommonAnnotationBeanPostProcessor* 라는 빈 후처리기를 자동으로 등록하는데,
> 여기에서 *@PostConstruct* 어노테이션이 붙은 메서드를 호출한다. 따라서 스프링 스스로도 스프링 내부의 기능을
> 확장하기 위해 빈 후처리기를 사용한다.

![image.png](./assets/image.png)
