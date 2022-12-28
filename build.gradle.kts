import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.compose") version "1.2.0"
    kotlin("jvm") version "1.7.10"
}

group = "com.yayarh.pvccalculator"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}


dependencies {
    implementation(compose.desktop.currentOs)
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

compose.desktop {
    application {
        mainClass = "com.yayarh.pvccalculator.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "PvcCalculator"
            packageVersion = "1.0.0"
            vendor = "Yahia RH"
            copyright = "Yahia RH"
        }
    }
}