package app.purple.mvp.models

import app.purple.models.TodoList

interface TodoListModel {
    fun fetchTodoList(id :Long, callback: (TodoList?) -> Unit)
    fun remove(todoList: TodoList?, callback: (wasRemoved: Boolean) -> Any)
}