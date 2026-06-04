# Easy Time Tracking ⏱️

A clean, lightweight, and offline-first Android application designed for professionals, freelancers, and students who need a seamless way to log, analyze, and manage their productivity. **Easy Time Tracking** focuses on simplicity, eliminating complex setups to let you start tracking your tasks in a single tap.

[![Platform](https://img.shields.io/badge/Platform-Android-3DDC84?style=flat-square&logo=android&logoColor=white)](https://developer.android.com/)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin-7F52FF?style=flat-square&logo=kotlin&logoColor=white)](https://kotlinlang.org/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg?style=flat-square)](LICENSE)

---

## ✨ Features

* **One-Tap Tracking:** Start and stop tracking tasks instantly from the monitor dashboard.
* **Intelligent State Management:** Automatically filters active tasks to prevent duplicate timers and tracks historical entries.
* **Insightful Analytics:** Visualize your time distribution with clean, interactive bar charts and categorical breakdowns.
* **History & Manual Logs:** Easily add, edit, or delete past time entries with a dedicated historical view.
* **Backup & Restore:** Robust CSV import/export functionality to keep your data safe or migrate it between devices.
* **Privacy-First & Offline:** Your data stays entirely on your device. No cloud syncing, no accounts required, and zero tracking scripts.
* **Material You Design:** A modern user interface featuring dynamic color-theming that adapts to your system settings, supporting both Light and Dark modes.
* **Smart Navigation:** Remembers your last visited screen for a seamless "pick up where you left off" experience using Jetpack DataStore.

---

## 🛠️ Tech Stack & Architecture

The application is built following modern Android development best practices, leveraging a robust, reactive, and scalable stack:

* **Language:** [Kotlin](https://kotlinlang.org/) - 100% type-safe and expressive.
* **UI Layer:** [Jetpack Compose](https://developer.android.com/jetpack/compose) - Declarative UI with `Material3` and `Navigation-Compose`.
* **Architecture:** Clean Architecture with **MVVM (Model-View-ViewModel)** design pattern.
* **Dependency Injection:** [Koin](https://insert-koin.io/) - A pragmatic lightweight dependency injection framework for Kotlin.
* **Local Database:** [Room Persistence Library](https://developer.android.com/topic/libraries/architecture/room) - SQLite abstraction for local data storage.
* **Preference Storage:** [Jetpack DataStore](https://developer.android.com/topic/libraries/architecture/datastore) - Modern replacement for SharedPreferences, used for UI state persistence.
* **Asynchrony:** [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) & [Flow](https://kotlinlang.org/docs/flow.html) - For reactive state management and non-blocking database operations.
* **Logging:** [Timber](https://github.com/JakeWharton/timber) - A clean and extensible logging utility.

---

## 📂 Repository Structure



---

## 🚀 Getting Started

### Prerequisites

* **Android Studio** Ladybug (2024.2.1) or newer.
* **Android SDK** 26 (Android 8.0 Oreo) or higher (Target SDK: 34+).
* **Gradle JDK:** Java 17.

### Installation & Setup

1. **Clone the repository:**
   