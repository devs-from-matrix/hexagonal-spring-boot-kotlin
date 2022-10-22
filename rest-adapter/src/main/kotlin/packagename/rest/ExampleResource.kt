package packagename.rest

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import packagename.domain.model.Example
import packagename.domain.model.ExampleInfo
import packagename.domain.port.RequestExample
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/examples")
class ExampleResource(private val requestExample: RequestExample) {

  @GetMapping
  fun getExamples(): ResponseEntity<Mono<ExampleInfo>> {
    return ResponseEntity.ok(Mono.just(requestExample.getExamples()))
  }

  @GetMapping(path = ["/{code}"])
  fun getExampleByCode(@PathVariable code: Long): ResponseEntity<Mono<Example>> {
    return ResponseEntity.ok(Mono.just(requestExample.getExampleByCode(code)))
  }
}
