package pl.saramak.mobilizationcheckin2019


import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.widget.SearchView

import pl.saramak.mobilizationcheckin2019.participants.UserAdapter

/**
 * Created by saramakm on 27/06/2017.
 */
class SearchTextListener(val mAdapter : UserAdapter) : TextWatcher {
    override fun afterTextChanged(s: Editable?) {

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(newText: CharSequence?, start: Int, before: Int, count: Int) {
        mAdapter.filter.filter(newText)
    }


}