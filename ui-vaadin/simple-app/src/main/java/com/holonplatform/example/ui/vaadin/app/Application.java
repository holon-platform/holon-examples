package com.holonplatform.example.ui.vaadin.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;

@SpringBootApplication
public class Application {

	// The Vaadin UI
	@SpringUI
	@SpringViewDisplay
	@Theme("valo")
	public static class UI extends com.vaadin.ui.UI {

		private static final long serialVersionUID = 1L;

		@Override
		protected void init(VaadinRequest request) {
		}

	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

}
