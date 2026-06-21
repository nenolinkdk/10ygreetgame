# Birthday Block Quest

Birthday Block Quest is a very small Android math quiz game written in Kotlin.
It is made for a 10-year-old child who likes block-building games.

The repository also includes a web/PWA version, like the SteveHit games, so it
can be opened from GitHub Pages and installed from Android Chrome.

## Game Features

- Birthday greeting start screen
- One quiz screen
- 10 math questions
- Addition, subtraction, multiplication, and simple division
- Three answer buttons for each question
- 10 points for every correct answer
- Final birthday message after the quiz
- No login
- No internet permission
- Simple colorful block-inspired UI

## Install From Android Chrome

Open this link on the phone:

`https://nenolinkdk.github.io/10ygreetgame/`

Then use Chrome's install prompt or choose:

`Menu` -> `Add to Home screen` / `Install app`

The direct APK is also in the repository:

`BirthdayBlockQuest-debug.apk`

## Open In Android Studio

1. Open Android Studio.
2. Choose **Open**.
3. Select this folder.
4. Let Android Studio sync Gradle.
5. Press **Run**.

The app uses one Kotlin file for the game logic:

`app/src/main/java/dk/nenolink/birthdayblockquest/MainActivity.kt`

That makes it easier for beginners to read and change.
