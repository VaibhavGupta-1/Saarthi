# Saarthi: One-Stop Personalized Career & Education Advisor (Android App)

<!-- Optional: Add your app's logo here -->
<p align="center">
  <img width="50" height="50" alt="image" src="https://github.com/user-attachments/assets/7e13d8e6-2ba3-40a5-9a20-a5b0a07b7d2a" />

</p> 

<p align="center">
  <i>Guiding students in Jammu & Kashmir towards a brighter future through personalized career and education advice.</i>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Kotlin-1.9.21-7F52FF?logo=kotlin" alt="Kotlin">
  <img src="https://img.shields.io/badge/Jetpack%20Compose-1.6.5-4285F4?logo=jetpackcompose" alt="Jetpack Compose">
  <img src="https://img.shields.io/badge/Platform-Android-3DDC84?logo=android" alt="Platform">
  <img src="https://img.shields.io/badge/License-MIT-yellow.svg" alt="License: MIT">
</p>

---

## 📸 Screenshots & Demo

A fully-featured UI prototype built with a modern, clean, and supportive design system.

<!-- IMPORTANT: Replace these links with your own screenshots! Create a "screenshots" folder in your repo. -->
| Onboarding & Login | User Details & Quiz | Dynamic Dashboard |
| :---: | :---: | :---: |
| <img width="250"  alt="image" src="https://github.com/user-attachments/assets/1e79c884-1b99-4130-8eaf-92525324d4a9" />| <img width="250"  alt="image" src="https://github.com/user-attachments/assets/bd265f3e-0b8a-40be-a26e-aac6cb965f61" />| <img width="250" alt="image" src="https://github.com/user-attachments/assets/62dd87b7-beb5-4860-b169-b87abd1d873b" />
 |

| Career Exploration | Profile Management | AI Chatbot |
| :---: | :---: | :---: |
| <img width="250" alt="image" src="https://github.com/user-attachments/assets/a46b5e9f-dc72-46fa-8da4-5433a678d937" /> | <img width="250" alt="image" src="https://github.com/user-attachments/assets/8b1d72e9-fd94-44bf-8f19-d2af12097976" /> | <img width="250" alt="image" src="https://github.com/user-attachments/assets/a23fd941-6850-4348-a1f4-897371b1f76c" /> |

## 🎯 The Problem

This project is a solution for the Smart India Hackathon (SIH) problem statement aimed at tackling the decline in student enrollment in government degree colleges in Jammu & Kashmir.

> At the heart of the problem lies a critical gap in awareness; most students and often their parents do not understand the importance of graduation, what career opportunities different degree courses unlock, or how to choose a subject stream based on personal interest and future prospects. This lack of clarity leads to poor academic decisions, dropouts, or migration to private institutions.

Saarthi aims to bridge this gap by providing a user-friendly, mobile-first platform for personalized guidance.

## ✨ Features

This repository contains the complete Android frontend, built with a "UI-first" approach using mock data and live authentication.

-   **Seamless User Onboarding:**
    -   Visually appealing Onboarding screen.
    -   Clean Login/Sign-up screen with options for OTP and Google Sign-In.
-   **🔥 Firebase Authentication:**
    -   **Google Sign-In:** Fully implemented, secure sign-in with Google accounts.
    -   **Phone (OTP) Authentication:** Complete flow for sending and verifying OTPs for phone number-based login.
-   **🧠 Personalized Assessment Quiz:**
    -   A multi-step quiz to gauge user interests and aptitudes.
    -   Dynamic progress bar and state management.
-   **📊 Dynamic & Personalized Dashboard:**
    -   Greets the user by name.
    -   Displays personalized career recommendations that **change based on quiz results**.
    -   Lists other career paths and upcoming deadlines.
-   **🚀 Career & College Exploration:**
    -   Interactive lists of careers and colleges.
    -   Clickable cards that navigate to beautiful, detailed information screens.
    -   Search functionality for the college directory.
