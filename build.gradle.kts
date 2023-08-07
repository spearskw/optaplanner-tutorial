plugins {
    kotlin("jvm") version "1.8.21"
}

kotlin {
    jvmToolchain(17)
}

sourceSets {
    main {
        kotlin.srcDir("src")
    }
    test {
        kotlin.srcDir("test")
    }
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.8.21")
}

repositories {
    mavenCentral()
}
