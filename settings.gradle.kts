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
2. Add build.gradle.kts in the Root Folder
Go back to the <> Code tab (root level).
Click "Add file" ➔ "Create new file".
In the filename box, type:
build.gradle.kts
(Note: This is in the root folder, not inside app/)
Paste this content:
plugins {
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
}
