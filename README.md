# GNews - A Simple News App

GNews is a Kotlin Multiplatform project designed to deliver a seamless news browsing experience across Android and iOS platforms. It leverages modern libraries and frameworks to ensure a robust and efficient application.

## Features

- **Compose Multiplatform**: Unified UI development for Android and iOS.
- **Ktor**: Handles remote API calls for fetching news data.
- **Kotlinx Serialization**: Simplifies JSON serialization and deserialization.
- **Coroutines**: Enables asynchronous operations for smooth performance.
- **Koin**: Dependency injection framework for better modularity and testability.
- **Kamel**: Efficient image loading and caching.

## Project Structure

### `/composeApp`
This module contains shared code for the Compose Multiplatform UI. It includes:

- `commonMain`: Houses code common to all platforms.
- Platform-specific folders (e.g., `iosMain`, `androidMain`): For platform-specific implementations, such as integrating native APIs.

### `/iosApp`
This module contains the iOS-specific entry point. It is also where you can add SwiftUI code if needed.

## Screenshots

Here are some snapshots of the app:

|               Light theme               |                Dark theme               |
|-----------------------------------------|-----------------------------------------|
| ![Android](snaps/android_light.png)     | ![Android](snaps/android_dark.png)      |
| ![iOS](snaps/ios_light.png)             | ![iOS](snaps/ios_dark.png)              |

## Learn More

To get started with Kotlin Multiplatform, visit the [official documentation](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html).