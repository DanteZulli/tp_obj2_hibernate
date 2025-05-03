package com.grupo25.tp_obj2_hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TpObj2HibernateApplication {

	public static void main(String[] args) {
		SpringApplication.run(TpObj2HibernateApplication.class, args);
	}

	@Bean
	public SessionFactory sessionFactory() {
		try {
			return new Configuration().configure().buildSessionFactory();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
