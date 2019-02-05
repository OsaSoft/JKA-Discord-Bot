package cloud.osasoft.jkaDiscordBot.util

import groovy.transform.CompileStatic

@CompileStatic
final class Utils {

	private Utils() { throw new IllegalStateException("Cannot instantiate utils class!") }

	static String stripColours(String line) {
		//clean up colors in names and chat (TODO: figure out if its possible to display them in discord)
		return line.replaceAll("\\^\\d", "")
	}
}
