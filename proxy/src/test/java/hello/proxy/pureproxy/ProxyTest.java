package hello.proxy.pureproxy;

import hello.proxy.pureproxy.code.CacheProxy;
import hello.proxy.pureproxy.code.ProxyPatternClient;
import hello.proxy.pureproxy.code.RealSubject;
import org.junit.jupiter.api.Test;

public class ProxyTest {

	@Test
	void noProxyTest() {
		RealSubject realSubject = new RealSubject();
		ProxyPatternClient client = new ProxyPatternClient(realSubject);

		client.execute();
		client.execute();
		client.execute();
	}

	@Test
	void cacheProxyTest() {
		RealSubject realSubject = new RealSubject();
		CacheProxy cacheProxy = new CacheProxy(realSubject);
		ProxyPatternClient client = new ProxyPatternClient(cacheProxy);

		client.execute();
		client.execute();
		client.execute();
	}
}
