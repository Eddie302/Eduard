package com.example.amphsesviewer.feature

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.*
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.util.concurrent.atomic.AtomicBoolean

interface ViewState
interface ViewEvent
interface ViewAction

open class SingleLiveData<T> : MutableLiveData<T>() {

    private val pending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {

        if (hasActiveObservers()) {
            Log.w(SingleLiveData::class.java.simpleName, "Multiple observers registered but only one will be notified of changes.")
        }

        // Observe the internal MutableLiveData
        super.observe(owner, Observer { t ->
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        })
    }

    @MainThread
    override fun setValue(value: T?) {
        pending.set(true)
        super.setValue(value)
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    fun call() {
        value = null
    }
}

abstract class ViewModelBase<State: ViewState, Action : ViewAction, Event : ViewEvent>(
    initState: State
) : ViewModel() {

    protected val compositeDisposable = CompositeDisposable()

    private val _viewState = MutableLiveData<State>()
    val viewState: LiveData<State> = _viewState

    private val _action =
        SingleLiveData<Action>()
    val action: LiveData<Action> = _action

    init {
        _viewState.value = initState
    }

    protected fun sendAction(action: Action) { _action.value = action }
    protected fun postAction(action: Action) { _action.postValue(action) }

    protected fun sendNewState(block: State.() -> State) {
        _viewState.value = block(checkNotNull(viewState.value))
    }
    protected fun postNewState(block: State.() -> State) {
        _viewState.postValue(block(checkNotNull(viewState.value)))
    }

    abstract operator fun invoke(event: Event)
}