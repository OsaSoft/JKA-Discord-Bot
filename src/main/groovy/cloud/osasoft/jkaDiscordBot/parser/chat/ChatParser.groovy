package cloud.osasoft.jkaDiscordBot.parser.chat

import cloud.osasoft.jkaDiscordBot.dto.discord.DiscordAuthor
import cloud.osasoft.jkaDiscordBot.dto.discord.DiscordEmbed
import cloud.osasoft.jkaDiscordBot.parser.LogParser
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

import javax.inject.Singleton

@Singleton
@Slf4j
@CompileStatic
class ChatParser implements LogParser {

	@Override
	boolean matches(String line) {
		return line ==~ /^say: .*/
	}

	@Override
	DiscordEmbed parse(String line) {
		line = this.stripChatType(line)

		DiscordEmbed embed = new DiscordEmbed()

		int separator = line.indexOf(": ")
		def (author, content) = [line[0..separator - 1], line[separator + 2..-1]]

		embed.author = new DiscordAuthor(name: author)
		embed.description = content

		return embed
	}

	protected String stripChatType(String line) {
		return line - "say: "
	}
}
