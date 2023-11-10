<a name="readme-top"></a>

<p align="center">
  <a>
    <img src="app/src/main/res/mipmap-xxxhdpi/ic_launcher.png" alt="Doodle App Icon" >
  </a>
</p>

<p align="center">
  <a href="https://github.com/enesky/Doodle/blob/main/LICENSE"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://renovatebot.com/"><img alt="Renovate" src="https://img.shields.io/badge/RenovateBot-enabled-light_green.svg"/></a>
  <a href="https://github.com/enesky/Doodle/actions/workflows/main.yml"><img alt="Lint Checks & Build" src="https://github.com/enesky/Doodle/actions/workflows/main.yml/badge.svg"/></a>
</p>

<p align="center">
  <a href="https://android-arsenal.com/api?level=24"><img alt="API" src="https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=flat"/></a>
  <a><img alt="Kotlin" src="https://img.shields.io/badge/Kotlin-1.9.10-purple.svg?style=flat"/></a>
  <a><img alt="JVM" src="https://img.shields.io/badge/JVM-17-yellow.svg?style=flat"/></a>
  <a><img alt="AGP" src="https://img.shields.io/badge/AGP-8.3.0_alpha12-orange.svg?style=flat"/></a>
</p>

# Doodle

Just doodling around

Trying to apply best practices for later usages :)

Doodle demonstrates modern Android development with Koin, Coroutines, Flow, Jetpack (Compose, ViewModel, Room), and Material Design based on MVVM, Clean and Multi-Module architecture.

## Tech stack & Open-source libraries

- Minimum SDK level 24
- [Kotlin](https://kotlinlang.org/) based, [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
- Jetpack
    - Compose: Android’s recommended modern toolkit for building native UI. 
    - Lifecycle: Observe Android lifecycles and handle UI states upon the lifecycle changes.
    - ViewModel: Manages UI-related data holder and lifecycle aware. Allows data to survive configuration changes such as screen rotations.
    - DataBinding: Binds UI components in your layouts to data sources in your app using a declarative format rather than programmatically.
    - Room: Constructs Database by providing an abstraction layer over SQLite to allow fluent database access.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) -
  Collection of libraries that help you design robust, testable, and maintainable apps.
    - [Stateflow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow) - StateFlow is
      a state-holder observable flow that emits the current and new state updates to its collectors.
    - [Flow](https://kotlinlang.org/docs/reference/coroutines/flow.html) - A flow is an asynchronous
      version of a Sequence, a type of collection whose values are lazily produced.
    - [Compose Destinations Navigation](https://developer.android.com/jetpack/compose/navigation) - 
      Simplified and type-safe navigation for Compose.
    - [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) - Jetpack
      DataStore is a data storage solution that allows you to store key-value pairs or typed objects
      with protocol buffers. DataStore uses Kotlin coroutines and Flow to store data asynchronously,
      consistently, and transactionally.
    - [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager) - 
      Schedule automatically repeating or guarantee one-time background processing.
- Firebase
    - Analytics: for tracking events.
    - Crashlytics: for tracking crashes.
    - Performance: for tracking performance.
    - Remote Config: for remote configuration.
    - Authentication: for user authentication.
- Architecture
    - MVVM Architecture (View - DataBinding - ViewModel - Model)
    - Clean Architecture approach (Data - Domain - Presentation)
    - Multi-Module Architecture with lots of customized convention plugins (build_logic - app - core - feature)
- [Koin](https://insert-koin.io/): for dependency injection.
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit): Construct the REST APIs and paging network data.
- [ksp](https://github.com/google/ksp): Kotlin Symbol Processing API.
- [Material-Components](https://github.com/material-components/material-components-android): Material design components for building ripple animation, and CardView.
- [Coil](https://github.com/coil-kt/coil) for async image loading library
- [Detekt](https://github.com/detekt/detekt) for static code analysis with [compose specific rules](https://github.com/mrmans0n/compose-rules/tree/main)
- [Spotless](https://github.com/diffplug/spotless) and [Ktlint](https://github.com/pinterest/ktlint) for lint check and auto formatting
- [RenovateBot](https://github.com/renovatebot/renovate) for auto dependency update tool
- [GitHub Actions](https://github.com/enesky/Doodle/actions) for simple ci pipeline
- [LeakCanary](https://github.com/square/leakcanary) for leak detection on runtime
- [Chucker](https://github.com/ChuckerTeam/chucker) for network inspector on debug builds

## How to run Doodle locally

- In order to run the app, you need to create a `local.properties` file in the root folder of the project.  
Here is how my current `local.properties` looks like:

```properties
doodle.api.url="https://api.jikan.moe/v4/"
doodle.api.key="sample api key" 
#Jikan API doesn't require an API key, but I'm using it as an example for later usages.
```

- Ktlint should be added using brew

```console
brew install ktlint
```

## Open API

<a href="https://jikan.moe/"><img align=right height=150 width= 150 alt="Jikan API" src="https://jikan.moe/assets/images/logo/jikan.logo.png"/></a>

[Jikan](https://jikan.moe/) (時間) is an open-source PHP & REST API for the “most active online anime + manga community and database” — MyAnimeList.net.
It parses the website to satisfy the need for an API.
Jikan is a free, open-source API.   

Used endpoints:  
- [Top Anime](https://api.jikan.moe/v4/top/anime/1/)  
- [Anime Details](https://api.jikan.moe/v4/anime/1)  
- [Anime Characters](https://api.jikan.moe/v4/anime/1/characters)  

# License
```xml
/*
 *                          Copyright 2023
 *            Designed and developed by Enes Kamil Yılmaz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
```

<p align="center">(<a href="#readme-top">back to top</a>)</p>
