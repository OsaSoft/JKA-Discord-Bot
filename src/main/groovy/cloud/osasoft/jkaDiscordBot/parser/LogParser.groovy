package cloud.osasoft.jkaDiscordBot.parser


import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

import javax.inject.Singleton

@Singleton
@Slf4j
@CompileStatic
class LogParser {

	String parse(byte[] bytes) {
		def lines = new String(bytes).split("\n")
		log.debug "Changes to file: \n $lines"

		return pars(lines)
	}

	private List<String> pars(String[] lines) {
		List<String> parsed = []

		lines.each { msg ->
			switch (msg) {
				case ~/^broadcast: .*/:
					parsed << msg - "broadcast: "
					break
				case ~/^say: .*/:
					parsed << msg - "say: "
					break
				case ~/^Kill: .*/:
					parsed << msg - "Kill: "
					break
				case ~/^.* is logged as an .*/:
					parsed << "ADMIN LOGIN: " + msg
					break
				case { matchesAllowed(it as String) }:
					parsed << msg
					break
			}
		}

		//clean up colors in names and chat (TODO: figure out if its possible to display them in discord)
		return parsed.collect { it.replaceAll("\\^\\d", "") }
	}

	private boolean matchesAllowed(String s) {
		s.startsWith("say:") ||
//				s.startsWith("tell:") ||
				s.startsWith("say_clan:") ||
//				s.startsWith("say_admin:") ||
				s.startsWith("ADMIN CMD EXECUTED")
	}
}
