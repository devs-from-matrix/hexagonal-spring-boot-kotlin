package packagename.rest.representation

import packagename.domain.model.Example

data class ExampleInfo(val examples: List<Example> = emptyList())
