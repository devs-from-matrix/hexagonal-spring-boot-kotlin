package packagename.repository

import packagename.domain.model.Example
import packagename.domain.port.ObtainExample
import packagename.repository.dao.ExampleDao
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class ExampleRepository(private val exampleDao: ExampleDao) : ObtainExample {

  override fun getAllExamples(): Flux<Example> {
    return exampleDao.findAll().map { it.toModel() }
  }

  override fun getExampleByCode(code: Long): Mono<Example> {
    return exampleDao.findById(code).map { it.toModel() }
  }
}