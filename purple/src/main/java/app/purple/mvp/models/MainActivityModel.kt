package app.purple.mvp.models

import app.purple.models.TodoList

class MainActivityModel : MainModel {

    override
    fun findTodoLists(callback: (MutableList<TodoList>?) -> Unit) {
        callback(TodoList.findAll()!!)
    }
}