package app.purple.features.home.models

import app.purple.models.TodoList
import app.purple.features.home.contracts.MainContract
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivityModel : MainContract.Model {
    override
    fun findTodoLists(): Observable<MutableList<TodoList>?> {
        return Observable.fromCallable { TodoList.findAll() }
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
    }
}