package br.com.zupacademy.witer.proposta.config.seguranca;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

public class ConfiguracaoDeSeguranca extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .antMatchers(HttpMethod.GET, "/propostas/**").hasAuthority("SCOPE_propostas:read")
                                .antMatchers(HttpMethod.GET, "/cartoes/**").hasAuthority("SCOPE_cartoes:read")
                                .antMatchers(HttpMethod.POST, "/cartoes/**").hasAuthority("SCOPE_cartoes:write")
                                .antMatchers(HttpMethod.POST, "/propostas/**").hasAuthority("SCOPE_propostas:write")
                                .anyRequest().authenticated()
                )
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
    }

}