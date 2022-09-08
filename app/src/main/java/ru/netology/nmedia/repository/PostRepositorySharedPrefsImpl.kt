package ru.netology.nmedia.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.dto.Post

class PostRepositorySharedPrefsImpl(
    context: Context,
) : PostRepository {
    private val gson = Gson()
    private val prefs = context.getSharedPreferences("repo", Context.MODE_PRIVATE)
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val key = "posts"
    private var nextId = 1L
    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)

    private val empty = Post(
        id = 0,
        content = "",
        author = "",
        likedByMe = false,
        published = ""
    )
    private val dataPost = MutableLiveData(empty)

    init {
        prefs.getString(key, null)?.let {
            posts = gson.fromJson(it, type)
            data.value = posts
        }
    }

    // для презентации убрали пустые строки
    override fun getAll(): LiveData<List<Post>> = data

    override fun getPost(): LiveData<Post> = dataPost
    override fun getPostById(id: Long): Post? {
        dataPost.value = posts.find {
            it.id == id
        }
        return dataPost.value
    }

    override fun save(post: Post) {
        if (post.id == 0L) {
            // remove hardcoded author & published
            posts = listOf(
                post.copy(
                    id = nextId++,
                    author = "Me",
                    likedByMe = false,
                    published = "now"
                )
            ) + posts
            data.value = posts
            sync()
            return
        }

        posts = posts.map {
            if (it.id != post.id)
                it
            else
                it.copy(content = post.content)
        }
        data.value = posts
        sync()
    }

    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id)
                it
            else
                it.copy(
                    likedByMe = !it.likedByMe,
                    likes = if (it.likedByMe) it.likes - 1 else it.likes + 1
                )
        }
        data.value = posts
        sync()
    }

    override fun shareById(id: Long) {
        posts = posts.map {
            if (it.id != id)
                it
            else
                it.copy(shared = it.shared + 1)
        }
        data.value = posts
        sync()
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
        sync()
    }

    private fun sync() {
        with(prefs.edit()) {
            putString(key, gson.toJson(posts))
            apply()
        }
    }
}