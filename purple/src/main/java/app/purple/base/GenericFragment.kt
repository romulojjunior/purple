package app.purple.base

import android.support.v4.app.Fragment
import android.widget.Toast
import io.reactivex.disposables.CompositeDisposable

open class GenericFragment : Fragment() {
    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    fun onShowMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}