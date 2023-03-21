package deepboot.deep.annotation

import org.springframework.context.annotation.Condition
import org.springframework.context.annotation.ConditionContext
import org.springframework.context.annotation.Conditional
import org.springframework.core.type.AnnotatedTypeMetadata
import org.springframework.util.ClassUtils

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Conditional(MyOnClassCondition::class)
annotation class ConditionalMyOnClass(
    val value: String,
) {

}

class MyOnClassCondition : Condition {
    override fun matches(context: ConditionContext, metadata: AnnotatedTypeMetadata): Boolean {
        val annotationAttributes = metadata.getAnnotationAttributes(ConditionalMyOnClass::class.java.name)
        val value = annotationAttributes?.get("value") as String
        return ClassUtils.isPresent(value, context.classLoader)
    }
}


