package com.example.bloggie.use_case

interface FetchSingleUseCase<I> {
    suspend operator fun invoke(item: I): I
}