package com.begin.java;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import strings.JoinKtKt;

@Slf4j
public class JavaTest {

	@Test
	void test() {
		log.info("text={}", JoinKtKt.lastChar("Java"));
	}
}
