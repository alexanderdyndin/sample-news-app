package com.example.feature

import com.example.feature.ui.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featuresModule = module {
    viewModel { NewsViewModel(get()) }
}