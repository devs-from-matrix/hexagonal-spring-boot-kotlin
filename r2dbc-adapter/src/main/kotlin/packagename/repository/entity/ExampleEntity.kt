package packagename.repository.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import packagename.domain.model.Example

@Table(name = "EXAMPLE.T_EXAMPLE")
data class ExampleEntity(
  @Id
  @Column(value = "TECH_ID")
  private val techId: Long? = null,
  @Column(value = "CODE")
  private val code: Long? = null,
  @Column(value = "DESCRIPTION")
  private val description: String
) {

  fun toModel(): Example {
    return Example(code, description)
  }
}
