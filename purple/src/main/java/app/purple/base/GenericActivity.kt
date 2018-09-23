package app.purple.base

import android.support.v7.app.AppCompatActivity
import com.pawegio.kandroid.toast
import io.reactivex.disposables.CompositeDisposable

open class GenericActivity : AppCompatActivity() {
    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    fun onShowMessage(message: String) {
        toast(message)
    }
}