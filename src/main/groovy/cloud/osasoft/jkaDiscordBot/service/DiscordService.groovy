package cloud.osasoft.jkaDiscordBot.service

import cloud.osasoft.jkaDiscordBot.dto.discord.DiscordMessage
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.micronaut.context.annotation.Value
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client

import javax.inject.Inject
import javax.inject.Singleton

import static io.micronaut.http.HttpRequest.POST

@Singleton
@Slf4j
@CompileStatic
class DiscordService {

	@Value("\${discord.webhook}")
	private String webhook

	@Client("https://discordapp.com/api/webhooks/")
	@Inject
	private RxHttpClient client

	void sendMessage(DiscordMessage message) {
		if (!message) return

		try {
			log.debug "Sending message to $webhook"
			client.exchange(POST(webhook, message), String).blockingFirst()
		} catch (Exception ex) {
			log.error "Error POSTing to Discord", ex
			log.debug "Payload was: $message"
		}
	}
}
