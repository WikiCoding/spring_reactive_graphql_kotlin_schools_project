package com.wikicoding.schools.domain.usecases

import com.wikicoding.schools.application.schools.abstractions.CommandHandler
import com.wikicoding.schools.application.schools.commands.delete_school.DeleteSchoolCommand
import com.wikicoding.schools.application.schools.commands.delete_school.SchoolDeletedEvent

interface DeleteSchoolUseCase: CommandHandler<DeleteSchoolCommand, SchoolDeletedEvent> {
}