package com.wikicoding.schools.domain.usecases

import com.wikicoding.schools.application.schools.abstractions.CommandHandler
import com.wikicoding.schools.application.schools.commands.update_school.SchoolUpdatedEvent
import com.wikicoding.schools.application.schools.commands.update_school.UpdateSchoolCommand

interface UpdateSchoolUseCase : CommandHandler<UpdateSchoolCommand, SchoolUpdatedEvent> {
}