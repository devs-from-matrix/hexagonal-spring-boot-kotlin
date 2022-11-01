package packagename.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.junit.jupiter.SpringExtension
import packagename.domain.port.ObtainExample
import reactor.test.StepVerifier

@ExtendWith(SpringExtension::class)
@DataJpaTest
@ActiveProfiles("test")
class ExampleJpaTest {

  @Autowired
  private lateinit var obtainExample: ObtainExample

  @Test
  fun `should start the application`() {
    assertThat(java.lang.Boolean.TRUE).isTrue
  }

  @Sql(scripts = ["/sql/data.sql"])
  @Test
  fun `given examples exists in database when asked for examples from database should return all examples`() {
    // Given from @Sql
    // When
    val examples = obtainExample.getAllExamples()
    // Then
    StepVerifier.create(examples)
        .expectNextMatches {
          assertThat(it.description).isEqualTo("Twinkle twinkle little star")
          true
        }
        .verifyComplete()
  }

  @Test
  fun `given no examples exists in database when asked for examples should return empty`() {
    // When
    val examples = obtainExample.getAllExamples()
    // Then
    StepVerifier.create(examples)
        .verifyComplete()
  }

  @Sql(scripts = ["/sql/data.sql"])
  @Test
  fun `given examples exists in database when asked for example by id should return the example`() {
    // Given from @Sql
    // When
    val example = obtainExample.getExampleByCode(1L)
    // Then
    StepVerifier.create(example)
        .expectNextMatches {
          assertThat(it.description).isEqualTo("Twinkle twinkle little star")
          true
        }
        .verifyComplete()
  }

  @Sql(scripts = ["/sql/data.sql"])
  @Test
  fun `given examples exists in database when asked for example by id that does not exists should empty`() {
    // Given from @Sql
    // When
    val example = obtainExample.getExampleByCode(-1000L)
    // Then
    StepVerifier.create(example)
        .verifyComplete()
  }
}
