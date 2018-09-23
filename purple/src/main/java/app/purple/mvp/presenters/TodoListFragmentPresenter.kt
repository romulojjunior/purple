package app.purple.mvp.presenters

import app.purple.R
import app.purple.models.TodoList
import app.purple.mvp.contracts.TodoListContract
import app.purple.mvp.models.TodoListFragmentModel

class TodoListFragmentPresenter(
        var todoListView: TodoListContract.View,
        var todoListModel: TodoListContract.Model = TodoListFragmentModel()) : TodoListContract.Presenter {

    override
    fun loadUI(todoList: TodoList) {
        todoListView.onLoadTodoItemsRecyclerView(todoList)
        todoListView.onLoadAddItemButton()
    }

    override
    fun fetchTodoList(id :Long) {
        val observable = todoListModel.fetchTodoList(id)
        todoListView.compositeDisposable.add(observable.subscribe { todoList: TodoList? ->
                    todoListView.onFetchTodoList(todoList)
            }
        )
    }

    override
    fun removeTodoList(todoList: TodoList?) {
        val observable = todoListModel.remove(todoList)

        observable.doOnError { error ->
            error.printStackTrace()
        }
        val message = todoListView.getContext()?.getString(R.string.try_again)
        if (message != null) {
            todoListView.onShowMessage(message)
        }

        todoListView.compositeDisposable.add(
                observable.subscribe { wasRemoved: Boolean ->
                    if (wasRemoved) {
                        todoListView.onRemoveTodoList()
                    } else {
                        val tryAgainMessage = todoListView.getContext()?.getString(R.string.try_again)
                        if (tryAgainMessage != null) {
                            todoListView.onShowMessage(tryAgainMessage)
                        }
                    }
                }
        )
    }
}