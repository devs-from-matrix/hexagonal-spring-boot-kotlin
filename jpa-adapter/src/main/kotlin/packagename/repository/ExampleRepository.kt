package packagename.repository

import packagename.domain.model.Example
import packagename.domain.port.ObtainExample
import packagename.repository.dao.ExampleDao
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class ExampleRepository(private val exampleDao: ExampleDao) : ObtainExample {

  override fun getAllExamples(): Flux<Example> {
    return Flux.fromIterable(exampleDao.findAll().map { it.toModel() })
  }

  override fun getExampleByCode(code: Long): Mono<Example> {
    val example = exampleDao.findByCode(code)
    return when(example.isPresent) {
      true -> Mono.just(example.get().toModel())
      false -> Mono.empty()
    }
  }
}
