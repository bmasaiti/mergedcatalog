package com.au.mergedcatalog;

import com.au.mergedcatalog.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.annotation.PostConstruct;
import javax.naming.Context;

@SpringBootApplication
public class MergedcatalogApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(MergedcatalogApplication.class, args);
		CatalogService catalogService = ctx.getBean(CatalogService.class);
		catalogService.buildFinalCatalog();

	}

}
