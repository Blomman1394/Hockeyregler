package com.example.hockeyregler.model

/**
 * Enkel modell för kapitel i MVP: titel samt start/end-sidor (0-baserat).
 */

data class Chapter(
    val id: Int,
    val title: String,
    val startPage: Int,
    val endPage: Int
)

