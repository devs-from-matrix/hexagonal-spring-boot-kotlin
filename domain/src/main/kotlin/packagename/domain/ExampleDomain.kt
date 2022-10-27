package packagename.domain

import packagename.domain.exception.ExampleNotFoundException
import packagename.domain.model.Example
import packagename.domain.port.ObtainExample
import packagename.domain.port.RequestExample
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class ExampleDomain(private val obtainExample: ObtainExample) : RequestExample {

  constructor() : this(object : ObtainExample {})

  override fun getExamples(): Flux<Example> =
      obtainExample.getAllExamples()

  override fun getExampleByCode(code: Long): Mono<Example> =
      obtainExample.getExampleByCode(code).switchIfEmpty(Mono.error(ExampleNotFoundException(code)))
}
