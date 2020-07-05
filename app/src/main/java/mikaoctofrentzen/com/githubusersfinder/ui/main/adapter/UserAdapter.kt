package mikaoctofrentzen.com.githubusersfinder.ui.main.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mikaoctofrentzen.com.githubusersfinder.R
import mikaoctofrentzen.com.githubusersfinder.model.Users
import mikaoctofrentzen.com.githubusersfinder.util.inflate

class UserAdapter(val users: MutableList<Users>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(parent.inflate(R.layout.user_item))
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}