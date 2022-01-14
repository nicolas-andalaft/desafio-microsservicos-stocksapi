package com.nicolas.stocksapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class StocksapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(StocksapiApplication.class, args);
	}

	@RestController
    static class SimpleRestController {
        @GetMapping("/")
        String sayHello(@AuthenticationPrincipal OidcUser oidcUser) {
            return "Hello: " + oidcUser.getFullName();
        }

        @GetMapping("/teste")
        String teste(@AuthenticationPrincipal OidcUser oidcUser) {
            return "Hello: " + oidcUser.getBirthdate();
        }
    }

}
