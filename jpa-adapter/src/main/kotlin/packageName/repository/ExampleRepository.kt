package packagename.repository

import packagename.domain.model.Example
import packagename.domain.port.ObtainExample
import packagename.repository.dao.ExampleDao

class ExampleRepository(private val exampleDao: ExampleDao) : ObtainExample {

  override fun getAllExamples(): List<Example> {
    return exampleDao.findAll().map { it.toModel() }
  }
}
