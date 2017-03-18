package app.appk.mvp.models

import app.appk.models.TodoList

class TodoListFragmentModel : TodoListModel {

    override
    fun fetchTodoList(id: Long, callback: (TodoList?) -> Unit) {
        callback(TodoList.findById(id))
    }

    override
    fun remove(todoList: TodoList?, callback: (wasRemoved: Boolean) -> Any) {
        if (todoList != null) {
            todoList.deleteTodoItems()
            callback(todoList.delete())
        } else {
            callback(false)
        }
    }
}