package com.sap.cloud.security.xsuaa.extractor;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.sap.cloud.security.xsuaa.token.Token;
import com.sap.cloud.security.xsuaa.token.TokenClaims;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

public class DefaultAuthoritiesExtractor extends JwtAuthenticationConverter implements AuthoritiesExtractor {

	public Collection<GrantedAuthority> getAuthorities(Jwt jwt) {
		return extractAuthorities(jwt);
	}

	@Override
	protected Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
		List<String> scopes = jwt.getClaimAsStringList(TokenClaims.CLAIM_SCOPES);

		if (scopes == null) {
			return Collections.emptyList();
		}

		return scopes.stream()
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
	}

}
