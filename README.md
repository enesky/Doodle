<a name="readme-top"></a>

<p align="center">
  <a>
    <img width=400, src="https://github.com/enesky/Doodle/blob/main/core/design-system/src/main/res/drawable/doodle_icon.png" alt="Doodle Icon">
  </a>
</p>

<p align="center">
  <a href="https://github.com/enesky/Doodle/blob/main/LICENSE" target="_blank"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://renovatebot.com/" target="_blank"><img alt="Renovate" src="https://img.shields.io/badge/RenovateBot-enabled-light_green.svg"/></a>
  <a href="https://github.com/enesky/Doodle/actions/workflows/main.yml" target="_blank"><img alt="Lint Checks & Build" src="https://github.com/enesky/Doodle/actions/workflows/main.yml/badge.svg"/></a>
</p>

<p align="center">
  <a href="https://android-arsenal.com/api?level=24" target="_blank"><img alt="API" src="https://img.shields.io/badge/API-24%2B-orange.svg?style=flat"/></a>
  <a><img alt="Kotlin" src="https://img.shields.io/badge/Kotlin-1.9.20-purple.svg?style=flat"/></a>
  <a><img alt="JVM" src="https://img.shields.io/badge/JVM-17-yellow.svg?style=flat"/></a>
</p>

Just doodling around

Trying to apply best practices for later usages :)

Doodle demonstrates modern Android development with Koin, Coroutines, Flow, Jetpack (Compose, ViewModel, Room, Credential Manager), and Material Design based on MVVM, Clean and Multi-Module architecture.

## Screenshots

<p align="center">
  <a>
    <img width=400, src="https://github.com/enesky/Doodle/blob/chore/readme_update/docs/signin_screen.png" alt="Home Screen">
    <img width=400, src="https://github.com/enesky/Doodle/blob/chore/readme_update/docs/home_screen.png" alt="Home Screen">
  </a>
</p>

## Module Dependency Graph

<p align="center">
  <a>
    <img width=400, src="https://github.com/enesky/Doodle/blob/chore/readme_update/tools/dependency-graph/project_digraph.dot.webp" alt="Module Dependency Graph">
    </a>
</p>

## Tech stack & Open-source libraries

