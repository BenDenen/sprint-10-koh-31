package ru.practicum.sprint_10_koh_31

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import java.util.Date

class MainActivity : AppCompatActivity() {

    private val chatMessagesList: MutableList<ChatMessage> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val ime = insets.getInsets(WindowInsetsCompat.Type.ime())
            val bottomPadding = if (ime.bottom > 0) {
                ime.bottom
            } else {
                systemBars.bottom
            }
            v.setPadding(
                systemBars.left, systemBars.top, systemBars.right,
                bottomPadding
            )
            insets
        }


        val etMessage = findViewById<EditText>(R.id.et_message)
        val ivAction = findViewById<ImageView>(R.id.iv_action)

        etMessage.doAfterTextChanged {
            ivAction.setImageResource(
                if (it.isNullOrBlank()) {
                    R.drawable.ic_audio
                } else {
                    R.drawable.ic_send
                }
            )
        }

        val rvItems = findViewById<RecyclerView>(R.id.rv_items)
        val adapter = ChatMessageAdapter(chatMessagesList)
        rvItems.adapter = adapter


        ivAction.setOnClickListener {
            val text = etMessage.text
            if (!text.isNullOrBlank()) {
                chatMessagesList.add(
                    ChatMessage(
                        text = text.toString(),
                        date = Date(),
                        isCurrentUser = true,
                        status = ChatMessageStatus.SENT
                    )
                )
                Log.d("MyTag", "size ${chatMessagesList.size}")
                adapter.notifyItemInserted(chatMessagesList.size)

                ivAction.postDelayed({
                    chatMessagesList.add(
                        ChatMessage(
                            text = "Hello $text",
                            date = Date(),
                            isCurrentUser = false,
                        )
                    )
                    adapter.notifyItemInserted(chatMessagesList.size)
                },1000)
            }

        }



    }
}

data class ChatMessage(
    val text: String,
    val date: Date,
    val status: ChatMessageStatus = ChatMessageStatus.SENT,
    val isCurrentUser: Boolean
)

enum class ChatMessageStatus {
    SENT, DELIVERED, READ
}