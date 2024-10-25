package com.wikicoding.schools.application.schools.abstractions

import com.wikicoding.schools.domain.events.AggregateEvent
import reactor.core.publisher.Mono

interface CommandHandler<T : Request, K: AggregateEvent> {
    fun handle(command: T): Mono<K>
}