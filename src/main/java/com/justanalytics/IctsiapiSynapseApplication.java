package com.justanalytics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class IctsiapiSynapseApplication {

	public static void main(String[] args) {
		SpringApplication.run(IctsiapiSynapseApplication.class, args);
	}

}
