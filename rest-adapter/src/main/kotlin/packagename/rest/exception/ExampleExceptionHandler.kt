package packagename.rest.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest
import packagename.domain.exception.ExampleNotFoundException
import java.lang.Exception

@RestControllerAdvice(basePackages = ["packagename"])
class ExampleExceptionHandler {

  @ExceptionHandler(value = [ExampleNotFoundException::class])
  fun handleExampleNotFoundException(exception: Exception, request: WebRequest): ResponseEntity<ExampleExceptionResponse> {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExampleExceptionResponse(exception.message, (request as ServletWebRequest).request.requestURI))
  }
}
