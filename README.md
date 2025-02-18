# NewsLine - Android News App 📰

NewsLine is an Android application built with **Java** that allows users to stay updated with the latest news. Users can read articles, save their favorites, comment on news posts, and share them with others. The app integrates **Firebase Authentication** for secure login, **Firebase Realtime Database** for saving user data, **News API** for fetching real-time news, and **Stripe API** for handling payments.

This app also includes a **dark mode/light mode** toggle for a better user experience.

## Features

- 📰 **Real-Time News Updates**: Get the latest news articles from various sources through the **News API**.
- 🔍 **Search Functionality**: Search news articles by keywords, topics, or categories.
- 💾 **Save Articles**: Users can save their favorite news articles for later viewing.
- 💬 **Comments**: Allow users to comment on news articles.
- 🔄 **Share**: Users can share news articles with others through different channels.
- 🌓 **Dark Mode/Light Mode**: Users can toggle between dark mode and light mode based on their preferences.
- 🛒 **Stripe Payment Integration**: Integrated Stripe for payments (if applicable for premium features or donations).
- 🔒 **Secure Authentication**: Users can log in or sign up using **Firebase Authentication**.

## Screenshots

### Home Page
![Home Page](https://github.com/Dilipjp/NewsLine/blob/main/screenshots/home.png)

### News Articles
![News Articles](https://github.com/Dilipjp/NewsLine/blob/main/screenshots/articles.png)

### Article Details
![Article Details](https://github.com/Dilipjp/NewsLine/blob/main/screenshots/article_details.png)

### Dark Mode
![Dark Mode](https://github.com/Dilipjp/NewsLine/blob/main/screenshots/dark_mode.png)

## Getting Started

Follow these steps to run the app locally:

### Prerequisites

- **Android Studio** for building and running the app.
- **Firebase Project** for authentication and Realtime Database.
- **News API Key** for fetching real-time news articles.
- **Stripe API Keys** for payment processing.

### Installation

1. **Clone the repository**:
   ```sh
   git clone https://github.com/Dilipjp/NewsLine.git
   cd NewsLine
Key Technologies Used
Java: For Android app development.
Firebase:
Firebase Authentication: Secure user authentication (sign up, login).
Firebase Realtime Database: Storing user data, favorites, and comments.
News API: Fetches the latest news articles from a variety of trusted news sources.
Stripe API: Integrated for processing payments (e.g., for premium features).
Android SDK: For native Android development.
Dark Mode/Light Mode: Custom theme switcher for user interface customization.

Features in Detail
Firebase Authentication
Users can log in or register using their email and password.
Firebase Authentication handles all the security aspects of user authentication.
News API Integration
Fetches the latest articles from a variety of trusted news sources.
Supports search functionality to filter articles by categories or keywords.
Displays articles with their titles, descriptions, images, and source.
Firebase Realtime Database
Saves user data such as:
Saved favorite articles.
Comments posted by the user on articles.
Real-time sync with the database ensures that the data is up-to-date across devices.
Comments
Users can comment on articles they read.
Comments are stored in Firebase Realtime Database and updated in real-time.
Dark Mode / Light Mode
Users can switch between Dark Mode and Light Mode in the app’s settings or system-wide settings.
The app’s UI adapts to the mode for better readability and aesthetics.
Stripe Payment Integration
Stripe is integrated for processing payments (for premium features, donations, or subscriptions).
Users can make payments securely within the app.
Share News
Articles can be shared via different social media platforms or messaging apps.
Mind Map
NewsLine App
│
├── Home Page
│ ├── Latest Headlines
│ ├── Search Bar
│ ├── Categories Navigation
│
├── News Article Page
│ ├── Article Title
│ ├── Article Content
│ ├── Author and Source
│ ├── Comments Section
│ ├── Share Option
│ ├── Save Article Button
│
├── User Account
│ ├── Login/Signup
│ ├── Profile
│ ├── Saved Articles
│ ├── Payment for Premium Features
│
└── Settings
├── Dark Mode/Light Mode Toggle
├── Notifications Preferences

Functional Requirements
User Registration and Login: Enable users to sign up, log in, and manage their profiles.
News Display: Fetch and display articles from the News API.
Search and Filter: Implement search and category filters for news articles.
Save and Share Articles: Allow users to save their favorite articles and share them on social media.
Commenting System: Users can comment on articles.
Dark Mode/Light Mode: Provide a mode toggle for better user experience.
Payment Integration: Process payments using Stripe for premium features.
Non-Functional Requirements
Performance: Ensure fast loading of articles and smooth user experience even under heavy load.
Security: Protect user data with Firebase Authentication and secure payment processing via Stripe.
Scalability: Handle increasing user load and expanding data.
Accessibility: Ensure the app is accessible to all users.
Usability: Design an intuitive and easy-to-navigate user interface.
Compatibility: Ensure compatibility across a wide range of Android devices