- Minimum SDK level 24
- <a href="https://kotlinlang.org/" target="_blank">Kotlin</a> based, Compose for UI Toolkit, <a href="https://github.com/Kotlin/kotlinx.coroutines" target="_blank">Coroutines</a> + <a href="https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/" target="_blank">Flow</a> for asynchronous jobs.
- Jetpack
    - <a href="https://developer.android.com/jetpack/compose" target="_blank">Compose</a>: Android’s recommended modern toolkit for building native UI.
    - <a href="https://developer.android.com/topic/libraries/architecture" target="_blank">Android Architecture Components</a>: Collection of libraries that help you design robust, testable, and maintainable apps.
        - <a href="https://developer.android.com/kotlin/coroutines" target="_blank">Coroutines</a>: Concurrency design pattern that you can use on Android to simplify code that executes asynchronously.
        - <a href="https://developer.android.com/kotlin/flow/stateflow-and-sharedflow" target="_blank">Stateflow</a>: StateFlow is a state-holder observable flow that emits the current and new state updates to its collectors.
        - <a href="https://kotlinlang.org/docs/reference/coroutines/flow.html" target="_blank">Flow</a>: A flow is an asynchronous version of a Sequence, a type of collection whose values are lazily produced.
        - <a href="https://developer.android.com/jetpack/compose/navigation" target="_blank">Compose Destinations Navigation</a>: Simplified and type-safe navigation for Compose.
        - <a href="https://developer.android.com/topic/libraries/architecture/datastore" target="_blank">DataStore</a>: Jetpack DataStore is a data storage solution that allows you to store key-value pairs or typed objects with protocol buffers. DataStore uses Kotlin coroutines and Flow to store data asynchronously, consistently, and transactionally.
        - <a href="https://developer.android.com/topic/libraries/architecture/workmanager" target="_blank">WorkManager</a>: Schedule automatically repeating or guarantee one-time background processing.
    - Firebase
        - <a href="https://firebase.google.com/products/analytics" target="_blank">Analytics</a>: for tracking events.
        - <a href="https://firebase.google.com/products/crashlytics" target="_blank">Crashlytics</a>: for tracking crashes.
        - <a href="https://firebase.google.com/products/performance" target="_blank">Performance</a>: for tracking performance.
        - <a href="https://firebase.google.com/products/remote-config" target="_blank">Remote Config</a>: for remote configuration.
        - <a href="https://firebase.google.com/products/auth" target="_blank">Authentication</a>: for user authentication.
    - Credential Manager API: Unifies the sign-in interface across authentication methods, making it clearer and easier for users to sign into apps, regardless of the method they choose.
    - Architecture
        - MVVM Architecture (View - DataBinding - ViewModel - Model)
        - Clean Architecture approach (Data - Domain - Presentation)
        - Multi-Module Architecture with lots of customized convention plugins (build_logic - app - core - feature)
    - <a href="https://insert-koin.io/" target="_blank">Koin</a>: for dependency injection.
    - <a href="https://github.com/square/retrofit" target="_blank">Retrofit2 & OkHttp3</a>: Construct the REST APIs and paging network data.
    - <a href="https://github.com/google/ksp" target="_blank">ksp</a>: Kotlin Symbol Processing API.
    - <a href="https://github.com/material-components/material-components-android" target="_blank">Material-Components</a>: Material design components for building ripple animation, and CardView.
    - <a href="https://github.com/coil-kt/coil" target="_blank">Coil</a> for async image loading library
    - <a href="https://github.com/detekt/detekt" target="_blank">Detekt</a> for static code analysis with <a href="https://github.com/mrmans0n/compose-rules/tree/main" target="_blank">compose specific rules</a>
    - <a href="https://github.com/diffplug/spotless" target="_blank">Spotless</a> and <a href="https://github.com/pinterest/ktlint" target="_blank">Ktlint</a> for lint check and auto formatting
    - <a href="https://github.com/renovatebot/renovate" target="_blank">RenovateBot</a> for auto dependency update tool
    - <a href="https://github.com/enesky/Doodle/actions" target="_blank">GitHub Actions</a> for simple ci pipeline
    - <a href="https://github.com/enesky/Doodle/tree/main/tools/git-hooks" target="_blank">GitHooks</a> for running static analysis and code formatters before commit and push actions
    - <a href="https://github.com/square/leakcanary" target="_blank">LeakCanary</a> for leak detection on runtime
    - <a href="https://github.com/ChuckerTeam/chucker" target="_blank">Chucker</a> for network inspector on debug builds

## How to run Doodle locally

- In order to run the app, you need to add these to your `local.properties` file in the root folder of the project.   

```properties
  doodle.api.url="https://api.jikan.moe/v4/"
  doodle.api.key="sample api key" #Jikan API doesn't require an API key, but I'm using it as an example for later usages.
```

- <a href="https://github.com/pinterest/ktlint" target="_blank">Ktlint</a> should be added using brew

```console
 brew install ktlint
```

## Open API
<a href="https://jikan.moe/" target="_blank"><img align=right height=150 width= 150 alt="Jikan API" src="https://jikan.moe/assets/images/logo/jikan.logo.png"/></a>

Jikan (時間) is an open-source PHP & REST API for the “most active online anime + manga community and database” — MyAnimeList.net.
It parses the website to satisfy the need for an API.
Jikan is a free, open-source API.

#### Used endpoints:

<a href="https://api.jikan.moe/v4/top/anime/1/" target="_blank">Top Anime</a>   
<a href="https://api.jikan.moe/v4/anime/1" target="_blank">Anime Details</a>    
<a href="https://api.jikan.moe/v4/anime/1/characters" target="_blank">Anime Characters</a>   

## License:

```xml
/*
 *                          Copyright 2023
 *            Designed and developed by Enes Kamil Yılmaz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *           http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
```

<p align="center">(<a href="#readme-top">back to top</a>)</p>
