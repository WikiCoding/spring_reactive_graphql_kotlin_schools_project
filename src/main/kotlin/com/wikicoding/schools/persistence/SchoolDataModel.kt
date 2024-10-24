package com.wikicoding.schools.persistence

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table


@Table(name = "schools")
data class SchoolDataModel(
    @Id
    var id: Int = 0,
    var schoolName: String = ""
)
