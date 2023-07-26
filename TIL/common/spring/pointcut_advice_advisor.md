# 목차

- [목차](#목차)
- [1. 포인트컷, 어드바이스, 어드바이저 - 소개](#1-포인트컷-어드바이스-어드바이저---소개)
- [2. 포인트 컷](#2-포인트-컷)
	- [2.1 여러개의 어드바이저](#21-여러개의-어드바이저)

# 1. 포인트컷, 어드바이스, 어드바이저 - 소개

- **포인트컷**: 어떤 **포인트**에 기능을 적용할지 하지 않을지 **잘라서(cut)** 구분하는 것이다.
어디에 부가 기능을 적용할지, 어디에 부가 기능을 적용하지 않을지 판단하는 필터링 로직이다.


- **어드바이스**: **프록시가 호출하는 부가 기능**이다. 단순하게 말하면, 프록시 로직이다.


- **어드바이저**: 포인트컷 하나 + 어드바이스 하나를 가지고 있는 것이다.

# 2. 포인트 컷

```java
public interface Pointcut {

	/**
	 * Return the ClassFilter for this pointcut.
	 * @return the ClassFilter (never {@code null})
	 */
	ClassFilter getClassFilter();

	/**
	 * Return the MethodMatcher for this pointcut.
	 * @return the MethodMatcher (never {@code null})
	 */
	MethodMatcher getMethodMatcher();


	/**
	 * Canonical Pointcut instance that always matches.
	 */
	Pointcut TRUE = TruePointcut.INSTANCE;

}
```

포인트컷 인터페이스는 클래스 필터와 메서드 필터가 있다. 
둘 다 _true_ 로 반환해야 어드바이스를 적용할 수 있다.

스프링은 많은 포인트컷을 제공한다.

```java
NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
pointcut.setMappedName("save");
```

위와 같이 설정하면 메서드 이름이 _save_ 인 것만 부가 기능을 적용한다.
그 외에도 제공하는 것은 다음과 같다.

- JdkRegexMethodPointcut : JDK 정규 표현식을 기반으로 포인트컷을 매칭
- AnnotationMatchingPointcut : 애노테이션으로 매칭
- AspectJExpressionPointcut : aspectJ 표현식으로 매칭

**가장 중요한 것은 AspectJExpressionPointcut** 이다.

실무에서는 사용하기도 편리하고 기능도 가장 많은 aspectJ 표현식을 기반으로 사용하는
_AspectJExpressionPointcut_ 을 사용하게 된다.

## 2.1 여러개의 어드바이저

핵심 기능에 여러 개의 부가 기능을 적용하고 싶을 때 프록시 팩토리 빈을 이용하면 손쉽게 여러 개를 적용할 수 있다.
프록시 팩토리에 원하는 만큼 _addAdvisor()_ 를 통해서 어드바이저를 등록하면 된다.

```java
@Test
@DisplayName("하나의 프록시, 여러 어드바이저")
void multiAdvisorTest2() {
    DefaultPointcutAdvisor advisor1 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice1());
    DefaultPointcutAdvisor advisor2 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice2());

    //프록시1 생성
    ServiceInterface target = new ServiceImpl();
    ProxyFactory proxyFactory1 = new ProxyFactory(target);

    proxyFactory1.addAdvisor(advisor2);
    proxyFactory1.addAdvisor(advisor1);
    ServiceInterface proxy1 = (ServiceInterface) proxyFactory1.getProxy();

    proxy1.save();
}
```

> **중요!**  
> 스프링 AOP를 처음 공부하거나 사용하면, AOP 적용 수 만큼 프록시가 생성된다고 착각하게 된다.
> 스프링은 AOP를 적용할 때, 최적화를 진행해서 지금처럼 프록시는 하나만 만들고, 하나의 프록시에 여러 어드바이저를 적용한다.
> 정리하면 하나의 _target_ 에 여러 AOP가 동시에 적용되어도 하나의 프록시만 생성된다.