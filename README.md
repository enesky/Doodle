<a name="readme-top"></a>

<p align="center">
  <a>
    <img src="app/src/main/res/mipmap-xxxhdpi/ic_launcher.png" alt="Doodel App Icon" >
  </a>
</p>

<p align="center">
  <a href="https://renovatebot.com/"><img alt="Renovate" src="https://img.shields.io/badge/RenovateBot-not_enabled-red.svg"/></a>
  <a href="https://github.com/enesky/Doodle/actions/workflows/main.yml"><img alt="Lint Checks & Build" src="https://github.com/enesky/Doodle/actions/workflows/main.yml/badge.svg"/></a>
  <!-- Add more -->
</p>

<p align="center">
  <a href="https://github.com/enesky/Doodle/blob/main/LICENSE"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=24"><img alt="API" src="https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=flat"/></a>
  <!-- Add more -->
</p>

# Doodle

Just doodling around

Trying to apply best practices for later usages :)

<p align="center">  
🗡️ Doodle demonstrates modern Android development with Koin, Coroutines, Flow, Jetpack (Room, ViewModel), and Material Design based on MVVM and Clean architecture.
</p>

## Tech stack & Open-source libraries

- Minimum SDK level 24
- [Kotlin](https://kotlinlang.org/) based, [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
- Jetpack
    - Lifecycle: Observe Android lifecycles and handle UI states upon the lifecycle changes.
    - ViewModel: Manages UI-related data holder and lifecycle aware. Allows data to survive configuration changes such as screen rotations.
    - DataBinding: Binds UI components in your layouts to data sources in your app using a declarative format rather than programmatically.
    - Room: Constructs Database by providing an abstraction layer over SQLite to allow fluent database access.
    - [Koin](https://insert-koin.io/): for dependency injection.
- Firebase
    - Analytics: for tracking events.
    - Crashlytics: for tracking crashes.
    - Performance: for tracking performance.
    - Remote Config: for remote configuration.
- Architecture
    - MVVM Architecture (View - DataBinding - ViewModel - Model)
    - Clean Architecture approach (Data - Domain - Presentation)
    - Repository Pattern
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit): Construct the REST APIs and paging network data.
- [ksp](https://github.com/google/ksp): Kotlin Symbol Processing API.
- [Material-Components](https://github.com/material-components/material-components-android): Material design components for building ripple animation, and CardView.

## Used API -> [Jikan API](https://docs.api.jikan.moe/)

[Jikan API](https://docs.api.jikan.moe/) is an Unofficial MyAnimeList API.
It scrapes the website to satisfy the need for a complete API - which MyAnimeList lacks.
Jikan is a free, open-source API.

Used endpoints:
-> [Top Anime](https://api.jikan.moe/v4/top/anime/1/)
-> [Anime Details](https://api.jikan.moe/v4/anime/1)
-> [Anime Characters](https://api.jikan.moe/v4/anime/1/characters)

## How to run the app on local

In order to run the app, you need to create a `local.properties` file in the root folder of the project.  
Here is how my current `local.properties` looks like:

```properties
doodle.api.url="https://api.jikan.moe/v4/"
doodle.api.key="sample api key" 
#Jikan API doesn't require an API key, but I'm using it as an example for later usages.
```

<p align="center">(<a href="#readme-top">back to top</a>)</p>
