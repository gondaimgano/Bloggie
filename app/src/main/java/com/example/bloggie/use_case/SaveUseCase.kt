package com.example.bloggie.use_case

import com.example.bloggie.model.Post

interface SaveUseCase {
    suspend operator fun invoke(id: Post):Boolean
}