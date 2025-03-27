package io.github.antoniaturcatto.docfind.common.security

import io.github.antoniaturcatto.docfind.common.model.Client
import io.github.antoniaturcatto.docfind.service.ClientService
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings
import org.springframework.stereotype.Component
import java.util.*

@Component
class CustomRegisteredClientsRepository(private val clientService: ClientService,
    private val clientSettings: ClientSettings,
    private val tokenSettings: TokenSettings) : RegisteredClientRepository{

    override fun save(registeredClient: RegisteredClient?) {

    }

    override fun findById(id: String?): RegisteredClient? {
        id ?: return null
        clientService.findById(UUID.fromString(id))?.let {
            return makeClientIntoRegisteredClient(it)
        }
        return null
    }

    override fun findByClientId(clientId: String?): RegisteredClient? {
        clientId ?: return null
        clientService.findByClientId(clientId)?.let {
            return makeClientIntoRegisteredClient(it)
        }
        return null
    }

    private fun makeClientIntoRegisteredClient(client: Client): RegisteredClient{
        return RegisteredClient
            .withId(client.id.toString())
            .clientId(client.clientId)
            .clientSecret(client.clientSecret)
            .redirectUri(client.redirectUri)
            .scope(client.scope)
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
            .clientSettings(clientSettings)
            .tokenSettings(tokenSettings)
            .build()
    }
}