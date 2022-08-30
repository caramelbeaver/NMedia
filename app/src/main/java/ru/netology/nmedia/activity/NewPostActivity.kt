package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityNewPostBinding

class NewPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.edit.setText(intent.getStringExtra(RESULT_KEY))
        binding.edit.requestFocus()
        binding.ok.setOnClickListener {
            val intent = Intent()
            if (binding.edit.text.isNullOrBlank()) {
                setResult(RESULT_CANCELED, intent)
            } else {
                val content = binding.edit.text.toString()
                intent.putExtra(RESULT_KEY, content)
                setResult(RESULT_OK, intent)
            }
            finish()
        }
    }

    object NewPostResultContract : ActivityResultContract<Unit, String?>() {
        override fun createIntent(context: Context, input: Unit): Intent =
            Intent(context, NewPostActivity::class.java)

        override fun parseResult(resultCode: Int, intent: Intent?): String? =
            if (resultCode == Activity.RESULT_OK) {
                intent?.getStringExtra(RESULT_KEY)
            } else {
                null
            }
    }

    object EditPostResultContract : ActivityResultContract<String, String?>() {
        override fun createIntent(context: Context, input: String): Intent {
            val intent = Intent(context, NewPostActivity::class.java)
            intent.putExtra(RESULT_KEY, input)
            return intent
        }

        override fun parseResult(resultCode: Int, intent: Intent?): String? =
            if (resultCode == Activity.RESULT_OK) {
                intent?.getStringExtra(RESULT_KEY)
            } else {
                null
            }
    }

    private companion object {
        private const val RESULT_KEY = "newPostContent"
    }
}