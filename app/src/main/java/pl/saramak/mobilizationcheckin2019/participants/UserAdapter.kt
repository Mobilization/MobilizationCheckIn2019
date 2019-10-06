package pl.saramak.mobilizationcheckin2019.participants


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import pl.saramak.mobilizationcheckin2019.R

import java.util.regex.Pattern
import java.text.Normalizer


class UserAdapter() : RecyclerView.Adapter<UserAdapter.ViewHolder>(), android.widget.Filterable {

    var setUserClickListener: ((User) -> Unit)? = null

    companion object Colors {
        val COLOR_MAP: Map<String, Int> = mapOf(
                Pair("bli", R.color.blind_bird),
                Pair("ear", R.color.early_bird),
                Pair("reg", R.color.regular),
                Pair("lat", R.color.late_bird),
                Pair("last bird", R.color.last_bird),
                Pair("org", R.color.organizer),
                Pair("vip", R.color.vip),
                Pair("spe", R.color.speaker)
        )
        val pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
    }


    override fun getFilter(): android.widget.Filter {
        return FilterUsers(this, myDataset)
    }

    class FilterUsers() : android.widget.Filter() {
        private val regex = Pattern.compile("\\s+")

        lateinit var userAdapter: UserAdapter;
        lateinit var filteredList: List<User>
        lateinit var orginalList: List<User>

        constructor(adapter: UserAdapter, orginal: List<User>) : this() {
            userAdapter = adapter
            orginalList = orginal
            filteredList = ArrayList(orginal)
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            userAdapter.filtered = results!!.values as List<User>
            userAdapter.notifyDataSetChanged()
        }

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val res = FilterResults()
            var filtered: List<User> = orginalList

            if (!constraint.isNullOrBlank()) {
                val constraints = constraint!!.split(regex)

                for (element in constraints) {
                    if(element.isNumeric()) {
                        filtered = filtered.filter { it.orderid_string!!.contains(element) || it.number_string!!.contains(element) }
                        if(filtered.isNotEmpty())
                            continue
                    }

                    filtered = filtered.filter { it.last_normalized!!.toLowerCase().contains(element ?: "") || it.first_normalized!!.toLowerCase().contains(element ?: "") || it.email!!.toLowerCase().contains(element ?: "") }

                }
            }

            res.values = filtered
            res.count = filtered.size
            return res
        }

        fun deAccent(str: CharSequence): String {
            val nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD)
            return pattern.matcher(nfdNormalizedString).replaceAll("")
        }
    }

    var filtered: List<User> = emptyList()

    var myDataset: List<User> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun getItemCount(): Int {
        if (filtered.isNotEmpty()) {
            return filtered.size
        }
        return myDataset.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var user: User;

        if (filtered.isNotEmpty()) {
            user = filtered.get(position)
        } else {
            user = myDataset.get(position);
        }
        //Log.d("user", "${user?.last} ${user?.first}" )
        holder.personalName.text = "${user?.last} ${user?.first}";
        holder.personalEmail.text = "${user.email}"
        holder.type.text = "${user?.type}"
        holder.type.setBackgroundColor(getColor(holder.personalName.context, user))
//        Log.d("user", "${user?.last} ${user?.first} ${user.email} ${user.checked}" )
        //in some cases, it will prevent unwanted situations
        holder.checkin.setOnCheckedChangeListener(null);
        holder.checkingParent.setOnClickListener(null)
        holder.checkingParent.setOnClickListener { holder.checkin.isChecked = !holder.checkin.isChecked }
        holder.checkin.isChecked = user.checked;
        holder.checkin.setOnCheckedChangeListener { buttonView, isChecked ->
            setUserClickListener?.invoke(user)
        }

    }

    private fun getColor(context: Context, user: User) = ContextCompat.getColor(context, COLOR_MAP.get(user?.type?.toLowerCase()?.substring(0, 3)) ?: android.R.color.white)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.getContext())
            .inflate(R.layout.user_item_layout, parent, false) as ConstraintLayout
        return ViewHolder(v);
    }

    class ViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
        var personalName = itemView.findViewById(R.id.person_detal) as android.widget.TextView
        var personalEmail = itemView.findViewById(R.id.person_email) as android.widget.TextView;
        var checkin = itemView.findViewById(R.id.checkin) as CheckBox
        var checkingParent = itemView;
        var type = itemView.findViewById(R.id.type) as TextView

    }

}

val numeric = "[0-9]+".toRegex()

private fun String.isNumeric(): Boolean = numeric.matches(this)

