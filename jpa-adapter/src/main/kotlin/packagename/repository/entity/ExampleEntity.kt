package packagename.repository.entity

import org.hibernate.envers.Audited
import packagename.domain.model.Example
import jakarta.persistence.*

@Table(name = "T_EXAMPLE")
@Entity
@SequenceGenerator(name = "SEQ_T_EXAMPLE", initialValue = 1, allocationSize = 100)
@Audited
data class ExampleEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_EXAMPLE")
    @SequenceGenerator(name = "SEQ_T_EXAMPLE", sequenceName = "SEQ_T_EXAMPLE", allocationSize = 1, initialValue = 1)
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
