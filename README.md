# Yandex Messenger Bot API

[![](https://jitpack.io/v/nesterenko1pavel/yandex-messenger-bot.svg)](https://jitpack.io/#nesterenko1pavel/yandex-messenger-bot)

### A few words
Hi! It's indie project for supporting Yandex Messenger API in kotlin. Glad to see you in my pet project. Hope this library will save your time and make your activity in messenger more convenient

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