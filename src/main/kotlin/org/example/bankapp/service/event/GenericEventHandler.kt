package org.example.bankapp.service.event

import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import java.util.concurrent.atomic.AtomicBoolean

abstract class GenericEventHandler<T : Any> {

    private val log = LoggerFactory.getLogger(javaClass)

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handleEvent(event: Any) {
        if (isEventTypeSupported(event)) {
            @Suppress("UNCHECKED_CAST")
            handle(event as T)
        }
    }

    private fun handle(event: T) {
        val isCancelled = AtomicBoolean(false)
        val future = executeWithTimeout(event, isCancelled)
        try {
            future.get(5, TimeUnit.SECONDS)
        } catch (e: TimeoutException) {
            handleException(event, isCancelled, future, e)
        } catch (e: Exception) {
            handleException(event, isCancelled, future, e)
        }
    }

    @Async
    open fun executeWithTimeout(event: T, cancellationToken: AtomicBoolean): CompletableFuture<Unit> {
        return CompletableFuture.supplyAsync {
//            Thread.sleep(6000)
            if (!cancellationToken.get()) {
                executeEvent(event)
            }
        }
    }

    private fun handleException(event: T, isCancelled: AtomicBoolean, future: CompletableFuture<Unit>, e: Exception) {
        isCancelled.set(true)
        future.cancel(true)
        onFailure(event)
        log.error("Exception occurred: ", e)
        throw e
    }

    protected abstract fun executeEvent(event: T)
    protected abstract fun onFailure(event: T)
    protected abstract fun isEventTypeSupported(event: Any): Boolean
}