package com.auth.core.config;

import com.auth.core.handler.HttpLogoutHandler;
import com.auth.core.jwt.JwtHelper;
import com.auth.core.jwt.util.JwtUtil;
import com.auth.core.security.JwtAuthenticationFilter;
import com.auth.core.security.JwtAuthorizationFilter;
import com.auth.feature.token.service.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.inject.Inject;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Inject
    UserDetailsService userDetailsService;

    @Inject
    BCryptPasswordEncoder passwordEncoder;

    @Inject
    TokenService tokenService;

    @Inject
    JwtHelper jwtHelper;

    @Inject
    JwtUtil jwtUtil;

    @Inject
    HttpLogoutHandler httpLogoutHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.POST, "/users/register").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessHandler(httpLogoutHandler)
                .and()
                // Authentication filter, this will intercept request path for login ("/login").
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), tokenService, jwtHelper, jwtUtil))
                // Authorization filter to check jwt validity.
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), userDetailsService, tokenService, jwtHelper, jwtUtil))
                // This disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    /**
     * Configure websecurity to ignore some request on filter chain in {@link JwtAuthorizationFilter}
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) {
        web
                .ignoring()
                .antMatchers("/h2/**")
                .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security",
                        "/swagger-ui.html", "/webjars/**", "/swagger-resources/configuration/ui", "/swagger-ui.html",
                        "/swagger-resources/configuration/security");
    }

    /**
     * Use the {@link UserDetailsService} to verify user.
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    public CorsConfigurationSource configurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Permit dto from different origin.
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}
