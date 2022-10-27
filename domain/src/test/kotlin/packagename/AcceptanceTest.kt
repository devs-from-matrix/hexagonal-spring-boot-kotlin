package packagename

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import packagename.domain.ExampleDomain
import packagename.domain.exception.ExampleNotFoundException
import packagename.domain.model.Example
import packagename.domain.port.ObtainExample
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
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
    val examples: Flux<Example> = requestExample.getExamples()
    StepVerifier.create(examples)
        .expectNextMatches {
          assertThat(it.description).isEqualTo("If you could read a leaf or tree youd have no need of books.-- Alistair Cockburn (1987)")
          true
        }
        .verifyComplete()
  }

  @Test
  fun `should be able to get examples when asked for examples from stub`(@Mock obtainExample: ObtainExample) {
    // Stub
    val example = Example(1L, "I want to sleep Swat the flies, Softly, please.-- Masaoka Shiki (1867-1902)")
    Mockito.lenient().`when`(obtainExample.getAllExamples()).thenReturn(Flux.just(example))
    // hexagon
    val requestExample = ExampleDomain(obtainExample)
    val examples: Flux<Example> = requestExample.getExamples()
    StepVerifier.create(examples)
        .expectNextMatches {
          assertThat(it.description).isEqualTo("I want to sleep Swat the flies, Softly, please.-- Masaoka Shiki (1867-1902)")
          true
        }
        .verifyComplete()
  }

  @Test
  fun `should be able to get example when asked for example by id from stub`(@Mock obtainExample: ObtainExample) {
    // Given
    // stub
    val code = 1L
    val description = "I want to sleep Swat the flies, Softly, please.-- Masaoka Shiki (1867-1902)"
    val expectedExample = Example(code, description)
    Mockito.lenient().`when`(obtainExample.getExampleByCode(code)).thenReturn(Mono.just(expectedExample))
    // When
    val requestExample = ExampleDomain(obtainExample)
    val actualExample = requestExample.getExampleByCode(code)
    // Then
    StepVerifier.create(actualExample)
        .expectNextMatches {
          assertThat(it.description).isEqualTo("I want to sleep Swat the flies, Softly, please.-- Masaoka Shiki (1867-1902)")
          true
        }
        .verifyComplete()
  }

  @Test
  fun `should be throw exception when asked for example by id that does not exists from stub`(@Mock obtainExample: ObtainExample) {
    // Given
    // stub
    val code = -1000L
    Mockito.lenient().`when`(obtainExample.getExampleByCode(code)).thenReturn(Mono.empty())
    // When
    val requestExample = ExampleDomain(obtainExample)
    val actualExample = requestExample.getExampleByCode(code)
    // Then
    StepVerifier.create(actualExample)
        .expectErrorMatches {
          assertThat(it).isInstanceOf(ExampleNotFoundException::class.java)
              .hasMessageContaining("Example with code: [$code] does not exists")
          true
        }
        .verify()
  }
}
