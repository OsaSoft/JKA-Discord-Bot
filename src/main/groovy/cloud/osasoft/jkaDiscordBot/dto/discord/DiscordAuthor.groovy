package cloud.osasoft.jkaDiscordBot.dto.discord

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.CompileStatic
import groovy.transform.ToString

/**
 * Info taken from <a href="https://birdie0.github.io/discord-webhooks-guide/discord_webhook.html">https://birdie0.github.io/discord-webhooks-guide/discord_webhook.html</a>
 */
@CompileStatic
@ToString
class DiscordAuthor {

	/**
	 * name of author
	 */
	String name

	/**
	 * url of author. If <tt>name</tt> was used, it becomes a hyperlink
	 */
	String url

	/**
	 * url of author icon
	 */
	@JsonProperty("icon_url")
	String iconUrl
}
