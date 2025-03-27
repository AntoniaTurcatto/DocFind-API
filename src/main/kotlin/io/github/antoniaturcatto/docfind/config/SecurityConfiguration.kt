package io.github.antoniaturcatto.docfind.config

import io.github.antoniaturcatto.docfind.common.security.SocialLoginSuccessHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.core.GrantedAuthorityDefaults
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
class SecurityConfiguration {

    @Bean
    fun securityFilterChain(httpSecurity: HttpSecurity, socialLoginSuccessHandler: SocialLoginSuccessHandler):SecurityFilterChain{
        return httpSecurity
            .csrf{it.disable()}
            .formLogin(Customizer.withDefaults())
            .httpBasic(Customizer.withDefaults())
            .authorizeHttpRequests {
                it.requestMatchers("/login/**").permitAll() // login
                it.requestMatchers("/users").permitAll() // save
                it.anyRequest().authenticated()
            }
            .oauth2Login {
                it.successHandler(socialLoginSuccessHandler)

            }
            //.oauth2Login(Customizer.withDefaults())
            .oauth2ResourceServer{it.jwt(Customizer.withDefaults())}//using JWT to validate user
            .build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder{
        return BCryptPasswordEncoder(10)
    }

    @Bean
    fun grantedAuthorityDefaults(): GrantedAuthorityDefaults {
        return GrantedAuthorityDefaults("") //qual prefixo queremos
    }

    //removing SCOPE_ prefix
    @Bean
    fun jwtAuthenticationConverter(): JwtAuthenticationConverter {
        val authoritiesConverter = JwtGrantedAuthoritiesConverter()
        authoritiesConverter.setAuthorityPrefix("")
        val converter = JwtAuthenticationConverter()
        converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter)
        return converter
    }
}