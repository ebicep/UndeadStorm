plugins {
    java
    application
}

group = "com.ebicep.undeadstorm"
version = ""

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<Jar>{
    baseName = "UndeadStorm"
    manifest {
        attributes(
                "Main-Class" to "com.ebicep.undeadstorm.Game"
        )
    }
}

application {
    applicationName = "UndeadStorm"
    applicationDefaultJvmArgs = arrayListOf<String>("-Xmx1G")
    mainClassName = "com.ebicep.undeadstorm.Game"

}
