package com.wikicoding.schools.persistence

import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface SchoolRepository : ReactiveCrudRepository<SchoolDataModel, Int> {
}