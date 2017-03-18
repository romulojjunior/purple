package app.appk.mvp.models

import app.appk.models.TodoList

interface TodoListModel {
    fun fetchTodoList(id :Long, callback: (TodoList?) -> Unit)
    fun remove(todoList: TodoList?, callback: (wasRemoved: Boolean) -> Any)
}