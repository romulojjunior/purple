package app.purple.features.todoList.contracts

import android.content.Context
import app.purple.models.TodoItem
import app.purple.models.TodoList
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

interface TodoListContract {
    interface View {
        val compositeDisposable: CompositeDisposable
        fun onFetchTodoList(todoList: TodoList?)
        fun onLoadAddItemButton()
        fun onLoadTodoItemsRecyclerView(todoList: TodoList)
        fun onAddNewItemToTodoItemsRecyclerView(todoItem: TodoItem)
        fun getContext(): Context?
        fun onShowMessage(message: String)
        fun onShowTodoItemFormDialog(item: TodoItem?)
        fun onRemoveTodoList()
    }

    interface Presenter {
        fun loadUI(todoList: TodoList)
        fun fetchTodoList(id: Long)
        fun removeTodoList(todoList: TodoList?)
    }

    interface Model {
        fun fetchTodoList(id: Long): Observable<TodoList?>
        fun remove(todoList: TodoList?): Observable<Boolean>
    }
}