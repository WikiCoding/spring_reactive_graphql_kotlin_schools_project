package com.wikicoding.schools.domain.repository

interface Repository {
    // not using since here it's not correct to have framework dependencies and I'm not doing real DDD
    // so I'm leaving it as a market interface
//    fun findBySchoolName(schoolName: String): Mono<SchoolDataModel>
}