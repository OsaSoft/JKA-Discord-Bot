package cloud.osasoft.jkaDiscordBot.service

import io.micronaut.context.ApplicationContext
import io.micronaut.runtime.server.EmbeddedServer
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class DiscordServiceTest extends Specification {

	@Shared
	@AutoCleanup
	EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer)

	@Shared
	def discordService = embeddedServer.applicationContext.getBean(DiscordService)

	void setup() {
	}

	void cleanup() {
	}

	def "Sends a message to Discord"() {
		expect:
			discordService.sendMessage("test")
	}
}
