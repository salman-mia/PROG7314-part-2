# CampusConnect

![Android CI](https://github.com/salman-mia/PROG7314-part-2/actions/workflows/android-ci.yml/badge.svg)

## PROG7314 - Programming 3C - Part 2

CampusConnect is a native Android application developed as part of the PROG7314 Programming 3C Portfolio of Evidence.

The application is designed to provide students with a central platform for accessing campus-related information and services. The prototype combines a Kotlin Android frontend with a custom ASP.NET Core REST API and database-backed functionality.

---

## Group Members

| Student | Student Number |
|---|---|
| Lushen | ST10439056 |
| Azhar | ST10327814 |
| Salman | ST10265136 |

---

## Purpose and Scope

The purpose of CampusConnect is to provide students with a single mobile application through which they can access useful campus information and interact with selected campus services.

The Part 2 prototype includes functionality relating to:

- User authentication
- Campus events
- Event RSVP functionality
- Academic resources
- Announcements
- Societies
- Lost and Found
- Campus services
- Student leaderboard
- User settings

The application has been structured so that prototype functionality can progressively be connected to the custom REST API and database.

---

## Technology Stack

### Android Application

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

### DevOps and Version Control

- Git
- GitHub
- GitHub Actions
- Gradle automated builds
- Automated unit testing

---

## Application Architecture

CampusConnect follows a layered structure that separates the user interface, application data, networking and backend functionality.

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

This structure improves maintainability by separating the presentation layer from API communication and data-management responsibilities.

---

## Project Structure

```text
CampusConnect
|
|-- .github/
|   `-- workflows/
|       `-- android-ci.yml
|
|-- app/
|   `-- src/
|       |-- main/
|       |   `-- java/com/example/campusconnect/
|       |       |-- auth/
|       |       |-- data/
|       |       |-- model/
|       |       |-- network/
|       |       `-- ui/
|       |
|       |-- test/
|       `-- androidTest/
|
|-- gradle/
|-- build.gradle.kts
|-- gradle.properties
|-- gradlew
|-- gradlew.bat
|-- settings.gradle.kts
`-- README.md
```

---

## REST API Integration

CampusConnect uses Retrofit to communicate with the custom ASP.NET Core REST API.

The networking layer is responsible for sending requests to the backend and converting JSON responses into Kotlin objects used by the application.

API functionality implemented during development includes:

- User login and registration
- Retrieving campus events
- Creating an event RSVP
- Cancelling an event RSVP
- Retrieving academic resources

The event functionality demonstrates communication between the Android application, REST API and SQL Server database.

For example:

```text
Android App
    |
    | POST RSVP
    v
ASP.NET Core API
    |
    v
SQL Server
    |
    | Updated RSVP information
    v
REST API
    |
    v
Android App
```

---

## Version Control

Git and GitHub are used for source control and collaborative development.

The project follows a version-control workflow where changes are committed with descriptive commit messages and pushed to the central GitHub repository.

Example commit messages include:

```text
Add CampusConnect Android application
Add GitHub Actions CI workflow
Fix academic resources API integration
Update project documentation and CI details
```

This provides a history of changes made throughout development and allows multiple contributors to collaborate through the shared repository.

---

## Continuous Integration with GitHub Actions

The project uses **GitHub Actions** for Continuous Integration (CI).

The workflow is located at:

```text
.github/workflows/android-ci.yml
```

The workflow is automatically triggered when code is pushed to the `main` branch and when a pull request targets `main`.

The CI pipeline performs the following steps:

1. Checks out the repository.
2. Configures the Java Development Kit.
3. Configures Gradle.
4. Grants execution permission to the Gradle wrapper.
5. Executes the project's unit tests.
6. Builds the Android debug application.

The main commands executed by the pipeline are:

```bash
./gradlew test
./gradlew assembleDebug
```

A successful GitHub Actions run confirms that the project can be compiled and that the configured unit-test task completes successfully in the CI environment.

---

## Automated Testing

Automated testing is integrated into the GitHub Actions workflow.

Every push to the `main` branch triggers:

```bash
./gradlew test
```

If compilation or a unit test fails, GitHub Actions marks the workflow as failed.

If the tests and build complete successfully, the workflow receives a green success status.

This provides automated feedback when changes are introduced into the repository and helps identify integration or compilation problems before submission.

---

## Building the Application

### Requirements

To build the Android application locally, the following are required:

- Android Studio
- Android SDK
- Java/JDK compatible with the project
- Internet access for Gradle dependencies

### Clone the Repository

```bash
git clone https://github.com/salman-mia/PROG7314-part-2.git
```

Open the cloned project in Android Studio and allow Gradle to synchronize.

### Build

The application can be built using Android Studio or Gradle:

```bash
./gradlew assembleDebug
```

On Windows PowerShell:

```powershell
.\gradlew.bat assembleDebug
```

### Run Unit Tests

```bash
./gradlew test
```

On Windows PowerShell:

```powershell
.\gradlew.bat test
```

---

## GitHub Actions Workflow

The CI workflow automatically runs for pushes and pull requests involving the `main` branch.

Current CI status is displayed at the top of this README through the GitHub Actions status badge.

The workflow can also be viewed under:

**GitHub Repository -> Actions -> CampusConnect Android CI**

---

## Screenshots

Screenshots demonstrating the application and development process can be added here.

Recommended evidence includes:

- Login screen
- CampusConnect home screen
- Campus Events screen
- Event RSVP functionality
- Academic Resources screen
- Settings screen
- Successful GitHub Actions workflow
- REST API / Swagger testing
- Database evidence

---

## Demo Video

The Part 2 demonstration video will be linked here:

**Demo Video:** `ADD VIDEO LINK HERE`

---

## Repository

Repository:

https://github.com/salman-mia/PROG7314-part-2

---

## Contributors

**Lushen**  
Student Number: ST10439056

**Azhar**  
Student Number: ST10327814

**Salman**  
Student Number: ST10265136

---

## Academic Declaration

This repository was created for academic purposes as part of the PROG7314 Programming 3C Portfolio of Evidence.

The repository contains the source code, project documentation, version-control history and Continuous Integration configuration used during the development of CampusConnect.
