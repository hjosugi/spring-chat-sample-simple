# spring-chat-sample-simple

The smallest application that uses [spring-chat](https://github.com/hjosugi/spring-chat): a chat page, with a model that answers and streams its reply. Three files and a pom, and nothing else.

For an application with a login, tools and a confirmation, see [spring-chat-sample](https://github.com/hjosugi/spring-chat-sample).

## Run it

spring-chat is not on Maven Central yet, so install it first:

```bash
git clone --branch v0.1.0 --depth 1 https://github.com/hjosugi/spring-chat
(cd spring-chat && ./mvnw install -DskipTests)
```

Then, in this repository:

```bash
ANTHROPIC_API_KEY=... ./mvnw spring-boot:run
```

Open <http://localhost:8080/chat>. It needs Java 17 or later.

## What you write

| | |
| --- | --- |
| `pom.xml` | Three dependencies: `spring-chat-spring-boot-starter`, a web stack (`spring-boot-starter-webmvc`) and a model (`spring-ai-starter-model-anthropic`) |
| `Application.java` | The `main` method of any Spring Boot application, 10 lines. There is no controller, no template and no JavaScript |
| `application.properties` | Two lines: the key and the model |

## What you do not write

| | |
| --- | --- |
| The page | A chat in the browser, with the reply appearing as it is written and a button that stops it. It is the [Deep Chat](https://deepchat.dev) web component, served from a WebJar: no React, no build step. It is at `/chat`, and one line puts it into a page of your own |
| The endpoint | `POST /chat/stream` answers with server-sent events, one for each piece of the reply, with a ping while the model is quiet and an error event that tells the browser nothing about the model or its key |
| The call of the model | Spring AI's `ChatClient`, streamed, on a thread that may block, and cancelled when the browser stops or leaves |
| The conversation | What was said before goes to the model with each message. A conversation is a session of Spring AI, removed after a day. With a login in the application it belongs to the user who started it |
| The limits | A longest message, one reply at a time for a conversation, and a reply that was cut says so |

Without spring-chat that is a controller that streams, the page and its script, and the memory of a conversation, all to write, and to get right when a reply is stopped half way.

## One more property

```properties
spring-chat.transport=websocket
```

carries the reply over a WebSocket instead of server-sent events. It also needs `spring-boot-starter-websocket` in the pom. The page, the events and the rest stay as they are.

## Test

`./mvnw verify` starts the application and asks for the page. It needs no key. spring-chat-sample has the tests that talk to a model, through a stand-in for its API.

## License

[Apache License 2.0](LICENSE)
