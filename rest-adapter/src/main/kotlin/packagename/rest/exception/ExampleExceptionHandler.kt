package packagename.rest.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import packagename.domain.exception.ExampleNotFoundException
import reactor.core.publisher.Mono

@RestControllerAdvice(basePackages = ["packagename"])
class ExampleExceptionHandler {

    @ExceptionHandler(value = [ExampleNotFoundException::class])
    fun handleExampleNotFoundException(exception: Exception, request: ServerHttpRequest): ResponseEntity<Mono<ExampleExceptionResponse>> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Mono.just(ExampleExceptionResponse(exception.message, request.uri.path)))
    }
}
