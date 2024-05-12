// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.4.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.serialization") version "1.9.22"
    id("com.google.gms.google-services") version "4.4.1" apply false
    id("com.autonomousapps.dependency-analysis") version "1.31.0"
}