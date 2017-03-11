package app.appk.mvp.views.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import app.appk.R
import app.appk.adapters.FragmentAdapter
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
    fun loadViewPager(fragments: List<Fragment>) {
        var viewPager = find<ViewPager>(R.id.activity_main_ViewPager)
        viewPager.adapter = FragmentAdapter(supportFragmentManager, fragments)
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
