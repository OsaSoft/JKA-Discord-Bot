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

	LogReaderService(
			@Value('${jka.log.directory}') String logDir,
			@Value('${jka.log.file}') String logFile
	) {
		this.logDir = logDir
		this.logFile = logFile

		log.info "Starting watching $logDir for changes to $logFile"
		PathObservables
				.watchNonRecursive(Paths.get(logDir))
				.subscribeOn(Schedulers.newThread())
				.subscribe(this.&dirChange)
	}

	private long lastByte = 0

	private void dirChange(WatchEvent event) {
		try {
			if (event.context().toString() == logFile) {
				switch (event.kind()) {
					case ENTRY_MODIFY:
						def file = new File("$logDir/$logFile")
						if (lastByte > file.size()) lastByte = 0

						def bytes = new byte[file.size() - lastByte]
						file.newInputStream().tap {
							skip(lastByte) //jump to last position
							read(bytes)
						}
						lastByte += bytes.size()

						println new String(bytes)

						break
					case [ENTRY_CREATE, ENTRY_DELETE]:
						lastByte = 0
						break
				}
			}
		} catch (Exception ex) {
			log.error "Exception processing file change event", ex
		}
	}
}
