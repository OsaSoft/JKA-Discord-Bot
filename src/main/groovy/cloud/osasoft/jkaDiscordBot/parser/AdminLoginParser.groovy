package cloud.osasoft.jkaDiscordBot.parser

import cloud.osasoft.jkaDiscordBot.dto.discord.DiscordAuthor
import cloud.osasoft.jkaDiscordBot.dto.discord.DiscordEmbed
import cloud.osasoft.jkaDiscordBot.parser.LogParser
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

import javax.inject.Singleton

@Singleton
@Slf4j
@CompileStatic
class AdminLoginParser implements LogParser {

	@Override
	boolean matches(String line) {
		return line ==~ /^.* is logged as an admin council\.$/
	}

	@Override
	DiscordEmbed parse(String line) {
		return new DiscordEmbed(
				author: new DiscordAuthor(name: getName(line)),
				color: 0,
				description: getMessage(line)
		)
	}

	private String getName(String line) {
		return line[0..line.indexOf(" is logged as")]
	}

	private String getMessage(String line) {
		return line[line.indexOf(" is logged as")..-1]
	}
}
