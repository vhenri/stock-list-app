# stock-list-app
A Simple Android app to fetch and display stocks + various related data

## Intro
This is a simple android app that fetches stock information from a remote api and displays the stock + related information in a neat little list.
To do this, the app leverages the following libraries:
- Retrofit + Okhttp
- Moshi 
- Dagger (Dependency Injection)
- kotlin-result (Monads ♥️)

## Requirements
App Needs to:
- Handle loading, error, and empty states
- Display the returned information in a list (feel free to add whatever UI touches you'd like)
- Have unit tests that protect against regressions (don't worry about snapshot or UI tests)

## Architectural Approach
This app uses Model-View-ViewModel (MVVM) architecture. Data flows in Unidirectional manner (UDF) like this:
+------------+    +------------+    +-----------+    +----------+
| Api Client +--->| Repository +--->| ViewModel +--->| Fragment |
+------------+    +------------+    +-----------+    +----------+

Retrofit + OkHttp is used to grab data from the APIs, and Moshi parses the data for us. API responses are converted into a **result monad** at the ApiClient layer (using kotln-result). This is done to allow us to easily handle errors only when we actually need the data value. In this app, that's the ViewModel. The app does also have custom exceptions (see `StockApiException`) to provide more granularity around errors, and can be extended as needed.

Once data arrives at the viewmodel, it's stored in a stateflow to notify the UI. This hot-flow was chosen as it takes an initial value through constructor and emits it immediately when someone starts collecting; and only emits the last known value. 

Dependency Injection is done with just vanilla Dagger. Since this is a pretty small and un-complicated app, it works just fine for this use-case. To extend / grow this app, Anvil would be a great option to consider adding! 

## Trade-offs
TODO

## How to Run 
TODO
 ### Requirements 
- The latest version of Android Studio 
Android Studio Electric Eel | 2022.1.1 Beta 2
Build #AI-221.6008.13.2211.9113387, built on September 27, 2022
Runtime version: 11.0.15+0-b2043.56-8887301 aarch64
VM: OpenJDK 64-Bit Server VM by JetBrains s.r.o.
macOS 12.6

## 3rd Party Libraries + other Sources
- Retrofit + Okhttp
- Moshi
- Dagger (Dependency Injection)
- kotlin-result (Monads ♥️)
- Referenced Google's "[Using Dagger in your Android app](https://github.com/googlecodelabs/android-dagger)" CodeLab for a refresher on using Dagger.
- Referenced Google [StateFlow and SharedFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow)
- Borrowed `CoroutineTestRule.kt` & `ViewModelTestRule.kt` from a few past projects, which were probably borrowed from StackOverflow 😅
- Images are from [unDraw](https://undraw.co/)

## Other information / Thoughts
At my day job, I work on a rather large application with multiple modules, a robust networking layer and lots of helpful classes. Building this app out has reminded me that I'm spoiled and it's good to revisit the basics once in a while :)

Anyways, the dependencies in this app are what I feel is the some-what minimum needed to create an app that is flexible enough to grow from and can be easily tested. A few more wish-list items not included in this app for brevity + conciseness include:
- Anvil (or Hilt, but we're Anvil fans here)
- LeakCanary (gotta plug those leaks! or at least be aware of them...)
- Timber (better logging)
- Coil (pretty images)

## TODO
[ ] - Add loading state / error state / empty state
[ ] - Add a button to switch api endpoints (to demonstrate above states)
[ ] - Add Unit tests

