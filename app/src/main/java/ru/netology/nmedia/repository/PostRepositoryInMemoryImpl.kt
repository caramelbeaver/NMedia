package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemoryImpl : PostRepository {
    private var nextId = 1L
    private var posts = listOf(
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "https://www.youtube.com/watch?v=WhWc3b3KhnY",
            published = "12 апреля в 12:12",
            likedByMe = false,
            likes = 9_999,
            shared = 1_099,
            viewed = 2
        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Чтобы поселиться в Париже, желательно выучить французский. Чтобы начать карьеру в сфере IT — быть на «ты» с языками программирования. Начните с Python. Его часто рекомендуют в качестве первого языка программирования, он интуитивно понятен и востребован в разных сферах от разработки сайтов до приложений.",
            published = "17 августа в 11:00",
            likedByMe = false,
            likes = 999,
            shared = 1_099, //9_996,
            viewed = 2_390_480
        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "22 августа в 19:00 на бесплатном занятии «Таргетолог или SMM-менеджер: что выбрать и как начать карьеру» вы поймёте, какая из этих профессий подойдёт именно вам. Узнаете о перспективах работы в каждом направлении.",
            published = "16 августа в 19:12",
            likedByMe = false,
            likes = 999,
            shared = 9_996,
            viewed = 2_390_480
        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Что в играх вам нравится больше — бежать по бесконечному игровому миру, собирая монетки и уворачиваясь от препятствий, или проходить миссию, попутно отстреливаясь от врагов? На бесплатном курсе «Введение в геймдизайн» узнаете о профессии геймдизайнера и попробуете создать собственную игру.",
            published = "15 августа в 19:00",
            likedByMe = false,
            likes = 999,
            shared = 9_996,
            viewed = 2_390_480
        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Кто из нас хоть раз не мечтал вернуться в прошлое? Можно было бы исправить ошибки или вообще кардинально изменить свою жизнь. Собрали фильмы о путешествиях во времени, которые помогут не жалеть о прошлом и уверенно смотреть в будущее.",
            published = "15 августа в 15:00",
            likedByMe = false,
            likes = 999,
            shared = 9_996,
            viewed = 2_390_480
        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Каким дизайнером быть? Собрали подборку открытых занятий, чтобы понять, какое направление в дизайне вам ближе.",
            published = "15 августа в 10:00",
            likedByMe = false,
            likes = 999,
            shared = 9_996,
            viewed = 2_390_480
        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Стажировка ― прекрасный шанс проявить себя и попасть на работу в компанию мечты. В первые дни вам доверят не совсем рабочие задачки: сбегать за кофе, сделать копии документов, забрать почту. В эти моменты важно показать себя. Так ваш наставник поймёт, что может поручить дела посложнее.",
            published = "12 августа в 18:00",
            likedByMe = false,
            likes = 999,
            shared = 9_996,
            viewed = 2_390_480
        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "В этом году в Telegram пришло много пользователей из социальных сетей, которые запретили в России. Бренды и блогеры стали активно развивать свои каналы, привлекать на площадку новых подписчиков. Разбираемся, как можно заработать на создании контента при помощи закрытого Telegram-канала.",
            published = "11 августа в 10:30",
            likedByMe = false,
            likes = 999,
            shared = 9_996,
            viewed = 2_390_480
        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likedByMe = false,
            likes = 999,
            shared = 9_996,
            viewed = 2_390_480
        ),
    )

    private val data = MutableLiveData(posts)

    private val empty = Post(
        id = 0,
        content = "",
        author = "",
        likedByMe = false,
        published = ""
    )
    private val dataPost = MutableLiveData(empty)

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
            return
        }

        posts = posts.map {
            if (it.id != post.id) it else it.copy(content = post.content)
        }
        data.value = posts
    }

    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id)
                it
            else
                it.copy(
                    likedByMe = !it.likedByMe,
                    likes = if (!it.likedByMe) it.likes + 1 else it.likes - 1
                )
        }
        data.value = posts
    }

    override fun shareById(id: Long) {
        posts = posts.map {
            if (it.id != id)
                it
            else
                it.copy(shared = it.shared + 1)
        }
        data.value = posts
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
    }
}