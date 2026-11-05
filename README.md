# CampusConnect

[![CampusConnect CI](https://github.com/salman-mia/PROG7314-part-2/actions/workflows/android-ci.yml/badge.svg)](https://github.com/salman-mia/PROG7314-part-2/actions/workflows/android-ci.yml)

## PROG7314 — Programming 3D | Final Portfolio of Evidence

CampusConnect is a native Android application developed as part of the PROG7314 Portfolio of Evidence.

The application provides students with a central platform for accessing campus information, academic resources, events and student services.

The project consists of a Kotlin Android frontend, a custom ASP.NET Core REST API and a SQL Server database. This repository contains the application source code, backend source code, automated tests, technical documentation and continuous integration configuration.

**Project status:** Final POE development in progress.

---

## Group Members

| Group Member | Student Number |
|---|---|
| Lushen | ST10439056 |
| Azhar | ST10327814 |
| Salman | ST10265136 |

---

## 1. Purpose and Scope

CampusConnect aims to improve student access to campus-related information and services through a single mobile application.

The project includes functionality relating to:

- User registration and authentication
- Campus events and RSVP management
- Academic resources
- Announcements
- Student societies
- Lost and Found
- Campus services
- Student leaderboard
- User settings

The application is designed to integrate selected features with the custom REST API and database.

### Final POE Development Requirements

The final assessment includes the following advanced capabilities, which must be verified before final release:

- Single Sign-On authentication
- Biometric authentication
- Offline functionality with data synchronisation
- Firebase Cloud Messaging notifications
- Support for at least two official South African languages
- Cloud-hosted REST API integration
- Custom application icon and production assets

These items are assessment requirements, not claims of completed implementation.

---

## 2. Technology Stack

### Android Frontend

- Kotlin
- Android Studio
- Jetpack Compose
- Retrofit
- OkHttp
- Gson
- Kotlin Coroutines
- Gradle

### Backend

- ASP.NET Core Web API
- C#
- Entity Framework Core
- SQL Server
- JWT-based API authentication
- Swagger / OpenAPI

### DevOps

- Git
- GitHub
- GitHub Actions
- Gradle automated builds
- .NET automated builds
- Android and backend unit testing

---

## 3. Application Architecture

CampusConnect uses a layered architecture separating presentation, data access, networking and backend functionality.

```text
Android Application
        |
        v
Jetpack Compose UI
        |
        v
CampusRepository
        |
        v
Retrofit / ApiClient
        |
        v
ASP.NET Core REST API
        |
        v
Entity Framework Core
        |
        v
SQL Server Database
```

The Android application communicates with the backend through REST endpoints. The backend processes supported requests and uses Entity Framework Core to access persistent data.

The final deployment architecture will be documented once the cloud-hosting configuration is confirmed.

---

## 4. Repository Structure

```text
CampusConnect/
|
|-- .github/
|   `-- workflows/
|       `-- android-ci.yml
|
|-- app/
|   `-- src/
|       |-- main/
|       |-- test/
|       `-- androidTest/
|
|-- CampusConnect.Backend/
|   |-- CampusConnect.API/
|   |-- tests/
|   `-- CampusConnect.sln
|
|-- gradle/
|-- build.gradle.kts
|-- gradle.properties
|-- gradlew
|-- gradlew.bat
|-- settings.gradle.kts
`-- README.md
```

The repository contains the Android frontend and Visual Studio backend within one shared version-control project.

---

## 5. REST API Integration

The Android application uses Retrofit to communicate with the custom ASP.NET Core REST API.

API functionality developed during the prototype includes:

- User registration and login
- Retrieving campus events
- Creating event RSVPs
- Cancelling event RSVPs
- Retrieving academic resources

Example data flow:

```text
Android App
    |
    | REST API Request
    v
ASP.NET Core API
    |
    v
SQL Server Database
    |
    | API Response
    v
Android App
```

The final POE requires cloud-hosted API and database integration. Deployment evidence will be added when the final infrastructure is available.

---

## 6. Version Control and Collaboration

Git and GitHub are used to manage source code, document changes and support group collaboration.

Development changes are committed with descriptive messages and pushed to the shared repository.

Examples of development commits include:

```text
Add CampusConnect Android application
Add GitHub Actions CI workflow
Fix academic resources API integration
Update project documentation and CI details
Add CampusConnect ASP.NET Core backend and tests
Add backend build and automated tests to CI
```

The repository's Git history provides a record of development activity.

### Final POE Release Tag

The final deployment commit will be tagged after all required features, documentation and verification tasks are complete.

**Release tag:** Pending final group verification.

---

## 7. Continuous Integration and Automated Testing

CampusConnect uses GitHub Actions to automatically build and test the application when changes are pushed to the `main` branch or a pull request targets `main`.

Workflow location:

```text
.github/workflows/android-ci.yml
```

### Android Build and Test

The Android job:

1. Checks out the repository.
2. Configures JDK 17.
3. Configures Gradle.
4. Executes Android unit tests.
5. Builds the Android debug application.

Commands:

```bash
./gradlew test
./gradlew assembleDebug
```

### ASP.NET Core Backend Build and Test

The backend job:

1. Checks out the repository.
2. Configures the .NET SDK.
3. Restores backend dependencies.
4. Builds the backend solution.
5. Executes backend unit tests.

Commands:

```bash
dotnet restore CampusConnect.Backend/CampusConnect.sln
dotnet build CampusConnect.Backend/CampusConnect.sln --configuration Release --no-restore
dotnet test CampusConnect.Backend/CampusConnect.sln --configuration Release --no-build
```

### CI Verification

Both jobs have completed successfully during development:

- Android Build and Test — Passed
- Backend Build and Test — Passed

These results confirm that the configured build and unit-test tasks completed successfully in the GitHub Actions environment.

The final release will require another successful CI run after all development changes have been merged.

[View GitHub Actions](https://github.com/salman-mia/PROG7314-part-2/actions)

---

## 8. Building and Testing Locally

### Android Requirements

- Android Studio
- Android SDK
- Compatible JDK
- Internet access for dependency restoration

### Clone Repository

```bash
git clone https://github.com/salman-mia/PROG7314-part-2.git
```

Open the cloned repository in Android Studio and allow Gradle to synchronise.

### Android Build

```bash
./gradlew assembleDebug
```

On Windows:

```powershell
.\gradlew.bat assembleDebug
```

### Android Unit Tests

```bash
./gradlew test
```

On Windows:

```powershell
.\gradlew.bat test
```

### Backend

Open the following solution in Visual Studio:

```text
CampusConnect.Backend/CampusConnect.sln
```

Alternatively, use the .NET CLI:

```bash
dotnet restore CampusConnect.Backend/CampusConnect.sln
dotnet build CampusConnect.Backend/CampusConnect.sln
dotnet test CampusConnect.Backend/CampusConnect.sln
```

The backend requires a compatible .NET SDK and an appropriately configured database connection for functionality that accesses SQL Server.

Do not commit production secrets, database passwords or private credentials to the repository.

---

## 9. Final Release Notes

The final release notes will document the functional changes made since the Part 2 prototype.

The completed release documentation will identify:

- Newly implemented advanced features
- Authentication improvements
- Offline synchronisation capabilities
- Notification integration
- Language and accessibility enhancements
- Cloud deployment changes
- User interface and production asset improvements
- Testing and release verification results

**Status:** Awaiting final feature implementation and group verification.

---

## 10. Screenshots and Technical Evidence

Final evidence should include relevant screenshots of:

- Login and authentication screens
- CampusConnect home screen
- Campus events and RSVP functionality
- Academic resources
- Settings and preferences
- Completed advanced features
- Successful GitHub Actions runs
- REST API functionality
- Backend database updates
- Final production application assets

Screenshots will be added once the final application has been verified.

---

## 11. Demonstration Video

The Final POE requires a narrated demonstration of the completed application running on a physical mobile device.

The video must demonstrate the relevant authentication flows, application settings, cloud API integration, offline synchronisation and completed application features.

**Final POE demonstration video:** Pending recording and upload.

The final video URL will be inserted here before submission.

---

## 12. Production Release and Publication Readiness

The final submission requires evidence of Google Play Store publication readiness, including:

- Signed production APK
- Promotional screenshots
- Final application icon and visual assets

**Status:** Pending production build and final verification.

---

## 13. AI Tooling Declaration

Any generative AI assistance used during development, documentation, debugging or asset generation will be disclosed in the final submission according to the assessment requirements.

The final declaration will describe the tools used, their purpose and the group's verification of generated outputs.

---

## 14. Repository

**GitHub:** https://github.com/salman-mia/PROG7314-part-2

**CI Workflow:** https://github.com/salman-mia/PROG7314-part-2/actions

---

## 15. Academic Declaration

CampusConnect was developed for academic purposes as part of the PROG7314 Portfolio of Evidence.

This repository contains project source code, development history, technical documentation, testing infrastructure and Continuous Integration configuration.

Final submission evidence will be completed and verified by the group before release.
