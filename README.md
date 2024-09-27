# cruiser

Client side application of SpotOn.

## Build and Run

Please complete [this tutorial](https://www.jetbrains.com/help/kotlin-multiplatform-dev/multiplatform-setup.html)
to ensure you have the correct environment for building and running the project.

Next you need a private token from mapbox. Follow [these instructions](https://docs.mapbox.com/android/maps/guides/install/#configure-your-secret-token)
to configure this for Android, but generally create a token with the `Downloads:Read` permission and
add it to your `gradle.properties` file.

     echo "MAPBOX_DOWNLOADS_TOKEN=<your_token>" >> ~/.gradle/gradle.properties

For iOS, follow [these instructions](https://docs.mapbox.com/ios/maps/guides/install/#step-3-configure-your-secret-token)
but generally add the following to your `~/.netrc` file.

     machine api.mapbox.com
     login mapbox
     password <your_token>

Assuming you have the correct environment and the token, you can build and run the project from
Android Studio.

## Compose Multiplatform Project Structure

This is a Kotlin Multiplatform project targeting Android, iOS, Web.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html),
[Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform/#compose-multiplatform),
[Kotlin/Wasm](https://kotl.in/wasm/)…

We would appreciate your feedback on Compose/Web and Kotlin/Wasm in the public Slack channel [#compose-web](https://slack-chats.kotlinlang.org/c/compose-web).
If you face any issues, please report them on [GitHub](https://github.com/JetBrains/compose-multiplatform/issues).

You can open the web application by running the `:composeApp:wasmJsBrowserDevelopmentRun` Gradle task.
