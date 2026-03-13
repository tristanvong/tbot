plugins {
    id("java")
    id("application")
}

group = "be.tbot"
version = "0.0.0"

application {
    mainClass = "be.tbot.Bot"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("net.dv8tion:JDA:6.3.1")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}