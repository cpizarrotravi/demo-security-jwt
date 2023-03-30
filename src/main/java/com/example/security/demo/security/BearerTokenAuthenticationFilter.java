package com.example.security.demo.security;

import com.example.security.demo.exception.BearerTokenException;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BearerTokenAuthenticationFilter extends OncePerRequestFilter {

  private static final Pattern authorizationPattern = Pattern.compile("^Bearer (?<token>[a-zA-Z0-9-._~+/]+=*)$", Pattern.CASE_INSENSITIVE);
  private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();
  private BearerTokenProvider bearerTokenProvider;
  private UserDetailsService userDetailsService;

  public BearerTokenAuthenticationFilter(BearerTokenProvider bearerTokenProvider, UserDetailsService userDetailsService) {
    this.bearerTokenProvider = bearerTokenProvider;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException,
      IOException {
    Optional<String> token;
    try {
      token = getTokenFromHeader(request);
    } catch (AuthenticationException invalid) {
      this.logger.trace("Sending to authentication entry point since failed to resolve bearer token", invalid);
      return;
    }
    if (!token.isPresent()) {
      this.logger.trace("Did not process request since did not find bearer token");
      filterChain.doFilter(request, response);
      return;
    }
    String username = bearerTokenProvider.getUsernameFromJWT(token.get());
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

    UsernamePasswordAuthenticationToken authenticationRequest = new UsernamePasswordAuthenticationToken(userDetails, null, AuthorityUtils.NO_AUTHORITIES);
    authenticationRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    try {
      SecurityContext context = SecurityContextHolder.createEmptyContext();
      context.setAuthentication(authenticationRequest);
      SecurityContextHolder.setContext(context);
      filterChain.doFilter(request, response);
    } catch (AuthenticationException failed) {
      SecurityContextHolder.clearContext();
      this.logger.trace("Failed to process authentication request", failed);
    }
  }


  public Optional<String> getTokenFromHeader(final HttpServletRequest request) {
    String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (!StringUtils.startsWithIgnoreCase(authorization, "bearer")) {
      return Optional.empty();
    }
    Matcher matcher = authorizationPattern.matcher(authorization);
    if (!matcher.matches()) {
      throw new BearerTokenException("Bearer token is malformed");
    }
    return Optional.ofNullable(matcher.group("token"));
  }

}
