# Yandex Messenger Bot API

[![](https://jitpack.io/v/nesterenko1pavel/yandex-messenger-bot.svg)](https://jitpack.io/#nesterenko1pavel/yandex-messenger-bot)

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