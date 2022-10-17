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

## Architectural approach
TODO 

## Trade-offs
TODO

## How to Run 
TODO

## 3rd Party Libraries + other Sources
- Retrofit + Okhttp
- Moshi
- Dagger (Dependency Injection)
- kotlin-result (Monads ♥️)
- Referenced Google's "[Using Dagger in your Android app](https://github.com/googlecodelabs/android-dagger)" CodeLab for a refresher on using Dagger.
- Referenced Google [StateFlow and SharedFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow)
- Borrowed `CoroutineTestRule.kt` & `ViewModelTestRule.kt` from a few past projects, which were probably borrowed from StackOverflow 😅

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

