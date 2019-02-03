package cloud.osasoft.jkaDiscordBot.service

import de.helmbold.rxfilewatcher.PathObservables
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.micronaut.context.annotation.Context
import io.micronaut.context.annotation.Value
import io.reactivex.schedulers.Schedulers

import java.nio.file.Paths
import java.nio.file.WatchEvent

import static java.nio.file.StandardWatchEventKinds.*

@Context
@Slf4j
@CompileStatic
class LogReaderService {

	private final String logDir
	private final String logFile

	private final DiscordService discordService

	LogReaderService(
			@Value('${jka.log.directory}') String logDir,
			@Value('${jka.log.file}') String logFile,
			DiscordService discordService
	) {
		this.logDir = logDir
		this.logFile = logFile
		this.discordService = discordService

		log.info "Starting watching $logDir for changes to $logFile"
		PathObservables
				.watchNonRecursive(Paths.get(logDir))
				.subscribeOn(Schedulers.newThread())
				.subscribe(this.&dirChange)
	}

	//TODO this shouldnt be in LogReaderService
	private void sendMessage(byte[] bytes) {
		def messages = new String(bytes).split("\n")
		log.debug "Changes to file: \n $messages"

		discordService.sendMessage(messages.findAll { matchesAllowed(it) }.join("\n"))
	}

	private boolean matchesAllowed(String s) {
		s.startsWith("broadcast:") ||
				s.startsWith("Kill:") ||
				s.startsWith("say:") ||
				s.startsWith("tell:") ||
				s.startsWith("say_clan:") ||
				s.startsWith("say_admin:") ||
				s.startsWith("ADMIN CMD EXECUTED") ||
				s ==~ /^.*is logged.*/
	}

	private long lastByte = 0

	private void dirChange(WatchEvent event) {
		try {
			if (event.context().toString() == logFile) {
				switch (event.kind()) {
					case ENTRY_MODIFY:
						log.debug "Watch event: File has been modified"

						def file = new File("$logDir/$logFile")
						if (lastByte > file.size()) lastByte = 0

						def bytes = new byte[file.size() - lastByte]
						file.newInputStream().tap {
							skip(lastByte) //jump to last position
							read(bytes)
						}

						log.debug "Read $bytes.length bytes"
						lastByte += bytes.size()

						sendMessage(bytes)
						break
					case [ENTRY_CREATE, ENTRY_DELETE]:
						log.debug "Watch event: File has been ${event.kind() == ENTRY_CREATE ? 'CREATED' : 'DELETED'}"
						lastByte = 0
						break
				}
			}
		} catch (Exception ex) {
			log.error "Exception processing file change event", ex
		}
	}
}
