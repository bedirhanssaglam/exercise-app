package com.example.exerciseapp.model

class ExerciseModel(
    private var id: Int,
    private var name: String,
    private var image: Int,
    private var isCompleted: Boolean = false,
    private var isSelected: Boolean = false,
) {
    fun getId(): Int = id

    fun getName(): String = name

    fun getImage(): Int = image

    fun getIsCompleted(): Boolean = isCompleted

    fun setIsCompleted(isCompleted: Boolean) {
        this.isCompleted = isCompleted
    }

    fun getIsSelected(): Boolean = isSelected

    fun setIsSelected(isSelected: Boolean) {
        this.isSelected = isSelected
    }
}