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
        todoListView.onLoadTodoItemsRecyclerView(todoList)
        todoListView.onLoadAddItemButton()
    }

    override
    fun fetchTodoList(id :Long) {
        val callback = { todoList: TodoList? ->
            todoListView.onFetchTodoList(todoList)
        }

        todoListModel.fetchTodoList(id, callback)
    }

    override
    fun removeTodoList(todoList: TodoList?) {
        todoListModel.remove(todoList, { wasRemoved ->
            if (wasRemoved) {
                todoListView.onRemoveTodoList()
            } else {
                todoListView.onShowMessage(todoListView.getContext().getString(R.string.try_again))
            }
        })
    }
}