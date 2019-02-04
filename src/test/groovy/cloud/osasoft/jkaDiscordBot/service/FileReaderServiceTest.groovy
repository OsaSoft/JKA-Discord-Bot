package cloud.osasoft.jkaDiscordBot.service

import cloud.osasoft.jkaDiscordBot.parser.LogParser
import org.spockframework.util.NotThreadSafe
import spock.lang.Specification
import spock.lang.Subject
import spock.util.concurrent.BlockingVariable

@NotThreadSafe
class FileReaderServiceTest extends Specification {

	@Subject
	FileReaderService fileReaderService

	DiscordService discordService = Mock()
	LogParser logParser = Mock()

	ThreadLocal<File> logFile = new ThreadLocal<>()

	void setup() {
		def logFileName = "log-file" + UUID.randomUUID()
		logFile.set(new FileTreeBuilder().file(logFileName))

		fileReaderService = new FileReaderService(logFile.get().canonicalPath - "/$logFileName", logFileName, logParser, discordService)
	}

	void cleanup() {
		logFile.get()?.delete()
	}

	def "Watched file changes are read (empty file)"() {
		given: "File is empty"
		and:
			def executionCheck = new BlockingVariable<Boolean>()
			logParser.parse(addition.bytes) >> { executionCheck.set(true) }

		when: "File is changed"
			logFile.get().write(addition)
		then: "changes are processed"
			executionCheck.get()

		where:
			addition << [
					"foo",
					"""this is a multiline
						string yo
						
						really a lot of
						
						
						
						lines""".stripIndent(),
					"test\n aaaaaa"
			]
	}

	def "Watched file changes are read (file with content)"() {
		given: "File has content"
			logFile.get().write("foobar")
			logFile.get().write("test\nfoo")
		and:
			def executionCheck = new BlockingVariable<Boolean>()
			logParser.parse(addition.bytes) >> { executionCheck.set(true) }

		when: "File is changed"
			logFile.get().write(addition)
		then: "changes are processed"
			executionCheck.get()

		where:
			addition << [
					"foo",
					"""this is a multiline
						string yo
						
						really a lot of
						
						
						
						lines""".stripIndent(),
					"test\n aaaaaa"
			]
	}
}
