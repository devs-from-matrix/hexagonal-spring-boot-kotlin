package packagename.domain.port

import packagename.domain.model.ExampleInfo

interface RequestExample {
  fun getExamples(): ExampleInfo
}
