package com.wikicoding.schools.application.schools.abstractions

interface QueryHandler<T : Request, K> {
    fun handle(query: T) : K
}