package deepboot.config

import org.springframework.context.annotation.DeferredImportSelector
import org.springframework.core.type.AnnotationMetadata
import org.springframework.util.MultiValueMap

class MyConfigurationPropertiesImportSelector : DeferredImportSelector {
    override fun selectImports(importingClassMetadata: AnnotationMetadata): Array<String> {
        val attr: MultiValueMap<String, Any>? =
            importingClassMetadata.getAllAnnotationAttributes(EnableMyConfigurationProperties::class.java.name)

        val first: Class<*> = attr!!.getFirst("value") as Class<*>
        println(first)
        return arrayOf(first.name)
    }
}
