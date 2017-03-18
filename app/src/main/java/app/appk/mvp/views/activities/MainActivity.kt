package app.appk.mvp.views.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.Menu
import android.view.MenuInflater
import app.appk.R
import app.appk.adapters.FragmentAdapter
import app.appk.dialogs.TodoListFormDialog
import app.appk.models.Status
import app.appk.models.TodoItem
import app.appk.models.TodoList
import app.appk.mvp.presenters.MainActivityPresenter
import app.appk.mvp.presenters.MainPresenter
import app.appk.mvp.views.MainView
import com.pawegio.kandroid.find
import com.pawegio.kandroid.toast

class MainActivity : AppCompatActivity(), MainView {

    var mainPresenter: MainPresenter? = null

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainPresenter = MainActivityPresenter(this)
        mainPresenter?.loadUI()
    }

    override
    fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)

        val addMenuTodoList = menu?.add("New Todo List")
        addMenuTodoList?.setOnMenuItemClickListener { showTodoListFormDialog(); false }
        return false
    }

    override
    fun onNewTodoListCreated(todoList: TodoList) {
        toast("Reloading TodoLists")
        mainPresenter?.loadUI()
    }

    override
    fun loadViewPager(fragments: List<Fragment>?) {
        var viewPager = find<ViewPager>(R.id.activity_main_ViewPager)
        if (fragments != null) viewPager.adapter = FragmentAdapter(supportFragmentManager, fragments)
    }

    override
    fun showTodoListFormDialog() {
        var dialog = TodoListFormDialog.newInstance(null)
        dialog.callback = object : TodoListFormDialog.Callback {
            override
            fun onSave(todoList: TodoList?): Unit {
                if (todoList != null) {
                    toast(R.string.save)
                    onNewTodoListCreated(todoList)
                } else {
                    toast(R.string.not_saved)
                }
            }
        }

        dialog.show(supportFragmentManager, TodoListFormDialog.TAG)
    }

    override
    fun showMessage(message: String) {
        toast(message)
    }

    // TODO: remote it
    private fun mockItems(id: Long) {
        var todoList = TodoList()
        todoList.title = "Lista ".plus(id)
        todoList.save()

        for(itemId in (1..20)) {
            var todoItem = TodoItem()
            todoItem.title = "item ".plus(itemId)
            todoItem.description = "Descriçao ".plus(itemId)
            todoItem.status = Status.done
            todoItem.todoListId = todoList.id
            todoItem.save()
        }
    }
}
