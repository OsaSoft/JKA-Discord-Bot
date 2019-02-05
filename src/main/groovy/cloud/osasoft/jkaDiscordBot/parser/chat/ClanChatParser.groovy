package cloud.osasoft.jkaDiscordBot.parser.chat

import cloud.osasoft.jkaDiscordBot.dto.discord.DiscordEmbed
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

import javax.inject.Singleton

@Singleton
@Slf4j
@CompileStatic
class ClanChatParser extends ChatParser {

	@Override
	boolean matches(String line) {
		return line ==~ /^say_clan: .*/
	}

	@Override
	DiscordEmbed parse(String line) {
		DiscordEmbed embed = super.parse(line)
		embed.color = 16711680 //red
		embed.title = "Clan chat"

		return embed
	}

	@Override
	protected String stripChatType(String line) {
		return line - "say_clan: "
	}
}
