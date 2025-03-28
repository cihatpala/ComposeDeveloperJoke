
# Joke App - Jetpack Compose & MVVM

Bu proje, Retrofit kullanarak bir API'den şaka (joke) verilerini çeken, MVVM mimarisi ile geliştirilmiş ve Jetpack Compose ile tasarlanmış bir Android uygulamasıdır.

## 📌 Proje Özellikleri
- **Jetpack Compose** ile modern UI
- **MVVM Mimarisi**
- **LiveData** ile veri gözlemleme
- **Retrofit** ile API'den veri çekme
- **Coroutines & Flow** kullanımı
- **ViewModel & State Management**

---

![jokeapp_preview](https://github.com/user-attachments/assets/7b24d3af-a597-4020-adc4-99174f499acb)

## 🚀 Kurulum

### 1️⃣ Gerekli Bağımlılıkları Ekleyin
Bu projeyi çalıştırmadan önce, aşağıdaki bağımlılıkların `libs.versions.toml`, `build.gradle.kts(App)` ve `build.gradle.kts(Module)`dosyanızda olduğundan emin olun:

```toml
[versions]
# Coroutine
#Retrofit
retrofit-ver = "2.11.0"

#Coroutine
coroutine-ver = "1.3.9"

#Serialization
serialization-ver = "1.8.0"

#ViewModel & LifeCycle & LiveData
lifecycle-ver = "2.8.7"
runtimeLivedata = "1.7.8"

[libraries]
#Retrofit
retrofit2 = { group = "com.squareup.retrofit2", name = "retrofit", version.ref = "retrofit-ver" }
gsonconverter = { group = "com.squareup.retrofit2", name = "converter-gson", version.ref = "retrofit-ver" }
rxadapter = { group = "com.squareup.retrofit2", name = "adapter-rxjava2", version.ref = "retrofit-ver" }

#Coroutine
coroutine-android = { group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-android", version.ref = "coroutine-ver" }
coroutine-core = { group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-core", version.ref = "coroutine-ver" }

#Serialization
kotlinx-serialization-json = { group = "org.jetbrains.kotlinx", name = "kotlinx-serialization-json", version.ref = "serialization-ver" }

#ViewModel & LifeCycle & LiveData
lifecycle-viewmodel = { group = "androidx.lifecycle", name = "lifecycle-viewmodel-ktx", version.ref = "lifecycle-ver" }
lifecycle-runtime = { group = "androidx.lifecycle", name = "lifecycle-runtime-ktx", version.ref = "lifecycle-ver" }
lifecycle-livedata = { group = "androidx.lifecycle", name = "lifecycle-livedata-ktx", version.ref = "lifecycle-ver" }
lifecycle-compose = { group = "androidx.lifecycle", name = "lifecycle-viewmodel-compose", version.ref = "lifecycle-ver" }
androidx-runtime-livedata = { group = "androidx.compose.runtime", name = "runtime-livedata", version.ref = "runtimeLivedata" }

[plugins]
kotlin-serialization = { id = "org.jetbrains.kotlin.plugin.serialization", version.ref = "kotlin" }
```

#### Without Version Catalog
If you are not using **Gradle Version Catalog**, add these dependencies to your `build.gradle.kts(Module :app)` file:

```kotlin
plugins {
    //other libs
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
  
    //retrofit2 - retrofit & gson converter & adapter rxjava3
    implementation(libs.retrofit2)
    implementation(libs.gsonconverter)
    implementation(libs.rxadapter)

    //coroutine
    implementation(libs.coroutine.android)
    implementation(libs.coroutine.core)

    //Kotlinx Serialization
    implementation(libs.kotlinx.serialization.json)

    //ViewModel & LifeCycle & LiveData
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.runtime)
    implementation(libs.lifecycle.livedata)
    implementation(libs.lifecycle.compose)
}
```
If you are not using **Gradle Version Catalog**, add these dependencies to your `build.gradle.kts(Project)` file:
```kotlin
plugins {
    //other libs
    alias(libs.plugins.kotlin.serialization) apply false
}
```
---

## 🛠 Mimari Yapı
Bu proje **MVVM (Model-View-ViewModel)** mimarisine uygundur:

- **Model:** `Joke.kt` → API'den gelen veri modelini içerir.
- **ViewModel:** `JokeViewModel.kt` → Veriyi yönetir ve UI ile paylaşır.
- **View:** `JokeScreen.kt` → Jetpack Compose UI bileşenlerini içerir.

---

## 📡 API Kullanımı
Bu uygulama, şakaları **GitHub üzerinden JSON olarak çeken** basit bir API kullanmaktadır. API'den veri çekmek için Retrofit kullanılmıştır.

**API Örneği:**
```json
{
    "error": false,
    "category": "Programming",
    "type": "twopart",
    "setup": "How do you generate a random string?",
    "delivery": "Put a Windows user in front of Vim and tell them to exit.",
    "flags": {
        "nsfw": false,
        "religious": false,
        "political": false,
        "racist": false,
        "sexist": false,
        "explicit": false
    },
    "id": 127,
    "safe": true,
    "lang": "en"
}
```

---

## 📲 Kullanım

### 1️⃣ `JokeScreen.kt` (UI Bileşeni)
```kotlin
@Composable
fun JokeScreen(viewModel: JokeViewModel = viewModel()) {
    val jokes by viewModel.jokes.observeAsState(initial = emptyList())

    LaunchedEffect(Unit) {
        viewModel.fetchJokes()
    }

    LazyColumn {
        items(jokes) { joke ->
            JokeItem(joke)
        }
    }
}
```

### 2️⃣ `JokeViewModel.kt` (ViewModel)
```kotlin
class JokeViewModel(application: Application) : AndroidViewModel(application) {
    private val _jokes = MutableLiveData<List<Joke>>()
    val jokes: LiveData<List<Joke>> get() = _jokes

    fun fetchJokes() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = retrofit.getData()
            if (response.isSuccessful) {
                response.body()?.let {
                    _jokes.postValue(it)
                }
            }
        }
    }
}
```

### 3️⃣ `Scaffold` İçinde Kullanımı
```kotlin
Scaffold(
    modifier = Modifier.fillMaxSize(),
    topBar = { TopAppBar(title = { Text("Jokes") }) }
) { innerPadding ->
    JokeScreen(Modifier.padding(innerPadding))
}
```

---

## 📌 Sonuç
Bu proje, MVVM ve Jetpack Compose kullanarak modern bir Android uygulamasının nasıl geliştirileceğini göstermektedir. 🚀


