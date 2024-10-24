package com.wikicoding.schools.controllers

import com.wikicoding.schools.dtos.SchoolDto
import com.wikicoding.schools.persistence.SchoolDataModel
import com.wikicoding.schools.persistence.SchoolRepository
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestBody
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Controller
class SchoolsGraphqlController(private val schoolRepository: SchoolRepository) {

    @QueryMapping
    fun getSchools(): Mono<List<SchoolDataModel>> {
        return schoolRepository.findAll().collectList()
    }

    @MutationMapping
    fun addSchool(@RequestBody schoolDto: SchoolDto): Mono<SchoolDataModel> {
        val schoolDm = SchoolDataModel(schoolName = schoolDto.schoolName)

        return schoolRepository.save(schoolDm)
    }
}