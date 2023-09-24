package packagename.boot.blocking.test.cucumber

import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import org.assertj.core.api.Assertions.assertThat
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import packagename.domain.model.Example
import packagename.repository.dao.ExampleDao
import packagename.repository.entity.ExampleEntity
import packagename.rest.exception.ExampleExceptionResponse
import packagename.rest.representation.ExampleInfo


class ExampleStepDef(restTemplate: TestRestTemplate, exampleDao: ExampleDao) : En {

  companion object {

    private const val LOCALHOST = "http://localhost:"
    private const val API_URI = "/api/v1/examples"
  }

  @LocalServerPort
  private val port: Int = 0
  private lateinit var responseEntity: ResponseEntity<*>

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
      responseEntity = restTemplate.getForEntity(url, ExampleInfo::class.java)
    }

    When("user requests for examples by code {string}") { code: String? ->
      val url = "$LOCALHOST$port$API_URI/$code"
      responseEntity = restTemplate.getForEntity(url, Example::class.java)
    }

    When("user requests for examples by id {string} that does not exists") { id: String? ->
      val url = "$LOCALHOST$port$API_URI/$id"
      responseEntity = restTemplate.getForEntity(url, ExampleExceptionResponse::class.java)
    }

    Then("the user gets an exception {string}") { exception: String? ->
      assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
      val body = responseEntity.body
      assertThat(body).isInstanceOf(ExampleExceptionResponse::class.java)
      when (body) {
        is ExampleExceptionResponse -> assertThat(body.message).isEqualTo(exception)
      }
    }

    Then("the user gets the following examples") { dataTable: DataTable ->
      val expectedExamples = dataTable.asList(Example::class.java)
      assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
      val body = responseEntity.body
      assertThat(body).isNotNull
      when (body) {
        is ExampleInfo -> assertThat(body.examples).isNotEmpty.extracting("description")
          .containsAll(expectedExamples.map { it.description })

        is Example -> assertThat(body).isNotNull.extracting("description")
          .isEqualTo(expectedExamples.first().description)
      }
    }
  }
}


