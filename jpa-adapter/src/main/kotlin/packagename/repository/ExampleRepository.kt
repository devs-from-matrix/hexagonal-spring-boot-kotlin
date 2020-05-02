package packagename.repository

import packagename.domain.model.Example
import packagename.domain.port.ObtainExample
import packagename.repository.dao.ExampleDao
import java.util.*

class ExampleRepository(private val exampleDao: ExampleDao) : ObtainExample {

  override fun getAllExamples(): List<Example> {
    return exampleDao.findAll().map { it.toModel() }
  }

  override fun getExampleByCode(code: Long): Optional<Example> {
    val example = exampleDao.findByCode(code)
    return when(example.isPresent) {
      true -> Optional.of(example.get().toModel())
      false -> Optional.empty()
    }
  }
}
