package pl.saramak.fbexample.view

import androidx.appcompat.widget.SearchView
import pl.saramak.mobilizationcheckin2019.participants.UserAdapter


/**
 * Created by saramakm on 27/06/2017.
 */
class SearchCloseListener(val adapter: UserAdapter) : SearchView.OnCloseListener {
    override fun onClose(): Boolean {
        //some operation
        adapter.filter.filter("")
        return true;
    }

}