# [stock-list-app](https://github.com/vhenri/stock-list-app)
A Simple Android app to fetch and display stocks + related data.

## Intro
This is a simple android app that fetches stock information from an api and displays the stock + related information in a scrollable list.
To do this, the app leverages the following libraries:
- Retrofit + Okhttp
- Moshi 
- Dagger
- kotlin-result (Monads ♥️)

## Requirements
App Needs to:
- Handle loading, error, and empty states
- Display the returned information in a list (feel free to add whatever UI touches you'd like)
- Have unit tests that protect against regressions (don't worry about snapshot or UI tests)

## Architectural Approach + Trade-offs
### Structure & Data Flows
This app is a one module, a Single-Activity application; this means additional screens would be added as fragments from the main activity. Navigation is implemented using Android Jetpack's Navigation Component. The nav-graph can be found in `res/navigation/main_nav_graph.xml`. Screens are created in xml and ViewBinding is used to access layout elements.  

This app uses Model-View-ViewModel (MVVM) architecture with Unidirectional Data flows (UDF):
```
+------------+    +------------+    +-----------+    +----------+
| Api Client +--->| Repository +--->| ViewModel +--->| Fragment |
+------------+    +------------+    +-----------+    +----------+
```

The app uses state flows to notify Fragment listeners of updates from the ViewModel (uses `_stateflow.update{}`) and the Fragment calls ViewModel functions to initiate actions. State Flows are hot and this was chosen for a variety of reasons: 
- it's a simpler, garbage-free implementation (vs channels) 
- it always has a value which can be safely read at any time via value property
- it has a clear separation into a read-only StateFlow interface and a MutableStateFlow.

Screens have 2 state flows: uiState and isLoading. UiState contains all the data required for the screen's main functionality (list of stocks, error data) and isLoading is a UI "side effect" that doesn't impact the overall application state.

### Networking 
Retrofit + OkHttp is used to grab data from the APIs, and Moshi parses the data for us. API responses are converted into a **result monad** at the ApiClient layer (using kotln-result). This is done to allow us to easily handle errors only when we actually need the data value. In this app, that's the ViewModel. The app does also have custom exceptions (see `StockApiException`) to provide more granularity around errors, and can be extended as needed.

### Dependency Injection
Dependency Injection is done with just *vanilla* Dagger. While Moshi does do annotation processing on top of Google's KSP, KAPT is still required for dagger. Since this is a pretty small and un-complicated app, it works just fine for this use-case. To extend / grow this app, Anvil would be a great option to consider adding as it would improve build times and probably cause less cashing-related headaches since KAPT wouldn't be required.

### Testing
Only Unit Tests are included in this project and therefore business logic is what's being tested. The following libraries are used to test: 
- Junit
- Mockk 
- kotlinx-coroutines-test
- CashApp Turbine (super helpful for those state flows!)

Again due to the size and low complexity of this app, mocks were used over stubs/fakes. For a more complex application (e.g. data coming in from multiple repositories/use-cases etc.), using Stubs or Fakes alongside Test Fixtures, would provide the most reliable and extensible testing implementation.

## How to Run
### Requirements 
- The latest version of Android Studio. This app was developed using `Android Studio Electric Eel | 2022.1.1 Beta 2` and has also been tested on `Android Studio Dolphin | 2021.3.1 Patch 1` so one of those should work just fine to build the app
- A physical android device or an emulated device using android studio
- JDK 18

### Running the App
1. Download & extract the project. Open in Android studio.
2. Build the app -  `./gradlew :app:assemble`
3. Run the app -  `./gradlew :app:installDebug` (Make sure you have a device connected or an emulator running!)
4. Run Unit Tests - `./gradlew test`

## 3rd Party Libraries + other Sources
- Retrofit + Okhttp
- Moshi
- Dagger
- kotlin-result (Monads ♥️)
- Coroutines

- Referenced Google's [Using Dagger in your Android app](https://github.com/googlecodelabs/android-dagger) CodeLab for a refresher on using Dagger.
- Referenced Google [StateFlow and SharedFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow)
- Borrowed `CoroutineTestRule.kt`,`ViewModelTestRule.kt` & BaseApiClient's `execute` from a few past projects ... which were probably borrowed from StackOverflow 😅
- Images are from [unDraw](https://undraw.co/)

## Other information / Thoughts
At my day job, I work on a rather large application with multiple modules, a robust networking layer and lots of helpful classes. Building this app out has reminded me that I'm spoiled and it's good to revisit the basics once in a while :)

Anyways, the dependencies in this app are what I feel is the some-what minimum needed to create an app that is flexible enough to grow from and can be easily tested. A few more wish-list items not included in this app for brevity/conciseness include:
- Anvil
- LeakCanary (gotta plug those leaks! or at least be aware of them...)
- Timber (better logging)
- Linting (good developer hygiene)

## Demo

https://user-images.githubusercontent.com/12767035/196528924-a0979f1c-3406-4b88-aaf6-1e81d5483e46.mov



