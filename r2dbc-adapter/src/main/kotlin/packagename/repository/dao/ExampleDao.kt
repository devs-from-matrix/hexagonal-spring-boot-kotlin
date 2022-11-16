package packagename.repository.dao

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import packagename.repository.entity.ExampleEntity

@Repository
interface ExampleDao : ReactiveCrudRepository<ExampleEntity, Long>
