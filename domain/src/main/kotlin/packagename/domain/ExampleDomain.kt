package packagename.domain

import packagename.domain.model.ExampleInfo
import packagename.domain.port.ObtainExample
import packagename.domain.port.RequestExample

class ExampleDomain(private val obtainExample: ObtainExample) : RequestExample {

  constructor() : this(object : ObtainExample {})

  override fun getExamples(): ExampleInfo {
    return ExampleInfo(obtainExample.getAllExamples())
  }
}
