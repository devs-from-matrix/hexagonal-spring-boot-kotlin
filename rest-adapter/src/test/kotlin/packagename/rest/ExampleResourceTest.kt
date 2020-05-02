package packagename.rest

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import packagename.domain.exception.ExampleNotFoundException
import packagename.domain.model.Example
import packagename.domain.model.ExampleInfo
import packagename.domain.port.RequestExample
import packagename.rest.exception.ExampleExceptionResponse

@ExtendWith(MockitoExtension::class)
@SpringBootTest(classes = [ExamplePoetryRestAdapterApplication::class], webEnvironment = RANDOM_PORT)
@EnableAutoConfiguration(exclude = [DataSourceAutoConfiguration::class])
class ExampleResourceTest {
  companion object {
    private const val LOCALHOST = "http://localhost:"
    private const val API_URI = "/api/v1/examples"
  }

  @LocalServerPort
  private val port: Int = 0
  @Autowired
  private lateinit var restTemplate: TestRestTemplate
  @Autowired
  private lateinit var requestExample: RequestExample

  @Test
  fun `should start the rest adapter application`() {
    assertThat(java.lang.Boolean.TRUE).isTrue()
  }

  @Test
  fun `should give examples when asked for examples with the support of domain stub`() {
    // Given
    Mockito.lenient().`when`(requestExample.getExamples()).thenReturn(mockExampleInfo())
    // When
    val url = "$LOCALHOST$port$API_URI"
    val responseEntity = restTemplate.getForEntity(url, ExampleInfo::class.java)
    // Then
    assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
    assertThat(responseEntity.body).isNotNull
    assertThat(responseEntity.body.examples).isNotEmpty.extracting("description").contains("Johnny Johnny Yes Papa !!")
  }

  @Test
  fun `should give the example when asked for an example by id with the support of domain stub`() {
    // Given
    val code = 1L
    val description = "Twinkle Twinkle"
    Mockito.lenient().`when`(requestExample.getExampleByCode(code)).thenReturn(mockExample(code, description))
    // When
    val url = "$LOCALHOST$port$API_URI/$code"
    val responseEntity = restTemplate.getForEntity(url, Example::class.java)
    // Then
    assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
    assertThat(responseEntity.body).isNotNull
    assertThat(responseEntity.body).isNotNull.isEqualTo(mockExample(code, description))
  }

  @Test
  fun `should give exception when asked for an example by id that does not exists with the support of domain stub`() {
    // Given
    val code = -1000L
    Mockito.lenient().`when`(requestExample.getExampleByCode(code)).thenThrow(ExampleNotFoundException(code))
    // When
    val url = "$LOCALHOST$port$API_URI/$code"
    val responseEntity = restTemplate.getForEntity(url, ExampleExceptionResponse::class.java)
    // Then
    assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
    assertThat(responseEntity.body).isNotNull
    assertThat(responseEntity.body).isEqualTo(ExampleExceptionResponse("Example with code: [$code] does not exists", "$API_URI/$code"))
  }

  private fun mockExample(code: Long, description: String): Example {
    return Example(code, description)
  }

  private fun mockExampleInfo(): ExampleInfo {
    return ExampleInfo(listOf(mockExample(1L, "Johnny Johnny Yes Papa !!")))
  }
}
