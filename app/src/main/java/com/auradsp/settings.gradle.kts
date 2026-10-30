pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "AuraDSP"
include(":app")
Click "Commit changes..." ➔ "Commit changes".
Step 3: Create Root build.gradle.kts
Back in auradsp-app, click "Add file" ➔ "Create new file".
In the filename box, type:
build.gradle.kts
Paste this code:
plugins {
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
}
