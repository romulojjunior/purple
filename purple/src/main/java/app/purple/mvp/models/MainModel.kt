package app.purple.mvp.models

import app.purple.models.TodoList
import io.reactivex.Observable

interface MainModel {
    fun findTodoLists() : Observable<MutableList<TodoList>?>
}