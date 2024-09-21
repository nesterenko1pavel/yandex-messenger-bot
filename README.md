# Yandex Messenger Bot API

[![](https://jitpack.io/v/nesterenko1pavel/yandex-messenger-bot.svg)](https://jitpack.io/#nesterenko1pavel/yandex-messenger-bot)

### A few words
Hi! It's indie project for supporting Yandex Messenger API in kotlin. Glad to see you in my pet project. Hope this library will save your time and make your activity in messenger more convenient

### Yandex Messenger API
[API Documentation](https://yandex.ru/dev/messenger/doc/ru/)

|    polling method    |  Status   |
|:--------------------:|:---------:|
| messages/getUpdates/ | supported |

|    messages method    |    Status     |
|:---------------------:|:-------------:|
|  messages/sendText/   |   supported   |
|  messages/sendFile/   |   supported   |
|   messages/getFile/   | not supported |
|  messages/sendImage/  | not supported |
| messages/sendGallery/ | not supported |
|   messages/delete/    | not supported |

|     chats method     |    Status     |
|:--------------------:|:-------------:|
|    chats/create/     |   supported   |
| chats/updateMembers/ |   supported   |
|  users/getUserLink/  | not supported |

|     polls method     |    Status     |
|:--------------------:|:-------------:|
| messages/createPoll/ | not supported |
|  polls/getResults/   | not supported |
|   polls/getVoters/   | not supported |

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