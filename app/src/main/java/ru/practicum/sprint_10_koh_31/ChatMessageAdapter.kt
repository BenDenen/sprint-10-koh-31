package ru.practicum.sprint_10_koh_31

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Locale

class ChatMessageAdapter(
    private val chatMessages: List<ChatMessage>
) : RecyclerView.Adapter<MessageViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MessageViewHolder {

        val layoutId = when (viewType) {
            CURRENT_USER_VIEW_TYPE -> R.layout.chat_message_item
            else -> R.layout.other_user_chat_message_item
        }

        val itemView = LayoutInflater.from(parent.context)
            .inflate(layoutId, parent, false)
        return when (viewType) {
            CURRENT_USER_VIEW_TYPE -> ChatMessageViewHolder(itemView)
            else -> OtherUserChatMessageViewHolder(itemView)
        }

    }

    override fun onBindViewHolder(
        holder: MessageViewHolder,
        position: Int
    ) {
        holder.bind(chatMessages[position])
    }

    override fun getItemCount(): Int {
        Log.d("MyTag", "getItemCount ${chatMessages.size}")
        return chatMessages.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (chatMessages[position].isCurrentUser) {
            CURRENT_USER_VIEW_TYPE
        } else {
            OTHER_USER_VIEW_TYPE
        }
    }

    companion object {
        private const val CURRENT_USER_VIEW_TYPE = 0
        private const val OTHER_USER_VIEW_TYPE = 1
    }

}

abstract class MessageViewHolder(item: View) : RecyclerView.ViewHolder(item) {

    abstract fun bind(data: ChatMessage)
}

class ChatMessageViewHolder(item: View) : MessageViewHolder(item) {

    private val dateFormatter = SimpleDateFormat("hh:mm", Locale.getDefault())

    private lateinit var tvMessage: TextView
    private lateinit var tvDate: TextView
    private lateinit var ivStatus: ImageView

    override fun bind(data: ChatMessage) {
        Log.d("MyTag", "bind $data")
        tvMessage = itemView.findViewById<TextView>(R.id.tv_message)
        tvMessage.text = data.text
        tvDate = itemView.findViewById<TextView>(R.id.tv_date)
        tvDate.text = dateFormatter.format(data.date)

        ivStatus =  itemView.findViewById<ImageView>(R.id.iv_status)
        ivStatus.setImageResource(
            when (data.status) {
                ChatMessageStatus.SENT -> R.drawable.ic_sent
                ChatMessageStatus.DELIVERED -> R.drawable.ic_delivered
                ChatMessageStatus.READ -> R.drawable.ic_read
            }
        )

    }

}

class OtherUserChatMessageViewHolder(item: View) : MessageViewHolder(item) {

    private val dateFormatter = SimpleDateFormat("hh:mm", Locale.getDefault())

    private lateinit var tvMessage: TextView
    private lateinit var tvDate: TextView

    override fun bind(data: ChatMessage) {
        Log.d("MyTag", "other user bind $data")
        tvMessage = itemView.findViewById<TextView>(R.id.tv_message)
        tvMessage.text = data.text
        tvDate = itemView.findViewById<TextView>(R.id.tv_date)
        tvDate.text = dateFormatter.format(data.date)


    }

}