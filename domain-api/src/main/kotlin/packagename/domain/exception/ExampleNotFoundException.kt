package packagename.domain.exception

class ExampleNotFoundException(id: Long) : RuntimeException("Example with code: [$id] does not exists")

