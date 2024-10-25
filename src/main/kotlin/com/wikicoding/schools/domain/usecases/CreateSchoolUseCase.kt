package com.wikicoding.schools.domain.usecases

import com.wikicoding.schools.application.schools.abstractions.CommandHandler
import com.wikicoding.schools.application.schools.commands.create_school.CreateSchoolCommand
import com.wikicoding.schools.application.schools.commands.create_school.SchoolCreatedEvent

interface CreateSchoolUseCase : CommandHandler<CreateSchoolCommand, SchoolCreatedEvent> {
}