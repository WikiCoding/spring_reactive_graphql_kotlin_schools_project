package com.wikicoding.schools.application.schools.queries.find_schools

import com.wikicoding.schools.application.schools.abstractions.QueryHandler
import com.wikicoding.schools.application.schools.abstractions.Request
import com.wikicoding.schools.domain.usecases.FindAllSchoolsUseCase
import com.wikicoding.schools.persistence.SchoolDataModel
import com.wikicoding.schools.persistence.SchoolRepository
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import java.time.Duration

class FindAllSchoolsQuery: Request

@RestController
@RequestMapping("api/v1/schools")
class FindAllSchoolsController(private val findAllSchoolsUseCase: FindAllSchoolsUseCase) {
    @GetMapping(produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun findAllSchools(): ResponseEntity<Flux<SchoolDataModel>> {
        val schoolDm: Flux<SchoolDataModel> = findAllSchoolsUseCase.handle(FindAllSchoolsQuery())

        return ResponseEntity.ok(schoolDm)
    }
}

@Service
class FindSchoolsQueryHandler(private val schoolRepository: SchoolRepository)
    : QueryHandler<FindAllSchoolsQuery, Flux<SchoolDataModel>>, FindAllSchoolsUseCase {
    override fun handle(query: FindAllSchoolsQuery): Flux<SchoolDataModel> {
        // if we want to add pagination or something like that then we can use with query parameter. For the moment is not needed
        return schoolRepository.findAll().delayElements(Duration.ofSeconds(2))
    }
}