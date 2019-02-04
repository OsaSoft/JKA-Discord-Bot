package cloud.osasoft.jkaDiscordBot.parser

import cloud.osasoft.jkaDiscordBot.dto.discord.DiscordEmbed
import groovy.transform.CompileStatic

@CompileStatic
interface LogParser {

	boolean matches(String line)

	DiscordEmbed parse(String line)
}