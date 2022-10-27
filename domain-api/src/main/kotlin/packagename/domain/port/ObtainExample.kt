package packagename.domain.port

import packagename.domain.model.Example
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ObtainExample {

  fun getAllExamples(): Flux<Example> {
    val example = Example(1L, "If you could read a leaf or tree youd have no need of books.-- Alistair Cockburn (1987)")
    return Flux.just(example)
  }

  fun getExampleByCode(code: Long): Mono<Example> {
    return Mono.just(Example(1L, "If you could read a leaf or tree youd have no need of books.-- Alistair Cockburn (1987)"))
  }
}
