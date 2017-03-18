package app.appk.mvp.views.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.TextView
import app.appk.R
import app.appk.adapters.TodoItemAdapter
import app.appk.dialogs.TodoItemFormDialog
import app.appk.dialogs.TodoListFormDialog
import app.appk.models.TodoItem
import app.appk.models.TodoList
import app.appk.mvp.presenters.TodoListFragmentPresenter
import app.appk.mvp.presenters.TodoListPresenter
import app.appk.mvp.views.MainView
import app.appk.mvp.views.TodoListView
import com.pawegio.kandroid.find
import com.pawegio.kandroid.toast

class TodoListFragment : Fragment(), TodoListView {
    var todoListPresenter: TodoListPresenter? = null
    var todoList: TodoList? = null
    var titleTextView: TextView? = null

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        todoListPresenter = TodoListFragmentPresenter(this)
    }

    override
    fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater?.inflate(R.layout.fragment_todo_list, container, false)
        titleTextView = view?.find<TextView>(R.id.fragment_todo_list_TextView)

        return view
    }

    override
    fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val todoListId = arguments!!.getLong(TODO_LIST_ID)
        val callback = { todoList: TodoList? ->
            this.todoList = todoList
            this.titleTextView?.text = todoList?.title

            if (todoList != null) todoListPresenter?.loadUI(todoList)
        }

        todoListPresenter?.fetchTodoList(todoListId, callback)
    }

    override
    fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        var addMenuTodoItem = menu?.add("New Todo Item")
        addMenuTodoItem?.setOnMenuItemClickListener { showTodoItemFormDialog(); false }

        var addMenuTodoList = menu?.add("New Todo List")
        addMenuTodoList?.setOnMenuItemClickListener { showTodoListFormDialog(); false }
    }

    // From View Interface

    override
    fun loadTodoItemsRecyclerView(todoList: TodoList) {
        var recyclerView = view?.find<RecyclerView>(R.id.fragment_todo_list_RecycleView)
        recyclerView?.layoutManager = LinearLayoutManager(context)

        var todoItems: MutableList<TodoItem>? = todoList.todoItems()
        if (todoItems != null) recyclerView?.adapter = TodoItemAdapter(todoItems)
    }

    override
    fun addNewItemToTodoItemsRecyclerView(todoItem: TodoItem) {
        if (todoItem?.save()!! > 0) {
            var recyclerView = view?.find<RecyclerView>(R.id.fragment_todo_list_RecycleView)
            var todoItemAdapter = recyclerView?.adapter as TodoItemAdapter
            todoItemAdapter.addItem(todoItem)
            toast(R.string.item_added)
        }

    }

    override
    fun showTodoItemFormDialog() {
        var dialog = TodoItemFormDialog.newInstance(null)
        dialog.callback = object : TodoItemFormDialog.Callback {
            override
            fun onSave(todoItem: TodoItem?): Unit {
                todoItem?.todoListId = todoList?.id
                addNewItemToTodoItemsRecyclerView(todoItem!!)
            }
        }

        dialog.show(activity.supportFragmentManager, TodoItemFormDialog.TAG)
    }

    override
    fun showTodoListFormDialog() {
        var dialog = TodoListFormDialog.newInstance(null)
        dialog.callback = object : TodoListFormDialog.Callback {
            override
            fun onSave(todoList: TodoList?): Unit {
                if (todoList != null) {
                    toast(R.string.save)
                    (activity as MainView).onNewTodoListCreated(todoList)
                } else {
                    toast(R.string.not_saved)
                }
            }
        }

        dialog.show(activity.supportFragmentManager, TodoListFormDialog.TAG)
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

