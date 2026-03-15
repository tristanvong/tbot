plugins {
    id("java")
    id("application")
    id("com.gradleup.shadow") version "9.3.2"
}

group = "be.tbot"
version = "0.1.0"

application {
    mainClass = "be.tbot.Bot"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("net.dv8tion:JDA:6.3.1")
    implementation("ch.qos.logback:logback-classic:1.5.32")

    testImplementation(platform("org.junit:junit-bom:6.0.3"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}