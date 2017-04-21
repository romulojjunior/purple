package app.purple.mvp.models

import app.purple.models.TodoList
import io.reactivex.Observable

interface TodoListModel {
    fun fetchTodoList(id: Long): Observable<TodoList?>
    fun remove(todoList: TodoList?): Observable<Boolean>
}