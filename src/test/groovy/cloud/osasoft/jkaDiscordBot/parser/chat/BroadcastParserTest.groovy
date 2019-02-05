package cloud.osasoft.jkaDiscordBot.parser.chat

import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class BroadcastParserTest extends Specification {

	@Subject
	BroadcastParser broadcastParser = new BroadcastParser()

	void setup() {
	}

	void cleanup() {
	}

	@Unroll
	def "Matches chat message line: #line"() {
		expect:
			broadcastParser.matches(line) == expected

		where:
			line                     | expected
			"broadcast: Test"        | true
			"broadcast: print: Test" | true
			" broadcast: test"       | false
			"broadcast:test"         | false
			"test:broadcast: "       | false
	}

	def "Parses CONNECTED line"() {
		expect:
			with(broadcastParser.parse('broadcast: print "BobaFett @@@PLCONNECT\n"')) {
				author?.name == "Server"
				description == "BobaFett $BroadcastParser.CONNECTED_MESSAGE"
			}
	}

	def "Parses DISCONNECTED line"() {
		expect:
			with(broadcastParser.parse('broadcast: print "Plague @@@DISCONNECTED\n"')) {
				author?.name == "Server"
				description == "Plague $BroadcastParser.DISCONNECTED_MESSAGE"
			}
	}

	def "Parses ENTER line"() {
		expect:
			with(broadcastParser.parse('broadcast: print "L3gion @@@PLENTER\n"')) {
				author?.name == "Server"
				description == "L3gion $BroadcastParser.ENTER_GAME_MESSAGE"
			}
	}
}
