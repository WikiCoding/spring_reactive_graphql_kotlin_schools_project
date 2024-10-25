package com.wikicoding.schools.application.schools.commands.delete_school

import com.wikicoding.schools.domain.events.AggregateEvent
import com.wikicoding.schools.application.schools.abstractions.CommandHandler
import com.wikicoding.schools.application.schools.abstractions.Request
import com.wikicoding.schools.domain.usecases.DeleteSchoolUseCase
import com.wikicoding.schools.persistence.SchoolRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

data class DeleteSchoolCommand(val schoolName: String) : Request

data class SchoolDeletedEvent(val schoolName: String): AggregateEvent

@RestController
@RequestMapping("api/v1/schools")
class DeleteSchoolRestController(private val commandHandler: DeleteSchoolUseCase) {

    @DeleteMapping("{schoolName}")
    fun deleteSchool(@PathVariable(name = "schoolName") schoolName: String): ResponseEntity<Mono<SchoolDeletedEvent>> {
        val command = DeleteSchoolCommand(schoolName)

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
class DeleteSchoolCommandHandler(private val schoolRepository: SchoolRepository)
    : CommandHandler<DeleteSchoolCommand, SchoolDeletedEvent>, DeleteSchoolUseCase {

    override fun handle(command: DeleteSchoolCommand): Mono<SchoolDeletedEvent> {
        if (command.schoolName.isEmpty()) throw IllegalArgumentException("School name must not be null or empty")

        return schoolRepository.findBySchoolName(command.schoolName)
            .switchIfEmpty(Mono.error(RuntimeException("School not found with name: ${command.schoolName}")))
            .flatMap { schoolDm ->
                schoolRepository.delete(schoolDm).thenReturn(SchoolDeletedEvent(command.schoolName))
            }
    }
}