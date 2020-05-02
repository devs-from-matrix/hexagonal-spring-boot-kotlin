package packagename.repository.entity

import packagename.domain.model.Example
import javax.persistence.*

@Table(name = "T_EXAMPLE")
@Entity
@SequenceGenerator(name = "SEQ_T_EXAMPLE", initialValue = 1, allocationSize = 100)
data class ExampleEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_EXAMPLE")
    @Column(name = "TECH_ID")
    private val techId: Long? = null,
    @Column(name = "CODE")
    private val code: Long? = null,
    @Column(name = "DESCRIPTION")
    private val description: String) {
  fun toModel(): Example {
    return Example(code, description)
  }
}
