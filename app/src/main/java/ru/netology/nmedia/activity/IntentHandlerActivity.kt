package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import ru.netology.nmedia.databinding.ActivityIntentHandlerBinding
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.R

class IntentHandlerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityIntentHandlerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent?.let {
            if (it.action != Intent.ACTION_SEND) {
                return@let
            }

            val text = it.getStringExtra(Intent.EXTRA_TEXT)
            if (text.isNullOrBlank()) {
                Snackbar.make(binding.root, R.string.error_empty_content, LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok) {
                        finish()
                    }
                    .show()
                return@let
            }

            binding.viewFrame.setOnClickListener {
                if (Uri.parse(text).isHierarchical) {
                    val intentView = Intent(Intent.ACTION_VIEW, Uri.parse(text))
                    startActivity(intentView)
                    finish()
                } else {
                    Snackbar.make(binding.root, R.string.error_ref_entry, LENGTH_INDEFINITE)
                        .setAction(android.R.string.ok) {
                            finish()
                        }
                        .show()
                }
            }
        }
    }
}