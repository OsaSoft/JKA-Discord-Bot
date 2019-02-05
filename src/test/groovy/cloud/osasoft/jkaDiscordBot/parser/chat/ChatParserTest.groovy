package cloud.osasoft.jkaDiscordBot.parser.chat

import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class ChatParserTest extends Specification {

	@Subject
	ChatParser chatParser = new ChatParser()

	void setup() {
	}

	void cleanup() {
	}

	@Unroll
	def "Matches chat message line: #line"() {
		expect:
			chatParser.matches(line) == expected

		where:
			line         | expected
			"say: Test"  | true
			" say: test" | false
			"say:test"   | false
			"test:say: " | false
	}

	@Unroll
	def "Parses line: #line"() {
		expect:
			with(chatParser.parse(line)) {
				author?.name == expAuthor
				description == expContent
			}

		where:
			line                                     | expAuthor             | expContent
			"say: =JM=ShadowSnake<GD>: Dont hug me"  | "=JM=ShadowSnake<GD>" | "Dont hug me"
			"say: NameWith:InIt: test"               | "NameWith:InIt"       | "test"
			"say:    : my name is blank!"            | "   "                 | "my name is blank!"
			"say: n00b: can I use : in my messages?" | "n00b"                | "can I use : in my messages?"
	}
}
