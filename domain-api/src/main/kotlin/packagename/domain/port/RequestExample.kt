package packagename.domain.port

import packagename.domain.model.Example
import packagename.domain.model.ExampleInfo

interface RequestExample {
  fun getExamples(): ExampleInfo
  fun getExampleByCode(code: Long): Example
}
