package packagename.domain

import packagename.domain.exception.ExampleNotFoundException
import packagename.domain.model.Example
import packagename.domain.model.ExampleInfo
import packagename.domain.port.ObtainExample
import packagename.domain.port.RequestExample

class ExampleDomain(private val obtainExample: ObtainExample) : RequestExample {

  constructor() : this(object : ObtainExample {})

  override fun getExamples(): ExampleInfo {
    return ExampleInfo(obtainExample.getAllExamples())
  }

  override fun getExampleByCode(code: Long): Example {
    val example = obtainExample.getExampleByCode(code)
    return when(example.isPresent) {
      true -> example.get()
      false -> throw ExampleNotFoundException(code)
    }
  }
}
