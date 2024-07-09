package com.example.bloggie.use_case



interface FetchUseCase<T> {
    suspend operator  fun invoke(): T
}