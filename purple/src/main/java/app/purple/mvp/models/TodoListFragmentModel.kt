package app.purple.mvp.models

import app.purple.models.TodoList
import app.purple.mvp.contracts.TodoListContract
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TodoListFragmentModel : TodoListContract.Model {

    override
    fun fetchTodoList(id: Long): Observable<TodoList?> {
        return Observable.fromCallable({ TodoList.findById(id) })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override
    fun remove(todoList: TodoList?): Observable<Boolean> {
        return Observable.fromCallable {
                todoList?.deleteTodoItems()
                todoList!!.delete()
            }.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
    }
}