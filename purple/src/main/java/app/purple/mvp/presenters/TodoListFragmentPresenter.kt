package app.purple.mvp.presenters

import app.purple.R
import app.purple.models.TodoList
import app.purple.mvp.models.TodoListFragmentModel
import app.purple.mvp.models.TodoListModel
import app.purple.mvp.views.TodoListView

class TodoListFragmentPresenter(
        var todoListView: TodoListView,
        var todoListModel: TodoListModel = TodoListFragmentModel()) : TodoListPresenter {

    override
    fun loadUI(todoList: TodoList) {
        todoListView.loadTodoItemsRecyclerView(todoList)
        todoListView.loadAddItemButton()
    }

    override
    fun fetchTodoList(id :Long, callback: (TodoList?) -> Unit) {
        todoListModel.fetchTodoList(id, callback)
    }

    override
    fun removeTodoList(todoList: TodoList?) {
        todoListModel.remove(todoList, { wasRemoved ->
            if (wasRemoved) {
                todoListView.onRemoveTodoList()
            } else {
                todoListView.showMessage(todoListView.getContext().getString(R.string.try_again))
            }
        })
    }
}