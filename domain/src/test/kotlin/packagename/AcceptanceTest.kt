package packagename

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import packagename.domain.ExampleDomain
import packagename.domain.exception.ExampleNotFoundException
import packagename.domain.model.Example
import packagename.domain.port.ObtainExample
import java.util.*

@ExtendWith(MockitoExtension::class)
class AcceptanceTest {

  @Test
  fun `should be able to get examples when asked for examples from hard coded examples`() {
    /*
      RequestExample    - left side port
      ExampleDomain     - hexagon (domain)
      ObtainExample     - right side port
    */
    val requestExample = ExampleDomain() // the example is hard coded
    val exampleInfo = requestExample.getExamples()
    assertThat(exampleInfo).isNotNull
    assertThat(exampleInfo.examples).isNotEmpty.extracting("description")
        .contains("If you could read a leaf or tree\r\nyoud have no need of books.\r\n-- Alistair Cockburn (1987)")
  }

  @Test
  fun `should be able to get examples when asked for examples from stub`(@Mock obtainExample: ObtainExample) {
    // Stub
    val example = Example(1L, "I want to sleep\r\nSwat the flies\r\nSoftly, please.\r\n\r\n-- Masaoka Shiki (1867-1902)")
    Mockito.lenient().`when`(obtainExample.getAllExamples()).thenReturn(listOf(example))
    // hexagon
    val requestExample = ExampleDomain(obtainExample)
    val exampleInfo = requestExample.getExamples()
    assertThat(exampleInfo).isNotNull
    assertThat(exampleInfo.examples).isNotEmpty.extracting("description")
        .contains("I want to sleep\r\nSwat the flies\r\nSoftly, please.\r\n\r\n-- Masaoka Shiki (1867-1902)")
  }

  @Test
  fun `should be able to get example when asked for example by id from stub`(@Mock obtainExample: ObtainExample) {
    // Given
    // stub
    val code = 1L
    val description = "I want to sleep\r\nSwat the flies\r\nSoftly, please.\r\n\r\n-- Masaoka Shiki (1867-1902)"
    val expectedExample = Example(code, description)
    Mockito.lenient().`when`(obtainExample.getExampleByCode(code)).thenReturn(Optional.of(expectedExample))
    // When
    val requestExample = ExampleDomain(obtainExample)
    val actualExample = requestExample.getExampleByCode(code)
    // Then
    assertThat(actualExample).isNotNull.isEqualTo(expectedExample)
  }

  @Test
  fun `should be throw exception when asked for example by id that does not exists from stub`(@Mock obtainExample: ObtainExample) {
    // Given
    // stub
    val code = -1000L
    Mockito.lenient().`when`(obtainExample.getExampleByCode(code)).thenReturn(Optional.empty())
    // When
    val requestExample = ExampleDomain(obtainExample)
    // Then
    assertThatThrownBy { requestExample.getExampleByCode(code) }.isInstanceOf(ExampleNotFoundException::class.java)
        .hasMessageContaining("Example with code: [$code] does not exists")
  }
}
