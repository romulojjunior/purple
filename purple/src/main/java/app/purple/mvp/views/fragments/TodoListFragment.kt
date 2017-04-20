package app.purple.mvp.views.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.TextView
import app.purple.R
import app.purple.adapters.TodoItemAdapter
import app.purple.dialogs.TodoItemFormDialog
import app.purple.models.TodoItem
import app.purple.models.TodoList
import app.purple.mvp.presenters.TodoListFragmentPresenter
import app.purple.mvp.presenters.TodoListPresenter
import app.purple.mvp.views.MainView
import app.purple.mvp.views.TodoListView
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
        todoListPresenter?.fetchTodoList(todoListId)
    }

    override
    fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        val removeMenuTodoList = menu?.add(getString(R.string.remove_list))
        removeMenuTodoList?.setOnMenuItemClickListener { todoListPresenter?.removeTodoList(todoList); false }
    }

    override
    fun onFetchTodoList(todoList: TodoList?) {
        this.todoList = todoList
        this.titleTextView?.text = todoList?.title

        if (todoList != null) todoListPresenter?.loadUI(todoList)
    }

    override
    fun onLoadAddItemButton() {
        view?.find<View>(R.id.fragment_todo_list_AddItemButton)?.setOnClickListener {
            onShowTodoItemFormDialog(null)
        }
    }

    override
    fun onLoadTodoItemsRecyclerView(todoList: TodoList) {
        val recyclerView = view?.find<RecyclerView>(R.id.fragment_todo_list_RecycleView)
        recyclerView?.layoutManager = LinearLayoutManager(context)

        val todoItems: MutableList<TodoItem>? = todoList.todoItems()
        val adapter = TodoItemAdapter(activity, todoItems, activity.supportFragmentManager)
        if (todoItems != null) recyclerView?.adapter = adapter
    }

    override
    fun onAddNewItemToTodoItemsRecyclerView(todoItem: TodoItem) {
        if (todoItem.save() > 0) {
            val recyclerView = view?.find<RecyclerView>(R.id.fragment_todo_list_RecycleView)
            val todoItemAdapter = recyclerView?.adapter as TodoItemAdapter
            todoItemAdapter.addItem(todoItem)
            toast(R.string.item_added)
        }
    }

    override
    fun onShowTodoItemFormDialog(item: TodoItem?) {
        val dialog = TodoItemFormDialog.newInstance(item)
        dialog.callback = object : TodoItemFormDialog.Callback {
            override
            fun onSave(todoItem: TodoItem?) {
                todoItem?.todoListId = todoList?.id
                onAddNewItemToTodoItemsRecyclerView(todoItem!!)
            }
        }

        dialog.show(activity.supportFragmentManager, TodoItemFormDialog.TAG)
    }

    override
    fun onShowMessage(message: String) {
        toast(message)
    }

    override
    fun onRemoveTodoList() {
        onShowMessage(getString(R.string.removed_todo_list))
        (activity as MainView).onChangeNewTodoLists()
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
