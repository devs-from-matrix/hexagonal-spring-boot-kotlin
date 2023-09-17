package packagename.nonblocking.test.cucumber

import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import org.assertj.core.api.Assertions.assertThat
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec
import packagename.domain.model.Example
import packagename.rest.exception.ExampleExceptionResponse
import packagename.rest.representation.ExampleInfo
import reactor.test.StepVerifier
import java.util.function.Consumer


class ExampleStepDef(webTestClient: WebTestClient, client: DatabaseClient) : En {

  companion object {

    private const val LOCALHOST = "http://localhost:"
    private const val API_URI = "/api/v1/examples"
  }

  @LocalServerPort
  private val port: Int = 0
  private lateinit var responseSpec: ResponseSpec

  init {

    DataTableType { row: Map<String, String> ->
      Example(
        row["code"].toString().toLong(),
        row["description"].toString()
      )
    }

    Given("the following examples exists in the library") { dataTable: DataTable ->
      val examples = dataTable.asList(Example::class.java)
      val statements: MutableList<String> = mutableListOf();
      examples.forEach { example ->
        statements += "INSERT INTO EXAMPLE.T_EXAMPLE(CODE,DESCRIPTION) VALUES(" + example.code + ", '" + example.description + "')"
      }
      statements.forEach(Consumer { it: String ->
        client.sql(it).fetch().rowsUpdated().`as`(StepVerifier::create).expectNextCount(1)
          .verifyComplete()
      })
    }

    When("user requests for all examples") {
      val url = "$LOCALHOST$port$API_URI"
      responseSpec = webTestClient.get().uri(url).exchange()
    }

    When("user requests for examples by code {string}") { code: String? ->
      val url = "$LOCALHOST$port$API_URI/$code"
      responseSpec = webTestClient.get().uri(url).exchange()
    }

    When("user requests for examples by id {string} that does not exists") { id: String? ->
      val url = "$LOCALHOST$port$API_URI/$id"
      responseSpec = webTestClient.get().uri(url).exchange()
    }

    Then("the user gets an exception {string}") { exception: String? ->
      responseSpec.expectStatus().isNotFound
      responseSpec.expectBody(ExampleExceptionResponse::class.java)
        .consumeWith {
          assertThat(it.responseBody.message).isNotNull.isEqualTo(exception)
        }
    }

    Then("the user gets the following examples") { dataTable: DataTable ->
      val expectedExamples = dataTable.asList(Example::class.java)

      responseSpec.expectStatus().isOk
        .expectBody(ExampleInfo::class.java)
        .consumeWith {

          assertThat(it.responseBody.examples).isNotNull.extracting("description")
            .containsAll(expectedExamples.map { it.description })
        }
    }

    Then("the user gets the following example") { dataTable: DataTable ->
      val expectedExamples = dataTable.asList(Example::class.java)

      responseSpec.expectStatus().isOk
        .expectBody(Example::class.java)
        .consumeWith {

          assertThat(it.responseBody).isNotNull.extracting("description")
            .isIn(expectedExamples.map { it.description })
        }
    }
  }
}


