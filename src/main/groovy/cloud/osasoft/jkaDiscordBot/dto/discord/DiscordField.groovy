package cloud.osasoft.jkaDiscordBot.dto.discord

import groovy.transform.CompileStatic
import groovy.transform.ToString

/**
 * Info taken from <a href="https://birdie0.github.io/discord-webhooks-guide/discord_webhook.html">https://birdie0.github.io/discord-webhooks-guide/discord_webhook.html</a>
 */
@CompileStatic
@ToString
class DiscordField {

	/**
	 * name of the field
	 */
	String name

	/**
	 * value of the field
	 */
	String value

	/**
	 * if true, fields will be displayed in same line, but there can only be 3 max in same line or 2 max if you used thumbnail
	 */
	boolean inline = false
}
