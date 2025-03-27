package io.github.antoniaturcatto.docfind.config

import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.http.MediaType
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.server.authorization.InMemoryOAuth2AuthorizationConsentService
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher
import java.security.KeyPairGenerator
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.time.Duration
import java.util.*


@Configuration
@EnableWebSecurity
class AuthorizationServerConfiguration {

    @Bean
    @Order(1)
    fun authServerSecurityFilterChain(http: HttpSecurity): SecurityFilterChain{
        val oauth2ServerConfigurer = OAuth2AuthorizationServerConfigurer.authorizationServer()

        return http
            .oauth2ResourceServer{it.jwt(Customizer.withDefaults())}
            .securityMatcher(oauth2ServerConfigurer.endpointsMatcher)
            .formLogin(Customizer.withDefaults())
            .with(oauth2ServerConfigurer) { authorizationServer ->
                authorizationServer.oidc(Customizer.withDefaults()) // OpenID Connect 1.0
            }
            .authorizeHttpRequests { it.anyRequest().authenticated() }
            .exceptionHandling{ exceptions ->
                exceptions
                    .defaultAuthenticationEntryPointFor(
                        LoginUrlAuthenticationEntryPoint ("/login"),
                        MediaTypeRequestMatcher (MediaType.TEXT_HTML)
                    )
            }
            .build()
    }



//it will be done another day
//    @Bean
//    fun authorizationConsentService(jdbcTemplate: JdbcTemplate, cRegisteredClientsRepository: CustomRegisteredClientsRepository): OAuth2AuthorizationConsentService {
//        return JdbcOAuth2AuthorizationConsentService(jdbcTemplate, cRegisteredClientsRepository)
//    }

    @Bean
    fun authorizationConsentService(): OAuth2AuthorizationConsentService {
        return InMemoryOAuth2AuthorizationConsentService()
    }

    @Bean
    fun tokenSettings():TokenSettings{
        return TokenSettings.builder()
            .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
            .accessTokenTimeToLive(Duration.ofMinutes(60))
            .refreshTokenTimeToLive(Duration.ofMinutes(90))
            .build()
    }

    @Bean
    fun clientSettings():ClientSettings{
        return ClientSettings.builder()
            .requireAuthorizationConsent(false)
            .build()
    }

    @Bean
    fun jwkSource(): JWKSource<SecurityContext>{
        val rsaKey = generateRSAKey()
        val jwkSet = JWKSet(rsaKey)
        return ImmutableJWKSet(jwkSet)
    }

    private fun generateRSAKey(): RSAKey{
        val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
        keyPairGenerator.initialize(2048)
        val keyPair = keyPairGenerator.generateKeyPair()
        val publicKey: RSAPublicKey = keyPair.public as RSAPublicKey
        val privateKey: RSAPrivateKey = keyPair.private as RSAPrivateKey
            return RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build()
    }

    @Bean
    fun jwtDecoder(jwkSource: JWKSource<SecurityContext?>?): JwtDecoder {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource)
    }
}