package deepboot.deep.config.autoconfig

import org.springframework.context.annotation.DeferredImportSelector
import org.springframework.core.type.AnnotationMetadata

class MyAutoConfigImportSelector : DeferredImportSelector {
    override fun selectImports(importingClassMetadata: AnnotationMetadata): Array<String> {
        return arrayOf(
            "deepboot.deep.config.autoconfig.DispatcherServletConfig",
            "deepboot.deep.config.autoconfig.TomcatWebServerConfig"
        )
    }
}