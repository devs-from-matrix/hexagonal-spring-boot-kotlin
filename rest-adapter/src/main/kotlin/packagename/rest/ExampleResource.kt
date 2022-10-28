package packagename.rest

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import packagename.domain.model.Example
import packagename.rest.representation.ExampleInfo
import packagename.domain.port.RequestExample
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/examples")
class ExampleResource(private val requestExample: RequestExample) {

  @GetMapping
  fun getExamples(): Mono<ResponseEntity<ExampleInfo>> {
    return requestExample.getExamples()
        .collectList()
        .map { ResponseEntity.ok(ExampleInfo(it)) }
  }

  @GetMapping(path = ["/{code}"])
  fun getExampleByCode(@PathVariable code: Long): Mono<ResponseEntity<Example>> {
    return requestExample.getExampleByCode(code)
        .map {
          ResponseEntity.ok(it)
        }
  }
}
