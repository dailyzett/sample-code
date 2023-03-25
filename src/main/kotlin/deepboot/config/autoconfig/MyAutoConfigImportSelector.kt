package deepboot.config.autoconfig

import deepboot.config.MyAutoConfiguration
import org.springframework.boot.context.annotation.ImportCandidates
import org.springframework.context.annotation.DeferredImportSelector
import org.springframework.core.type.AnnotationMetadata
import java.util.stream.StreamSupport

class MyAutoConfigImportSelector(
    private val classLoader: ClassLoader,
) : DeferredImportSelector {
    override fun selectImports(importingClassMetadata: AnnotationMetadata): Array<String> {
        val candidates = ImportCandidates.load(MyAutoConfiguration::class.java, classLoader)
        return StreamSupport.stream(candidates.spliterator(), false).toArray { size ->
            arrayOfNulls<String>(size)
        }
    }
}