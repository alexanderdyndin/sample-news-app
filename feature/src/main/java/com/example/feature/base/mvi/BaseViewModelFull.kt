package com.example.feature.base.mvi

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature.base.mvi.impl.SimpleActionProducer
import com.example.feature.base.mvi.impl.SimpleStateStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

abstract class BaseViewModelFull<TState, TMutator : BaseMutator<TState>, TAction>(
    initialState: TState,
    private val mutatorFactory: () -> TMutator,
    private val stateStore: SimpleStateStore<TState, TMutator>
    = SimpleStateStore(initialState,  mutatorFactory),
    private val actionProducer: SimpleActionProducer<TAction>
    = SimpleActionProducer()
): ViewModel(),
    StateStore<TState, TMutator> by stateStore,
    ActionProducer<TAction> by actionProducer,
    KoinComponent {

    init {
        viewModelScope.launch(Dispatchers.Default) {
            state.collect {
                Log.d("STATE", it.toString())
            }
        }
    }
}