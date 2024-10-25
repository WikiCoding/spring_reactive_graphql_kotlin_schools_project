package com.wikicoding.schools.application.schools.commands.update_school

import com.wikicoding.schools.application.schools.abstractions.CommandHandler
import com.wikicoding.schools.application.schools.abstractions.Request
import com.wikicoding.schools.domain.events.AggregateEvent
import com.wikicoding.schools.domain.usecases.UpdateSchoolUseCase
import com.wikicoding.schools.persistence.SchoolRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

data class UpdateSchoolCommand(val previousSchoolName: String, val newSchoolName: String) : Request

data class SchoolUpdatedEvent(val schoolId: Int, val schoolName: String): AggregateEvent

@RestController
@RequestMapping("api/v1/schools")
class UpdateSchoolRestController(private val commandHandler: UpdateSchoolUseCase) {

    @PutMapping
    fun updateSchool(@RequestBody command: UpdateSchoolCommand)
    : ResponseEntity<Mono<SchoolUpdatedEvent>> {
        return try {
            ResponseEntity.status(HttpStatus.OK).body(commandHandler.handle(command))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Mono.empty())
        } catch (e: RuntimeException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(Mono.empty())
        }
    }
}

@Service
class UpdateSchoolCommandHandler(private val schoolRepository: SchoolRepository)
    : CommandHandler<UpdateSchoolCommand, SchoolUpdatedEvent>, UpdateSchoolUseCase {

        override fun handle(command: UpdateSchoolCommand): Mono<SchoolUpdatedEvent> {
            if (command.previousSchoolName.isEmpty()) throw IllegalArgumentException("School name must not be null or empty")

            return schoolRepository.findBySchoolName(command.previousSchoolName)
                .switchIfEmpty(Mono.error(RuntimeException("School not found with name: ${command.previousSchoolName}")))
                .flatMap { schoolDm ->
                    schoolDm.schoolName = command.newSchoolName
                    schoolRepository.save(schoolDm)
                }
                .map { saved ->
                    SchoolUpdatedEvent(schoolId = saved.id, schoolName = saved.schoolName)
                }
        }
}