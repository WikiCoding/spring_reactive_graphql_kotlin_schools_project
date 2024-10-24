package com.wikicoding.schools.controllers

import com.wikicoding.schools.dtos.SchoolDto
import com.wikicoding.schools.persistence.SchoolDataModel
import com.wikicoding.schools.persistence.SchoolRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@RestController
@RequestMapping("api/v1/schools")
class SchoolsRestController(private val schoolRepository: SchoolRepository) {

    @GetMapping
    fun getSchools() : ResponseEntity<Flux<SchoolDataModel>> {
        return ResponseEntity.status(HttpStatus.OK).body(schoolRepository.findAll())
    }

    @PostMapping
    fun addSchool(@RequestBody schoolDto: SchoolDto): ResponseEntity<Mono<SchoolDataModel>> {
        val schoolDm = SchoolDataModel(schoolName = schoolDto.schoolName)
        val saved = schoolRepository.save(schoolDm)

        return ResponseEntity.status(HttpStatus.CREATED).body(saved)
    }
}