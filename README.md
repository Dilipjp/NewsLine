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

### Features in Detail

- **Firebase Authentication**  
  Users can easily log in or register using their email and password. Firebase Authentication ensures secure handling of user credentials, keeping authentication safe and seamless.

- **News API Integration**  
  Fetch the latest articles from a wide range of trusted news sources. The app supports powerful search functionality to filter articles by categories or keywords, displaying each article with its title, description, images, and source for easy reference.

- **Firebase Realtime Database**  
  Store and manage user data with Firebase Realtime Database. This includes:  
  - **Saved Favorite Articles**: Users can bookmark articles to view later.  
  - **Comments**: Users can leave comments on articles, which are saved in real-time and synced across all devices, ensuring up-to-date information.

- **Dark Mode / Light Mode**  
  Users can switch between Dark Mode and Light Mode according to their preference. The app’s UI automatically adapts to the selected mode, providing an optimal reading experience in various lighting conditions.

- **Stripe Payment Integration**  
  Integrated with Stripe to securely process payments for premium features, donations, or subscriptions. Users can conveniently make payments within the app.

- **Share News**  
  Easily share articles with others via different social media platforms or messaging apps, allowing users to spread important news effortlessly.

### Mind Map

The app structure can be visualized as follows:

#### **Home Page**
- **Latest Headlines**: Display the top news stories.
- **Search Bar**: Allow users to search for specific articles by keywords or topics.
- **Categories Navigation**: Provide easy access to different categories of news (e.g., Technology, Politics, Sports).

#### **News Article Page**
- **Article Title**: Display the title of the selected article.
- **Article Content**: Show the full content or summary of the article.
- **Author and Source**: Display the author’s name and the news source.
- **Comments Section**: Enable users to read, post, and manage comments on the article.
- **Share Option**: Allow users to share articles via social media or other channels.
- **Save Article Button**: Enable users to save articles to their favorites list for later reading.

#### **User Account**
- **Login/Signup**: Secure user authentication for new users and existing members.
- **Profile**: Display the user’s personal information and settings.
- **Saved Articles**: Access the list of articles saved by the user for future reference.


#### **Settings**
- **Dark Mode/Light Mode Toggle**: Allow users to switch between dark and light themes based on their preferences.
- **Payment for Premium Features**: Process payments for premium services or features.
- **Notifications Preferences**: Let users manage their notification settings for breaking news, updates, and other alerts.



### Functional Requirements

The **Functional Requirements** define the specific functionalities that the **NewsLine** app must support to deliver a seamless and user-friendly experience:

1. **User Registration and Login**:
   - Allow users to register by providing their email and password.
   - Enable users to securely log in using **Firebase Authentication**.
   - Provide a seamless sign-up and login flow with email validation and error handling.

2. **News Display**:
   - Fetch and display the latest news articles from various trusted sources using the **News API**.
   - Articles should include essential metadata such as titles, descriptions, images, and publication sources.

3. **Search and Filter**:
   - Implement a **search bar** that allows users to search for articles by keywords, topics, or categories.
   - Filter news articles based on categories, trending topics, or user preferences.

4. **Save and Share Articles**:
   - Allow users to **save** articles as favorites for later reading.
   - Enable users to **share** articles with others via various social media platforms or messaging apps.

5. **Commenting System**:
   - Provide users with the ability to comment on news articles.
   - Allow users to view, post, and delete their comments.
   - Comments should be stored and updated in **Firebase Realtime Database** for real-time synchronization.

6. **Dark Mode/Light Mode**:
   - Implement a toggle that allows users to switch between **Dark Mode** and **Light Mode**.
   - The UI should adapt seamlessly to the selected mode to improve readability and user experience.

7. **Payment Integration**:
   - Integrate **Stripe** for payment processing (for premium features, donations, or subscriptions).
   - Ensure secure and seamless payment transactions within the app.
   
---

### Non-Functional Requirements

The **Non-Functional Requirements** focus on the overall performance, security, and quality of the application:

1. **Performance**:
   - Ensure fast and responsive loading times for news articles, even with a large amount of data.
   - Optimize performance to provide a smooth user experience with minimal lag or delays.

2. **Security**:
   - Utilize **Firebase Authentication** for secure login and user data protection.
   - Ensure that **Stripe API** transactions are securely processed with end-to-end encryption.
   - Safeguard user data, including personal details and payment information, by implementing best security practices.

3. **Scalability**:
   - Design the app to handle increasing numbers of users and news data as the app grows.
   - Ensure that **Firebase Realtime Database** is capable of scaling as the volume of comments, saved articles, and user interactions increase.

4. **Accessibility**:
   - Provide a highly **accessible** user interface, ensuring that all elements are usable by people with various disabilities.
   - Support common accessibility features like screen readers, large text sizes, and high-contrast modes.

5. **Usability**:
   - Focus on creating an **intuitive** and easy-to-navigate interface.
   - Minimize the number of steps required for users to access their desired content (articles, saved posts, etc.).
   - Prioritize user-friendly interactions, ensuring that all buttons, links, and actions are clearly visible and responsive.

6. **Compatibility**:
   - Ensure that the app is compatible with a wide range of **Android devices**, supporting multiple screen sizes, resolutions, and OS versions.
   - Test the app on different Android versions (Lollipop and above) to ensure consistent behavior.

7. **Maintainability**:
   - Write clean, well-documented, and modular code to facilitate future updates and bug fixes.
   - Use best practices for code versioning and testing to ensure that future developers can easily maintain and enhance the app.

8. **Localization and Internationalization**:
   - Support multiple languages and regions, providing localized content for a global user base.
   - Implement a flexible system for managing different language strings and adjusting UI layouts based on language preferences.

9. **Backup and Recovery**:
   - Ensure that user data (saved articles, comments, preferences) is regularly backed up and can be recovered in case of app failure or crashes.
   - Implement graceful recovery mechanisms to restore app functionality without losing user data.



