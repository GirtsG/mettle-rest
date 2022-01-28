package com.accenture.mettle;

import com.accenture.mettle.controller.FeatureController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MettleApplicationTests {

	@Autowired
	private FeatureController featureController;

	@Test
	void contextLoads() {
		assertThat(featureController).isNotNull();
	}

}
