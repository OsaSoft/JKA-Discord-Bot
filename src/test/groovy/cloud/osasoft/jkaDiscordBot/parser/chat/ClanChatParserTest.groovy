package cloud.osasoft.jkaDiscordBot.parser.chat

import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class ClanChatParserTest extends Specification {

	@Subject
	ClanChatParser clanChatParser = new ClanChatParser()

	void setup() {
	}

	void cleanup() {
	}

	@Unroll
	def "Matches chat message line: #line"() {
		expect:
			clanChatParser.matches(line) == expected

		where:
			line              | expected
			"say_clan: Test"  | true
			" say_clan: test" | false
			"say_clan:test"   | false
			"test:say_clan: " | false
			"say: Test"       | false
			" say: test"      | false
			"say:test"        | false
			"test:say: "      | false
	}

	@Unroll
	def "Parses line: #line"() {
		expect:
			with(clanChatParser.parse(line)) {
				author?.name == expAuthor
				getTitle() == expContent
				color == 16711680
			}

		where:
			line                                          | expAuthor             | expContent
			"say_clan: =JM=ShadowSnake<GD>: Dont hug me"  | "=JM=ShadowSnake<GD>" | "<Clan> Dont hug me"
			"say_clan: NameWith:InIt: test"               | "NameWith:InIt"       | "<Clan> test"
			"say_clan:    : my name is blank!"            | "   "                 | "<Clan> my name is blank!"
			"say_clan: n00b: can I use : in my messages?" | "n00b"                | "<Clan> can I use : in my messages?"
	}
}
