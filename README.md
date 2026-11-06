# CampusConnect

CampusConnect is a native Android application developed for the PROG7314 Portfolio of Evidence. The application gives students access to campus information, events, societies, academic resources, services, announcements, lost-and-found listings, settings, and other student-focused features from one mobile interface.

## Project Information

- Application name: CampusConnect
- Package name: `com.example.campusconnect`
- Platform: Native Android
- Language: Kotlin
- User interface: Jetpack Compose
- Minimum SDK: 26
- Target SDK: 36
- Compile SDK: 37

## Group Members

| Group Member | Student Number |
|---|---|
| Lushen | ST10439056 |
| Azhar | ST10327814 |
| Salman | ST10265136 |

---

## Main Features

CampusConnect includes the following application features:

- Campus account login
- Student dashboard
- Campus events
- Societies
- Lost and found
- Announcements
- Academic resources
- Campus services
- Student leaderboard
- Application settings
- English and isiZulu language support
- Biometric authentication
- Offline data storage and synchronisation
- Firebase Cloud Messaging notifications
- Custom application icon

## Advanced Features

### Biometric Authentication

CampusConnect supports native Android biometric authentication. Users can sign in using a fingerprint or another biometric method supported by their physical Android device.

The application checks whether biometric hardware is available and whether a biometric credential has been enrolled before displaying the biometric sign-in option. Standard login remains available as a fallback.

### Offline Mode and Synchronisation

Lost-and-found data can be stored locally when the device has no network connection. The offline architecture uses a local SQLite database and WorkManager.

When connectivity becomes available, the background synchronisation worker processes pending records. The repository and network layers provide the integration point for the group’s cloud-hosted REST API.

### Real-Time Notifications

Firebase Cloud Messaging is used to receive campus notifications and alerts. The application:

- Retrieves an FCM registration token
- Subscribes to the `campus_updates` topic
- Requests notification permission on supported Android versions
- Displays notifications using an Android notification channel
- Handles new Firebase registration tokens
- Opens CampusConnect when a notification is selected

### Multi-Language Support

CampusConnect supports:

- English
- isiZulu

All visible application text is stored in Android string resources. Users can change the language from the Settings screen, and the selected language is retained on the device.

### Production Assets

CampusConnect contains a custom adaptive launcher icon with legacy, round, monochrome, and Play Store versions.

## Technical Architecture

The Android project is organised into the following areas:

- `data` — repositories, local database access and background synchronisation
- `model` — application and offline data models
- `network` — Retrofit API definitions and network configuration
- `notifications` — Firebase Cloud Messaging service
- `ui` — Jetpack Compose screens, navigation and application state
- `ui/theme` — application colours, typography and Material theme
- `res/values` — English string resources
- `res/values-zu` — isiZulu string resources

## Technologies and Libraries

- Kotlin
- Jetpack Compose
- Material 3
- AndroidX Biometric
- SQLite
- Android WorkManager
- Firebase Cloud Messaging
- Retrofit
- Gson
- Kotlin Coroutines
- Android DataStore
- Android lifecycle and ViewModel libraries
- JUnit
- AndroidX Test
- Espresso
- GitHub Actions

## Firebase Configuration

CampusConnect uses Firebase Cloud Messaging.

The Android Firebase application must use this package name:

```text
com.example.campusconnect
