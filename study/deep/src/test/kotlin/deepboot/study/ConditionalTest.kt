package deepboot.study

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.runner.ApplicationContextRunner
import org.springframework.context.annotation.*
import org.springframework.core.type.AnnotatedTypeMetadata

class ConditionalTest {

    @Test
    fun test() {
        //true
        val runner = ApplicationContextRunner()
        runner.withUserConfiguration(Config1::class.java)
            .run { context ->
                assertThat(context).hasSingleBean(MyBean::class.java)
                assertThat(context).hasSingleBean(Config1::class.java)
            }

        //false
        runner.withUserConfiguration(Config2::class.java)
            .run { context ->
                assertThat(context).doesNotHaveBean(MyBean::class.java)
                assertThat(context).doesNotHaveBean(Config2::class.java)
            }
    }

    companion object {

        @Retention(AnnotationRetention.RUNTIME)
        @Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE)
        @Conditional(BooleanCondition::class)
        annotation class BooleanConditional(val value: Boolean)

        @Configuration
        @BooleanConditional(true)
        class Config1 {
            @Bean
            fun myBean() = MyBean()
        }

        @Configuration
        @BooleanConditional(false)
        class Config2 {
            @Bean
            fun myBean() = MyBean()
        }

        class MyBean {}

        class BooleanCondition : Condition {
            override fun matches(context: ConditionContext, metadata: AnnotatedTypeMetadata): Boolean {
                val annotationAttributes = metadata.getAnnotationAttributes(BooleanConditional::class.java.name)
                return annotationAttributes?.get("value") as Boolean
            }
        }
    }
}