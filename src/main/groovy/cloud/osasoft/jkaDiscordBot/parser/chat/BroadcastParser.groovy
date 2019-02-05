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
class BroadcastParser implements LogParser {

	private static final CONNECTED_MESSAGE = "*connected*"
	private static final DISCONNECTED_MESSAGE = "*disconnected*"
	private static final ENTER_GAME_MESSAGE = "*entered the game*"

	@Override
	boolean matches(String line) {
		return line ==~ /^broadcast: .*/
	}

	@Override
	DiscordEmbed parse(String line) {
		DiscordEmbed embed = new DiscordEmbed(author: new DiscordAuthor(name: "Server"))

		line -= "broadcast: "

		switch (line) {
			case ~/^print ".* @@@PLCONNECT\n"$/:
				embed.description = getName(line) + CONNECTED_MESSAGE
				break
			case ~/^print ".* @@@DISCONNECTED\n"$/:
				embed.description = getName(line) + DISCONNECTED_MESSAGE
				break
			case ~/^print ".* @@@PLENTER\n"$/:
				embed.description = getName(line) + ENTER_GAME_MESSAGE
				break
			default:
				embed.description = line
		}

		return embed
	}

	private String getName(String line) {
		return line[line.indexOf('"') + 1..line.lastIndexOf(" @@@")]
	}
}
