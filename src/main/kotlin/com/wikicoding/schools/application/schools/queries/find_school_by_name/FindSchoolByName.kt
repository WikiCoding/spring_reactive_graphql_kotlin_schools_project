package com.wikicoding.schools.application.schools.queries.find_school_by_name

import com.wikicoding.schools.application.schools.abstractions.QueryHandler
import com.wikicoding.schools.application.schools.abstractions.Request
import com.wikicoding.schools.domain.usecases.FindSchoolByNameUseCase
import com.wikicoding.schools.persistence.SchoolDataModel
import com.wikicoding.schools.persistence.SchoolRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

data class FindSchoolByNameQuery(val name: String): Request

@RestController
@RequestMapping("api/v1/schools", params = ["schoolName"])
class FindSchoolByNameController(private val findSchoolByNameQueryHandler: FindSchoolByNameUseCase) {
    @GetMapping("{schoolName}")
    fun findSchoolByName(@PathVariable(name = "schoolName") schoolName: String): ResponseEntity<Mono<SchoolDataModel>> {
        val findSchoolByNameQuery = FindSchoolByNameQuery(schoolName)

        return try {
            ResponseEntity.status(HttpStatus.OK).body(findSchoolByNameQueryHandler.handle(findSchoolByNameQuery))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Mono.empty())
        }
    }
}

@Service
class FindSchoolByNameQueryHandler(private val schoolRepository: SchoolRepository)
    : QueryHandler<FindSchoolByNameQuery, Mono<SchoolDataModel>>, FindSchoolByNameUseCase {
    override fun handle(query: FindSchoolByNameQuery): Mono<SchoolDataModel> {
        if (query.name.isEmpty()) throw IllegalArgumentException("Name must not be empty")

        return schoolRepository.findBySchoolName(query.name)
    }
}