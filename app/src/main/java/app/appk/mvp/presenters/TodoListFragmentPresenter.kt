package app.appk.mvp.presenters

import app.appk.models.TodoList
import app.appk.mvp.models.TodoListFragmentModel
import app.appk.mvp.models.TodoListModel
import app.appk.mvp.views.TodoListView

class TodoListFragmentPresenter(
        var todoListView: TodoListView,
        var todoListModel: TodoListModel = TodoListFragmentModel()) : TodoListPresenter {

    override
    fun loadUI(todoList: TodoList) {
        todoListView.loadTodoItemsRecyclerView(todoList)
    }

    override
    fun fetchTodoList(id :Long, callback: (TodoList?) -> Unit) {
        todoListModel.fetchTodoList(id, callback)
    }
}