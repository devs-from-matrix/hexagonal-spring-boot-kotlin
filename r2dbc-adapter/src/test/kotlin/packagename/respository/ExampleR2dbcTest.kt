package packagename.respository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import packagename.domain.port.ObtainExample
import reactor.test.StepVerifier
import java.util.function.Consumer


@SpringBootTest
@ExtendWith(SpringExtension::class)
@TestMethodOrder(OrderAnnotation::class)
@ActiveProfiles("test")
class ExampleR2dbcTest {

  @Autowired
  lateinit var client: DatabaseClient

  @Autowired
  lateinit var obtainExample: ObtainExample

  @Test
  fun `should be able to boot`() {
    assertThat(true).isTrue
  }

  @Test
  fun `given examples exists in database when asked for examples from database should return all examples`() {
    // Given
    deleteExampleData()
    loadSampleExampleData()
    // When & Then
    obtainExample.getAllExamples().`as`(StepVerifier::create).expectNextMatches {
      assertThat(it.description).isEqualTo("Twinkle twinkle little star")
      true
    }.verifyComplete()
  }

  @Test
  fun `given no examples exists in database when asked for examples should return empty`() {
    // Given
    deleteExampleData()
    // When & Then
    obtainExample.getAllExamples().`as`(StepVerifier::create).verifyComplete()
  }

  @Test
  @Order(1) // we need this order because of the auto-increment
  fun `given examples exists in database when asked for example by id should return the example`() {
    // Given
    deleteExampleData()
    loadSampleExampleData()
    // When & Then
    obtainExample.getExampleByCode(1).`as`(StepVerifier::create).expectNextMatches {
      assertThat(it.description).isEqualTo("Twinkle twinkle little star")
      true
    }.verifyComplete()
  }

  @Test
  fun `given examples exists in database when asked for example by id that does not exists should empty`() {
    // Given
    deleteExampleData()
    loadSampleExampleData()
    // When & Then
    obtainExample.getExampleByCode(-1).`as`(StepVerifier::create).expectNextCount(0)
      .verifyComplete()
  }

  private fun loadSampleExampleData() {
    val statements: List<String> = listOf(
      "INSERT INTO EXAMPLE.T_EXAMPLE(CODE,DESCRIPTION) VALUES(1, 'Twinkle twinkle little star');",
    )

    statements.forEach(Consumer { it: String ->
      client.sql(it).fetch().rowsUpdated().`as`(StepVerifier::create).expectNextCount(1)
        .verifyComplete()
    })
  }

  private fun deleteExampleData() {
    val statements: List<String> = listOf(
      "DELETE FROM EXAMPLE.T_EXAMPLE",
    )

    statements.forEach(Consumer { it: String ->
      client.sql(it).fetch().rowsUpdated().`as`(StepVerifier::create).expectNextCount(1)
        .verifyComplete()
    })
  }
}