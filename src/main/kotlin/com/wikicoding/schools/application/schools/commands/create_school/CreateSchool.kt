package com.wikicoding.schools.application.schools.commands.create_school

import com.wikicoding.schools.domain.events.AggregateEvent
import com.wikicoding.schools.application.schools.abstractions.CommandHandler
import com.wikicoding.schools.application.schools.abstractions.Request
import com.wikicoding.schools.domain.usecases.CreateSchoolUseCase
import com.wikicoding.schools.persistence.SchoolDataModel
import com.wikicoding.schools.persistence.SchoolRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

data class CreateSchoolCommand(val schoolName: String): Request
data class SchoolCreatedEvent(val schoolId: Int, val schoolName: String): AggregateEvent


@RestController
@RequestMapping("api/v1/schools")
class CreateSchoolRestController(private val commandHandler: CreateSchoolUseCase) {
    @PostMapping
    fun createSchool(@RequestBody command: CreateSchoolCommand): ResponseEntity<Mono<SchoolCreatedEvent>> {
        return try {
            ResponseEntity.status(HttpStatus.CREATED).body(commandHandler.handle(command))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Mono.empty())
        }
    }
}

@Service
class CreateSchoolUseCaseCommandHandler(private val schoolRepository: SchoolRepository)
    : CommandHandler<CreateSchoolCommand, SchoolCreatedEvent>, CreateSchoolUseCase {

        override fun handle(command: CreateSchoolCommand): Mono<SchoolCreatedEvent> {
            if (command.schoolName.isEmpty()) throw IllegalArgumentException("School name must not be null or empty")

            val schoolDm = SchoolDataModel(schoolName = command.schoolName)

            return schoolRepository.save(schoolDm).map { saved ->
                SchoolCreatedEvent(saved.id, schoolDm.schoolName)
            }
        }
}