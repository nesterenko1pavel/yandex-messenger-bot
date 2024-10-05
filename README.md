# Yandex Messenger Bot API

[![](https://jitpack.io/v/nesterenko1pavel/yandex-messenger-bot.svg)](https://jitpack.io/#nesterenko1pavel/yandex-messenger-bot)

### A few words
Hi! It's indie project for supporting Yandex Messenger API in kotlin. Glad to see you in my pet project. Hope this library will save your time and make your activity in messenger more convenient

### Yandex Messenger API
[API Documentation](https://yandex.ru/dev/messenger/doc/ru/)

### Implementation

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url = URI.create("https://jitpack.io") }
    }
}

dependencies {
  implementation("com.github.nesterenko1pavel:yandex-messenger-bot:VERSION")
}
```

### Use cases

```kotlin
fun startBot(token: String): Unit = runBlocking {
    supervisorScope {
        val controller = BotController(token)
        launch { controller.startPolling() }
        
        controller.addUpdateListener { updates ->
            println(updates)
        }

        controller.botApiClient.sendText(
            SendTextRequest(
                login = "login",
                text = "Hello world"
            )
        )
        
        controller.botApiClient.getFile(
            fileName = "name",
            fileExtension = "webp",
            fileId = "disk/fileId",
        )
    }
}
```

### After words
- Library supports all API methods except `messages/sendGallery/`. I faced a few problems how to send directory with files as a `binary data`
- While working on current project I discovered a few inconsistencies with official doc:
  - According to [Update](https://yandex.ru/dev/messenger/doc/ru/data-types#update) in case of getting update with image we will catch `Image[]`, but in reality we get `Image[][]`
  - According to [polls/getVoters/](https://yandex.ru/dev/messenger/doc/ru/api-requests/poll-get-voters#rezultat) we should get cursor as `Integer` in response but in reality we get `object` with `Integer` property `next`