package packagename.rest

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import packagename.domain.exception.ExampleNotFoundException
import packagename.domain.model.Example
import packagename.domain.model.ExampleInfo
import packagename.domain.port.RequestExample
import packagename.rest.exception.ExampleExceptionResponse

@WebFluxTest(ExampleResource::class)
class ExampleResourceTest {
  companion object {
    private const val API_URI = "/api/v1/examples"
  }

  @Autowired
  private lateinit var webTestClient: WebTestClient
  @Autowired
  private lateinit var requestExample: RequestExample

  @Test
  fun `should start the rest adapter application`() {
    assertThat(java.lang.Boolean.TRUE).isTrue
  }

  @Test
  fun `should give examples when asked for examples with the support of domain stub`() {
    // Given
    Mockito.lenient().`when`(requestExample.getExamples()).thenReturn(mockExampleInfo())
    // When
    webTestClient
            .get()
            .uri(API_URI)
            .exchange()
            // Then
            .expectStatus().isOk
            .expectBody(ExampleInfo::class.java)
            .consumeWith { assertThat(it.responseBody?.examples).isNotEmpty.extracting("description").contains("Johnny Johnny Yes Papa !!") }
  }

  @Test
  fun `should give the example when asked for an example by id with the support of domain stub`() {
    // Given
    val code = 1L
    val description = "Twinkle Twinkle"
    Mockito.lenient().`when`(requestExample.getExampleByCode(code)).thenReturn(mockExample(code, description))
    // When
    webTestClient
            .get()
            .uri("$API_URI/$code")
            .exchange()
            // Then
            .expectStatus().isOk
            .expectBody(Example::class.java)
            .consumeWith {
              assertThat(it.responseBody).isNotNull.isEqualTo(mockExample(code, description))
            }
  }

  @Test
  fun `should give exception when asked for an example by id that does not exists with the support of domain stub`() {
    // Given
    val code = -1000L
    Mockito.lenient().`when`(requestExample.getExampleByCode(code)).thenThrow(ExampleNotFoundException(code))
    // When
    webTestClient
            .get()
            .uri("$API_URI/$code")
            .exchange()
            // Then
            .expectStatus().isNotFound
            .expectBody(ExampleExceptionResponse::class.java)
            .consumeWith {
              assertThat(it.responseBody).isNotNull.isEqualTo(ExampleExceptionResponse("Example with code: [$code] does not exists", "$API_URI/$code"))
            }
  }

  private fun mockExample(code: Long, description: String): Example {
    return Example(code, description)
  }

  private fun mockExampleInfo(): ExampleInfo {
    return ExampleInfo(listOf(mockExample(1L, "Johnny Johnny Yes Papa !!")))
  }
}
