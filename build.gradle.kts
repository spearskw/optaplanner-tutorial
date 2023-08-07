plugins {
    kotlin("jvm") version "1.8.21"
    kotlin("plugin.noarg") version "1.8.21"
}

kotlin {
    jvmToolchain(17)
}

sourceSets {
    main {
        kotlin.srcDir("src")
        resources.srcDir("res")
    }
    test {
        kotlin.srcDir("test")
    }
}

dependencies {
    implementation("org.optaplanner:optaplanner-core:9.41.0.Final")
    implementation("ch.qos.logback:logback-classic:1.4.8")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.8.21")
}

repositories {
    mavenCentral()
}

noArg {
    annotation("org.optaplanner.core.api.domain.entity.PlanningEntity")
    annotation("org.optaplanner.core.api.domain.solution.PlanningSolution")
}
