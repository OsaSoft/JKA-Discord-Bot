package cloud.osasoft.jkaDiscordBot

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.micronaut.context.ApplicationContext

import java.util.concurrent.TimeUnit

@CompileStatic
@Slf4j
class Application {
	static void main(String[] args) {
		long start = System.currentTimeMillis()

		def context = ApplicationContext
				.build()
				.build()
				.start()

		log.info "Startup in ${System.currentTimeMillis() - start}ms"

		//every 10 seconds check if the context is still running, otherwise quit
		while (context.isRunning()) {
			Thread.sleep(TimeUnit.SECONDS.toMillis(10))
		}
	}
}