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
class AdminCmdParser implements LogParser {

	@Override
	boolean matches(String line) {
		return line ==~ /^ADMIN CMD EXECUTED : .*/
	}

	@Override
	DiscordEmbed parse(String line) {
		return new DiscordEmbed(
				author: new DiscordAuthor(name: "Server"),
				description: line
		)
	}
}
