package packagename.domain.port

import packagename.domain.model.Example
import java.util.*

interface ObtainExample {

  fun getAllExamples(): List<Example> {
    val example = Example(1L, "If you could read a leaf or tree\r\nyoud have no need of books.\r\n-- Alistair Cockburn (1987)")
    return listOf(example)
  }

  fun getExampleByCode(code: Long): Optional<Example> {
    return Optional.of(Example(1L, "If you could read a leaf or tree\r\nyoud have no need of books.\r\n-- Alistair Cockburn (1987)"))
  }
}
