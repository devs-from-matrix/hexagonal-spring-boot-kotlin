package packagename.repository.entity

import jakarta.persistence.*

@Table(name = "REVISION_INFO", schema = "EXAMPLE_AUDIT")
@Entity
@SequenceGenerator(
    schema = "EXAMPLE_AUDIT", name = "SEQ_REVISION_INFO",
    sequenceName = "EXAMPLE_AUDIT.SEQ_REVISION_INFO", allocationSize = 1
)
data class EnversRevisionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_REVISION_INFO")
    @Column(name = "REV")
    private val rev: Long? = null,
    @Column(name = "TIMESTAMP")
    private val code: Long? = null
)