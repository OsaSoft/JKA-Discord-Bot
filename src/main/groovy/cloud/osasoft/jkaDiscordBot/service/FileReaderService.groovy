package cloud.osasoft.jkaDiscordBot.service

import cloud.osasoft.jkaDiscordBot.parser.LogParser
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
class FileReaderService {

	private final String dir
	private final String file

	private final LogParser logParser
	private final DiscordService discordService

	FileReaderService(
			@Value('${jka.log.directory}') String dir,
			@Value('${jka.log.file}') String file,
			LogParser logParser,
			DiscordService discordService
	) {
		this.dir = dir
		this.file = file
		this.discordService = discordService
		this.logParser = logParser

		log.info "Starting watching $dir for changes to $file"
		PathObservables
				.watchNonRecursive(Paths.get(dir))
				.subscribeOn(Schedulers.newThread())
				.subscribe(this.&dirChange)
	}

	private long lastByte = 0

	private void dirChange(WatchEvent event) {
		try {
			if (event.context().toString() == file) {
				switch (event.kind()) {
					case ENTRY_MODIFY:
						log.debug "Watch event: File has been modified"

						def file = new File((dir ? "$dir/" : "") + file)
						if (lastByte > file.size()) lastByte = 0

						def bytes = new byte[file.size() - lastByte]
						file.newInputStream().tap {
							skip(lastByte) //jump to last position
							read(bytes)
						}

						log.debug "Read $bytes.length bytes"
						lastByte += bytes.size()

						discordService.sendMessage(logParser.parse(bytes))
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
