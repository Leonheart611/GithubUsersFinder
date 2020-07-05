package mikaoctofrentzen.com.githubusersfinder.ui.main.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.user_item.view.*
import mikaoctofrentzen.com.githubusersfinder.R
import mikaoctofrentzen.com.githubusersfinder.model.Users
import mikaoctofrentzen.com.githubusersfinder.util.inflate
import mikaoctofrentzen.com.githubusersfinder.util.loadImage

class UserAdapter(val users: MutableList<Users>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    fun addUsers(users: MutableList<Users>) {
        this.users.addAll(users)
        notifyDataSetChanged()
    }

    fun renewUser(users: MutableList<Users>) {
        this.users.clear()
        this.users.addAll(users)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(parent.inflate(R.layout.user_item))
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        users[position].let {
            holder.bind(it)
        }
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: Users) {
            with(itemView) {
                tv_github_name.text = data.login
                iv_github_profile.loadImage(data.avatarUrl)
            }
        }
    }
}