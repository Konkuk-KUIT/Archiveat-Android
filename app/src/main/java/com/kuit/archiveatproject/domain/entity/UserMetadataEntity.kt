package com.kuit.archiveatproject.domain.entity

data class UserMetadataResult(
    val employmentTypes: List<String>,
    val availabilityOptions: List<String>,
    val categories: List<UserMetadataCategory>,
)

data class UserMetadataCategory(
    val id: Long,
    val name: String,
    val topics: List<UserMetadataTopic>,
)

data class UserMetadataTopic(
    val id: Long,
    val name: String,
)