package app.purple.mvp.models

import app.purple.models.TodoList
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivityModel : MainModel {
    override
    fun findTodoLists(): Observable<MutableList<TodoList>?> {
        return Observable.fromCallable({ TodoList.findAll() })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
    }
}