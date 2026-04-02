# MysticLib

<hr>

Documentation Map - [Gradle Setup](#gradle-setup---repositories), [Hooking It Up](#java---hooking-up-the-library)

<hr>

## Gradle Setup - Repositories
<details>
<summary>Fabric Loom, ForgeGradle, NeoGradle, ModDevGradle</summary>

```gradle
repositories {
    maven {
        name = "Modrinth"
        url = "https://api.modrinth.com/maven"
    }
}
```

</details>
<details>
<summary>Architectury Loom (Root build.gradle)</summary>

```gradle
subprojects {
    repositories {
        maven {
            name = "Modrinth"
            url = "https://api.modrinth.com/maven"
        }
    }
}
```

</details>

## Gradle Setup - Implementation

<details>
<summary>Fabric Loom, Architectury Loom (subprojects)</summary>

### Groovy DSL

```groovy
modImplementation "maven.modrinth:mysticlib:LIB_VERSION"
```

### Kotlin DSL

```kotlin
modImplementation("maven.modrinth:mysticlib:LIB_VERSION")
```
</details>
<details>
<summary>ForgeGradle</summary>

### Groovy DSL

```groovy
implementation fg.deobf("maven.modrinth:mysticlib:LIB_VERSION")
```

### Kotlin DSL

```kotlin
implementation(fg.deobf("maven.modrinth:mysticlib:LIB_VERSION"))
```
</details>
<details>
<summary>NeoGradle/ModDevGradle</summary>

### Groovy DSL

```groovy
implementation "maven.modrinth:mysticlib:LIB_VERSION"
```

### Kotlin DSL

```kotlin
implementation("maven.modrinth:mysticlib:LIB_VERSION")
```
</details>

Replace `LIB_VERSION` with the desired release version found on Modrinth.<br>
*On Architectury Loom each subproject build.gradle requires its own modImplementation

<hr>

## Java - Hooking Up The Library
In your main mod class


Note from Millie: this documentation is very not finished