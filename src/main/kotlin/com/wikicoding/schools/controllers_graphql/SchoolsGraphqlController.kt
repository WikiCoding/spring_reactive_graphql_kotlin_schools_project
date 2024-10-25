package com.wikicoding.schools.controllers_graphql

import com.wikicoding.schools.application.schools.commands.create_school.CreateSchoolCommand
import com.wikicoding.schools.application.schools.commands.create_school.CreateSchoolUseCaseCommandHandler
import com.wikicoding.schools.application.schools.commands.create_school.SchoolCreatedEvent
import com.wikicoding.schools.application.schools.commands.delete_school.DeleteSchoolCommand
import com.wikicoding.schools.application.schools.commands.delete_school.SchoolDeletedEvent
import com.wikicoding.schools.application.schools.commands.update_school.SchoolUpdatedEvent
import com.wikicoding.schools.application.schools.commands.update_school.UpdateSchoolCommand
import com.wikicoding.schools.application.schools.queries.find_school_by_name.FindSchoolByNameQuery
import com.wikicoding.schools.application.schools.queries.find_schools.FindAllSchoolsQuery
import com.wikicoding.schools.application.schools.queries.find_schools.FindSchoolsQueryHandler
import com.wikicoding.schools.domain.usecases.*
import com.wikicoding.schools.persistence.SchoolDataModel
import com.wikicoding.schools.persistence.SchoolRepository
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Mono

@Controller
class SchoolsGraphqlController(
    private val findAllSchoolsQueryHandler: FindAllSchoolsUseCase,
    private val findSchoolByNameQueryHandler: FindSchoolByNameUseCase,
    private val createSchoolCommandHandler: CreateSchoolUseCase,
    private val updateSchoolCommandHandler: UpdateSchoolUseCase,
    private val deleteSchoolCommandHandler: DeleteSchoolUseCase,
) {

    @QueryMapping
    fun getSchools(): Mono<List<SchoolDataModel>> {
        val query = FindAllSchoolsQuery()
        return findAllSchoolsQueryHandler.handle(query).collectList()
    }

    @QueryMapping
    fun getSchoolByName(@Argument schoolName: String): Mono<SchoolDataModel> {
        val query = FindSchoolByNameQuery(schoolName)
        return findSchoolByNameQueryHandler.handle(query)
    }

    @MutationMapping
    fun addSchool(@Argument command: CreateSchoolCommand): Mono<SchoolCreatedEvent> {
        return createSchoolCommandHandler.handle(command)
    }

    @MutationMapping
    fun updateSchool(@Argument command: UpdateSchoolCommand): Mono<SchoolUpdatedEvent> {
        return updateSchoolCommandHandler.handle(command)
    }

    @MutationMapping
    fun deleteSchool(@Argument command: DeleteSchoolCommand): Mono<SchoolDeletedEvent> {
        return deleteSchoolCommandHandler.handle(command)
    }
}