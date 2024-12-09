package com.bptn.feedAppAutomation.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.bptn.feedAppAutomation.provider.ResourceProvider;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import java.util.Date;

@Component
public class JwtService {

	@Autowired
	ResourceProvider provider;

	public String generateJwtToken(String username, long expiration) {

		return JWT.create().withIssuer(this.provider.getJwtIssuer()).withAudience(this.provider.getJwtAudience())
				.withIssuedAt(new Date()).withSubject(username)
				.withExpiresAt(new Date(System.currentTimeMillis() + expiration))
				.sign(HMAC512(this.provider.getJwtSecret()));
	}

	public String getSubject(String token) {

		return JWT.require(HMAC512(this.provider.getJwtSecret())).withIssuer(this.provider.getJwtIssuer()).build()
				.verify(token).getSubject();
	}

}
