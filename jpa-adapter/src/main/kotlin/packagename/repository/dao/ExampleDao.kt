package packagename.repository.dao

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import packagename.repository.entity.ExampleEntity
import java.util.*

@Repository
interface ExampleDao : JpaRepository<ExampleEntity, Long> {
  fun findByCode(code: Long): Optional<ExampleEntity>
}
