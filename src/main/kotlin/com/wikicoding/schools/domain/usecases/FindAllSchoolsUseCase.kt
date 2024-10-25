package com.wikicoding.schools.domain.usecases

import com.wikicoding.schools.application.schools.abstractions.QueryHandler
import com.wikicoding.schools.application.schools.queries.find_schools.FindAllSchoolsQuery
import com.wikicoding.schools.persistence.SchoolDataModel
import reactor.core.publisher.Flux

interface FindAllSchoolsUseCase: QueryHandler<FindAllSchoolsQuery, Flux<SchoolDataModel>> {
}