package io.github.hjosugi.chatsample.simple;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The application has a chat page, and nothing in it was written here.
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = "spring.ai.anthropic.api-key=test-key")
class ApplicationTests {

	@Value("${local.server.port}")
	private int port;

	@Test
	void theChatPageIsThere() throws Exception {
		HttpResponse<String> page = HttpClient.newHttpClient()
			.send(HttpRequest.newBuilder(URI.create("http://localhost:" + this.port + "/chat")).build(),
					HttpResponse.BodyHandlers.ofString());

		assertThat(page.statusCode()).isEqualTo(200);
		assertThat(page.body()).contains("<deep-chat");
	}

}
