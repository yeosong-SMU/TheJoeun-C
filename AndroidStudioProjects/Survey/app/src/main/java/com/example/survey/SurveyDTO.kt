package com.example.survey

data class SurveyDTO (
    var surveyIdx: Int = 0,
    var question: String? = null,
    var ans1: String? = null,
    var ans2: String? = null,
    var ans3: String? = null,
    var ans4: String? = null,
    var status: String? = null
)