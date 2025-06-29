package pe.edu.upc.calfinapp.calfin_app.iam.infrastructure.authorization.sfs.configuration;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import pe.edu.upc.calfinapp.calfin_app.iam.infrastructure.authorization.sfs.pipeline.BearerAuthorizationRequestFilter;
import pe.edu.upc.calfinapp.calfin_app.iam.infrastructure.hashing.bcrypt.BCryptHashingService;
import pe.edu.upc.calfinapp.calfin_app.iam.infrastructure.security.CustomUserDetailsService;
import pe.edu.upc.calfinapp.calfin_app.iam.infrastructure.tokens.jwt.BearerTokenService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

/**
 * Web Security Configuration.
 * <p>
 * This class is responsible for configuring the web security.
 * It enables the method security and configures the security filter chain.
 * It includes the authentication manager, the authentication provider,
 * the password encoder and the authentication entry point.
 * </p>
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration {

  private final CustomUserDetailsService customUserDetailsService;
  private final BearerTokenService tokenService;
  private final BCryptHashingService hashingService;
  private final AuthenticationEntryPoint unauthorizedRequestHandler;
  private final CorsConfigurationSource corsConfigurationSource;

  @Bean
  public BearerAuthorizationRequestFilter authorizationRequestFilter() {
    return new BearerAuthorizationRequestFilter(tokenService, customUserDetailsService);
  }

  @Bean
  public AuthenticationManager authenticationManager(
          AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    var authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(customUserDetailsService);
    authenticationProvider.setPasswordEncoder(hashingService);
    return authenticationProvider;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return hashingService;
  }

  @Bean
  public CorsFilter corsFilter() {
    return new CorsFilter(corsConfigurationSource);
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource));
    http.csrf(csrfConfigurer -> csrfConfigurer.disable())
            .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(unauthorizedRequestHandler))
            .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(
                    authorizeRequests -> authorizeRequests.requestMatchers(
                                    "/api/v1/authentication/**", "/v3/api-docs/**", "/swagger-ui.html",
                                    "/swagger-ui/**", "/swagger-resources/**", "/webjars/**", "/api/v1/profiles/**",
                                    "/api/v1/publications/**")
                            .permitAll()
                            .anyRequest()
                            .authenticated());
    http.authenticationProvider(authenticationProvider());
    http.addFilterBefore(authorizationRequestFilter(), UsernamePasswordAuthenticationFilter.class);
    http.addFilterBefore(corsFilter(), BearerAuthorizationRequestFilter.class);
    return http.build();
  }

  public WebSecurityConfiguration(
          CustomUserDetailsService customUserDetailsService,
          BearerTokenService tokenService, BCryptHashingService hashingService,
          AuthenticationEntryPoint authenticationEntryPoint,
          CorsConfigurationSource corsConfigurationSource) {

    this.customUserDetailsService = customUserDetailsService;
    this.tokenService = tokenService;
    this.hashingService = hashingService;
    this.unauthorizedRequestHandler = authenticationEntryPoint;
    this.corsConfigurationSource = corsConfigurationSource;
  }
}