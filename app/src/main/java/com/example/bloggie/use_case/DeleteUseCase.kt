package com.example.bloggie.use_case

interface DeleteUseCase<T> {
    suspend operator fun invoke(favorite:T)
}