package cloud.osasoft.jkaDiscordBot.dto.discord

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.CompileStatic
import groovy.transform.ToString

/**
 * Info taken from <a href="https://birdie0.github.io/discord-webhooks-guide/discord_webhook.html">https://birdie0.github.io/discord-webhooks-guide/discord_webhook.html</a>
 */
@CompileStatic
@ToString
class DiscordMessage {

	/**
	 * overrides the current username of the webhook
	 */
	String username

	/**
	 * if used, it overrides the default avatar of the webhook
	 */
	@JsonProperty("avatar_url")
	String avatarUrl

	/**
	 * simple message, the message contains (up to 2000 characters)
	 */
	String content

	/**
	 * array of embed objects. That means, you can use more than one in the same body
	 */
	List<DiscordEmbed> embeds = []
}
