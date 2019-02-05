package cloud.osasoft.jkaDiscordBot.service

import cloud.osasoft.jkaDiscordBot.dto.discord.DiscordEmbed
import cloud.osasoft.jkaDiscordBot.dto.discord.DiscordMessage
import cloud.osasoft.jkaDiscordBot.parser.LogParser
import cloud.osasoft.jkaDiscordBot.util.Utils
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

import javax.inject.Singleton

@Singleton
@Slf4j
@CompileStatic
class LogParserService {

	private final List<LogParser> parsers

	LogParserService(List<LogParser> parsers) {
		this.parsers = parsers
	}

	DiscordMessage parse(byte[] bytes) {
		def lines = new String(bytes).split("\n")
		log.debug "Changes to file: \n $lines"

		def message = new DiscordMessage()

		message.embeds.addAll(lines.collect(this.&parseLine))

		return message
	}

	DiscordEmbed parseLine(String line) {
		return parsers.find { it.matches(line) }.parse(Utils.stripColours(line))
	}

//	private List<String> pars(String[] lines) {
//		List<String> parsed = []
//
//		lines.each { msg ->
//			switch (msg) {
//				case ~/^Kill: .*/:
//					parsed << msg - "Kill: "
//					break
//				case ~/^.* is logged as an .*/:
//					parsed << "ADMIN LOGIN: " + msg
//					break
//				case { matchesAllowed(it as String) }:
//					parsed << msg
//					break
//			}
//		}
//		return parsed
//	}

//	private boolean matchesAllowed(String s) {
//				s.startsWith("ADMIN CMD EXECUTED")
//	}
}
