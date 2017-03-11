package app.appk.mvp.views.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.appk.R
import app.appk.adapters.TodoItemAdapter
import app.appk.models.TodoItem
import app.appk.models.TodoList
import app.appk.mvp.presenters.TodoListFragmentPresenter
import app.appk.mvp.presenters.TodoListPresenter
import app.appk.mvp.views.TodoListView
import com.pawegio.kandroid.find

class TodoListFragment : Fragment(), TodoListView {
    var todoListPresenter: TodoListPresenter? = null

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        todoListPresenter = TodoListFragmentPresenter(this)
    }

    override
    fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_todo_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadUI(arguments!!.getLong(TODO_LIST_ID))
    }

    override fun loadUI(todoListId: Long) {
        todoListPresenter?.fetchTodoList(todoListId)
    }

    override fun loadTodoItemsRecyclerView(todoList: TodoList) {
        var recyclerView = view?.find<RecyclerView>(R.id.fragment_todo_list_RecycleView)
        recyclerView?.layoutManager = LinearLayoutManager(context)

        var todoItems: MutableList<TodoItem>? = todoList.todoItems()
        if (todoItems != null) recyclerView?.adapter = TodoItemAdapter(todoItems)
    }

    // Statics

    companion object {
        const val TODO_LIST_ID = "TODO_LIST_ID"

        fun newInstance(todoListId: Long): TodoListFragment {
            val args: Bundle = Bundle()
            args.putLong(TODO_LIST_ID, todoListId)

            val fm = TodoListFragment()
            fm.arguments = args

            return fm
        }
    }
}

