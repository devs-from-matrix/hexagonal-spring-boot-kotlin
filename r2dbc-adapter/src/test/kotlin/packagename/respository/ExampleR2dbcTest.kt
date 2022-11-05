package packagename.respository

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.r2dbc.core.DatabaseClient
import reactor.test.StepVerifier
import java.util.function.Consumer


@SpringBootTest
class ExampleR2dbcTest {

  @Autowired
  lateinit var client: DatabaseClient

  @Test
  fun `should be able to boot`() {
    assert(true)
  }

  @Test
  fun `should be able to perform ddl operations`() {
    val statements: List<String> = listOf( //
        "DROP TABLE IF EXISTS player;",
        "CREATE table player (id INT AUTO_INCREMENT NOT NULL, name VARCHAR2, age INT NOT NULL);")

    statements.forEach(Consumer { it: String ->
      client.sql(it)
          .fetch()
          .rowsUpdated()
          .`as`(StepVerifier::create)
          .expectNextCount(1)
          .verifyComplete()
    })
  }
}