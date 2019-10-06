package pl.saramak.mobilizationcheckin2019.participants

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.FirebaseDatabase
import pl.saramak.core.ui.BaseViewModel

class ParticipantsViewModel : BaseViewModel(), UpdatedUserListener {

    val database: FirebaseDatabase
    val userStatus = MutableLiveData<List<User>>()

    init {
        database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("/")
        myRef.addValueEventListener(UserChangeDataListener(this))
    }

    override fun onNewUpdateListener(users: List<User>) {
        userStatus.value = users
    }

    fun checkUser(u: User) {
        val myRef = database.getReference("/${u.number}")
        u.checked = !u.checked
        myRef.setValue(u)
    }


}