-   **🤖 AI-Powered Chatbot:**
    -   Integrated with the **Google Gemini API** to provide an interactive chat experience.
    -   The AI is primed with a system prompt to act as a helpful career advisor for students in J&K.
    -   The UI gracefully handles keyboard interactions and loading states.
-   **👤 Profile Management:**
    -   Users can view and edit their name, education, and interests.
    -   **Photo Upload:** A fully functional image picker to let users add a profile picture from their gallery.
    -   Secure logout functionality.

## 🛠️ Tech Stack & Architecture

This project is built using modern Android development best practices and libraries.

-   **UI:** 100% [Jetpack Compose](https://developer.android.com/jetpack/compose) with [Material 3](https://m3.material.io/) design principles.
-   **Architecture:** Follows the recommended **MVVM (Model-View-ViewModel)** architecture pattern.
-   **State Management:** Utilizes `ViewModel`s and `StateFlow` to manage and expose UI state in a reactive and lifecycle-aware manner.
-   **Navigation:** A robust navigation graph built with [Navigation Compose](https://developer.android.com/jetpack/compose/navigation), featuring nested navigation for the main tabbed interface.
-   **Authentication:** [Firebase Authentication](https://firebase.google.com/docs/auth) for Google Sign-In and Phone OTP.
-   **Asynchronous Operations:** [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) for all background tasks, including API calls.
-   **Image Loading:** [Coil](https://coil-kt.github.io/coil/) for efficiently loading and displaying user-selected profile pictures.
-   **Generative AI:** [Google AI client SDK for Gemini](https://ai.google.dev/tutorials/android_quickstart) for the chatbot functionality.
-   **Build System:** Gradle with Kotlin DSL.

## 📂 Project Structure

The project follows a clean, feature-oriented package structure to ensure scalability and maintainability.

```
com.example.saarthi
│
├── data/ # Data layer
│ ├── mock/ # Mock data sources for UI development (MockData.kt)
│ ├── model/ # Kotlin data classes (User, Career, ChatMessage, etc.)
│ └── remote/ # (Placeholder for Retrofit API services)
│
├── navigation/ # Navigation graph logic (AppNavigation.kt, Screen.kt)
│
├── ui/ # UI layer (Composables)
│ ├── screens/ # Individual screen Composables (LoginScreen, DashboardScreen, etc.)
│ └── theme/ # App theme, colors, and typography (Color.kt, Theme.kt, Type.kt)
│
└── viewmodel/ # ViewModels for each screen's business logic
```

## ⚙️ Setup and Installation

To run this project on your own device:

1.  **Clone the Repository**
    ```bash
    git clone https://github.com/your-username/saarthi-android.git
    ```
2.  **Open in Android Studio**
    -   Open Android Studio (Hedgehog or newer is recommended).
    -   Select `File > Open` and navigate to the cloned project directory.

3.  **Set up Firebase**
    -   Go to the [Firebase Console](https://console.firebase.google.com/) and create a new project.
    -   Add an Android app with the package name `com.example.saarthi`.
    -   Follow the instructions to download the `google-services.json` file.
    -   Place the `google-services.json` file in the **`app/`** directory of the project.
    -   In the Firebase Console, go to **Authentication > Sign-in method** and enable the **Google** and **Phone** providers.

4.  **Set up Gemini API Key**
    -   Go to [Google AI Studio](https://aistudio.google.com/) and get an API key.
    -   In the root directory of the project, create a file named `local.properties`.
    -   Add your API key to this file:
      ```properties
      GEMINI_API_KEY=YOUR_API_KEY_HERE
      ```

5.  **Build and Run**
    -   Let Android Studio sync the Gradle files.
    -   Build and run the app on an emulator or a physical Android device.

## 🛣️ Future Work & Roadmap

-   **Backend Integration:** Replace all calls to `MockData` with live API calls to the real backend using Retrofit.
-   **UI/UX Enhancements:** Add animations and transitions to make the user experience even smoother.
-   **Error Handling:** Implement more robust error handling and user feedback for network failures.
-   **Offline Caching:** Implement a local database (e.g., Room) to cache data for offline access.

---
*This project was developed as a part of the Smart India Hackathon initiative.*
