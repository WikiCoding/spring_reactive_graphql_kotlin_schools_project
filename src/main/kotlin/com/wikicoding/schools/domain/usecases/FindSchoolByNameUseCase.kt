package com.wikicoding.schools.domain.usecases

import com.wikicoding.schools.application.schools.abstractions.QueryHandler
import com.wikicoding.schools.application.schools.queries.find_school_by_name.FindSchoolByNameQuery
import com.wikicoding.schools.persistence.SchoolDataModel
import reactor.core.publisher.Mono

interface FindSchoolByNameUseCase: QueryHandler<FindSchoolByNameQuery, Mono<SchoolDataModel>> {
}