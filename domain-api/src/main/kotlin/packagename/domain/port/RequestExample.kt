package packagename.domain.port

import packagename.domain.model.Example
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface RequestExample {

  fun getExamples(): Flux<Example>
  fun getExampleByCode(code: Long): Mono<Example>
}
