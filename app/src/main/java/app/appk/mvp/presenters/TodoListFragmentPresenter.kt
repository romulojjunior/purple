package app.appk.mvp.presenters

import app.appk.models.TodoList
import app.appk.mvp.views.TodoListView

class TodoListFragmentPresenter(
        var todoListView: TodoListView) : TodoListPresenter {

    override
    fun fetchTodoList(id :Long) {
        var todoList = TodoList.findById(id)
        todoListView.loadTodoItemsRecyclerView(todoList)
    }
}