package cloud.osasoft.jkaDiscordBot.service

import de.helmbold.rxfilewatcher.PathObservables
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.micronaut.context.annotation.Context
import io.micronaut.context.annotation.Value
import io.reactivex.schedulers.Schedulers

import java.nio.file.Paths
import java.nio.file.WatchEvent

import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY

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

	private void dirChange(WatchEvent event) {
		if (event.context().toString() == logFile && event.kind() == ENTRY_MODIFY) {
			println new File("$logDir/$logFile").text
		}
	}
}
