package app.purple.features.todoList.views

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.TextView
import android.widget.Toast
import app.purple.base.GenericFragment
import app.purple.R
import app.purple.adapters.TodoItemAdapter
import app.purple.dialogs.TodoItemFormDialog
import app.purple.models.TodoItem
import app.purple.models.TodoList
import app.purple.features.home.contracts.MainContract
import app.purple.features.todoList.contracts.TodoListContract
import app.purple.features.todoList.presenters.TodoListFragmentPresenter

class TodoListFragment : GenericFragment(), TodoListContract.View {
    var todoListPresenter: TodoListContract.Presenter? = null
    var todoList: TodoList? = null
    var titleTextView: TextView? = null

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        todoListPresenter = TodoListFragmentPresenter(this)
    }

    override
    fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view =  inflater.inflate(R.layout.fragment_todo_list, container, false)
        titleTextView = view?.findViewById(R.id.fragment_todo_list_TextView)

        return view
    }

    override
    fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
        view?.findViewById<View>(R.id.fragment_todo_list_AddItemButton)?.setOnClickListener {
            onShowTodoItemFormDialog(null)
        }
    }

    override
    fun onLoadTodoItemsRecyclerView(todoList: TodoList) {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.fragment_todo_list_RecycleView)
        recyclerView?.layoutManager = LinearLayoutManager(context)

        val todoItems: MutableList<TodoItem>? = todoList.todoItems()

        val adapter = TodoItemAdapter(context!!, todoItems)
        adapter.onShowDialog = { item, callback: (item: TodoItem?) -> Unit ->
            val dialog = TodoItemFormDialog.newInstance(item)
            dialog.callback = object : TodoItemFormDialog.Callback {
                override
                fun onSave(todoItem: TodoItem?) {
                   callback(todoItem)
                }
            }

            dialog.show(fragmentManager, TodoItemFormDialog.TAG)
        }


        if (todoItems != null) recyclerView?.adapter = adapter
    }

    override
    fun onAddNewItemToTodoItemsRecyclerView(todoItem: TodoItem) {
        if (todoItem.save() > 0) {
            val recyclerView = view?.findViewById<RecyclerView>(R.id.fragment_todo_list_RecycleView)
            val todoItemAdapter = recyclerView?.adapter as TodoItemAdapter
            todoItemAdapter.addItem(todoItem)
            Toast.makeText(context, R.string.item_added, Toast.LENGTH_LONG).show()
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

        dialog.show(activity?.supportFragmentManager, TodoItemFormDialog.TAG)
    }

    override
    fun onRemoveTodoList() {
        onShowMessage(getString(R.string.removed_todo_list))
        (activity as MainContract.View).onChangeNewTodoLists()
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

