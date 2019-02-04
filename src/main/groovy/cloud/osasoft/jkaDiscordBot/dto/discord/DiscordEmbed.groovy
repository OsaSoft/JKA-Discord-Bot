package cloud.osasoft.jkaDiscordBot.dto.discord

import com.fasterxml.jackson.annotation.JsonGetter
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.CompileStatic
import groovy.transform.ToString

/**
 * Info taken from <a href="https://birdie0.github.io/discord-webhooks-guide/discord_webhook.html">https://birdie0.github.io/discord-webhooks-guide/discord_webhook.html</a>
 */
@CompileStatic
@ToString
class DiscordEmbed {

	/**
	 * color code of the embed. You have to use Decimal numeral system, not Hexadecimal.
	 * You can use SpyColor for that. It has decimal number converter.
	 */
	Integer color

	/**
	 * embed author object
	 */
	DiscordAuthor author

	/**
	 * title of embed
	 */
	String title

	/**
	 * url of embed. If <tt>title</tt> was used, it becomes hyperlink
	 */
	String url

	/**
	 * description text
	 */
	String description

	/**
	 * array of embed field objects
	 */
	List<DiscordField> fields = []

	/**
	 * url of embed thumbnail
	 */
	String thumbnailUrl

	/**
	 * url of embed image
	 */
	String imageUrl

	/**
	 * embed footer object
	 */
	DiscordFooter footer

	/**
	 * ISO8601 timestamp <tt>(yyyy-mm-ddThh:mm:ss.msZ)</tt>
	 */
	@JsonProperty("timestamp")
	Date date

	@JsonGetter("thumbnailUrl")
	private Map<String, String> jsonThumbnail() {
		return thumbnailUrl ? [url: thumbnailUrl] : null
	}

	@JsonGetter("imageUrl")
	private Map<String, String> jsonImage() {
		return imageUrl ? [url: imageUrl] : null
	}
}
