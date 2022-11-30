package packagename.nonblocking.test.cucumber

import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import org.assertj.core.api.Assertions.assertThat
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec
import packagename.domain.model.Example
import packagename.repository.dao.ExampleDao
import packagename.repository.entity.ExampleEntity
import packagename.rest.exception.ExampleExceptionResponse
import packagename.rest.representation.ExampleInfo


class ExampleStepDef(webTestClient: WebTestClient, exampleDao: ExampleDao) : En {

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
    DataTableType { row: Map<String, String> ->
      ExampleEntity(
        code = row["code"].toString().toLong(), description = row["description"].toString()
      )
    }

    Before { _ -> exampleDao.deleteAll() }
    After { _ -> exampleDao.deleteAll() }

    Given("the following examples exists in the library") { dataTable: DataTable ->
      val examples = dataTable.asList(ExampleEntity::class.java)
      exampleDao.saveAll(examples)
    }

    When("user requests for all examples") {
      val url = "$LOCALHOST$port$API_URI"
      responseSpec = webTestClient.get().uri(url).exchange()
    }

    When("user requests for examples by code {string}") { code: String? ->
      val url = "$LOCALHOST$port$API_URI/$code"
      responseSpec = webTestClient.get().uri(url).exchange()
//      responseEntity = restTemplate.getForEntity(url, Example::class.java)
    }

    When("user requests for examples by id {string} that does not exists") { id: String? ->
      val url = "$LOCALHOST$port$API_URI/$id"
      responseSpec = webTestClient.get().uri(url).exchange()
//      responseEntity = restTemplate.getForEntity(url, ExampleExceptionResponse::class.java)
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
            .isEqualTo(expectedExamples.map { it.description })
        }
    }
  }
}


