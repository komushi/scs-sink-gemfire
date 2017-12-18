package info.cloudnative.scs.sink.gemfire;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


@SpringBootApplication
@Import(org.springframework.cloud.stream.app.gemfire.sink.GemfireSinkConfiguration.class)
public class GemfireSinkRabbitApplication {

	public static void main(String[] args) {
		SpringApplication.run(GemfireSinkRabbitApplication.class, args);
	}
}
