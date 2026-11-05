# CampusConnect — Final POE Release Notes

**Module:** PROG7314 — Programming 3D
**Project:** CampusConnect
**Release:** Final Portfolio of Evidence
**Status:** In development — pending final feature verification

## 1. Release Overview

CampusConnect is a native Android application developed using Kotlin, supported by a custom ASP.NET Core REST API and SQL Server database.

The Final POE builds upon the Part 2 prototype, focusing on advanced functionality, application reliability, production readiness and automated testing.

This document records confirmed development improvements and will be updated with the final advanced features before submission.

## 2. Confirmed Development Updates

### 2.1 Source Code Management

The Android frontend and ASP.NET Core backend have been integrated into the shared GitHub repository.

The repository includes application source code, backend services, unit tests and project documentation.

### 2.2 Continuous Integration

GitHub Actions has been configured to automatically build and test both the Android frontend and ASP.NET Core backend.

The CI pipeline includes:

- Android unit testing using Gradle
- Android debug application builds
- Backend dependency restoration
- ASP.NET Core solution builds
- Backend unit testing

Both CI jobs have passed during development.

### 2.3 Documentation Improvements

The repository README has been expanded to document the application architecture, development tools, backend integration, automated testing and final release requirements.

## 3. Advanced Features — Pending Verification

The following final-assessment features will be documented once their implementation has been confirmed:

- Single Sign-On and biometric authentication
- Offline mode with automatic synchronisation
- Firebase Cloud Messaging notifications
- Multi-language support
- Cloud-hosted REST API integration
- Production application icon and visual assets

This list represents planned assessment requirements rather than confirmed completed features.

## 4. Testing and Quality Assurance

GitHub Actions automatically executes the configured build and unit-test jobs whenever changes are pushed to the main branch or a pull request targets main.

Final release verification will be performed after the group's advanced-feature development is complete.

## 5. Final Release Checklist

- [x] Shared GitHub repository established
- [x] Android frontend and backend source code included
- [x] Automated Android build and tests configured
- [x] Automated backend build and tests configured
- [x] Technical README updated for Final POE preparation
- [ ] Advanced features completed and verified
- [ ] Final application screenshots added
- [ ] Signed production APK prepared
- [ ] Final demonstration video linked
- [ ] Final GitHub Actions verification passed
- [ ] Final POE release tag created

## 6. Release Status

The Final POE release remains in development.

The final release tag will be created only after all required features, documentation and submission evidence have been completed and verified.
