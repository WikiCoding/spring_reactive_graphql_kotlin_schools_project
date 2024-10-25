package com.wikicoding.schools.persistence

import com.wikicoding.schools.domain.repository.Repository
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface SchoolRepository : ReactiveCrudRepository<SchoolDataModel, Int>, Repository {
    fun findBySchoolName(schoolName: String): Mono<SchoolDataModel>
}