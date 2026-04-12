# LumynLib

<hr>

Documentation Map - [Gradle Setup](#gradle-setup---repositories), [In-Mod Setup](#java---setup),
[Config Library](https://github.com/Lumynity-Studios/LumynLib/blob/rep-info/DOCS/DOCS-CONFIG.md),
[Variable Manager](https://github.com/Lumynity-Studios/LumynLib/blob/rep-info/DOCS/DOCS-VARIABLES.md),
[Util Classes](https://github.com/Lumynity-Studios/LumynLib/blob/rep-info/DOCS/DOCS-UTIL.md)

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
modImplementation "maven.modrinth:LumynLib:LIB_VERSION"
```

### Kotlin DSL

```kotlin
modImplementation("maven.modrinth:LumynLib:LIB_VERSION")
```
</details>
<details>
<summary>ForgeGradle</summary>

### Groovy DSL

```groovy
implementation fg.deobf("maven.modrinth:LumynLib:LIB_VERSION")
```

### Kotlin DSL

```kotlin
implementation(fg.deobf("maven.modrinth:LumynLib:LIB_VERSION"))
```
</details>
<details>
<summary>NeoGradle/ModDevGradle</summary>

### Groovy DSL

```groovy
implementation "maven.modrinth:LumynLib:LIB_VERSION"
```

### Kotlin DSL

```kotlin
implementation("maven.modrinth:LumynLib:LIB_VERSION")
```
</details>

Replace `LIB_VERSION` with the desired release version found on Modrinth.<br>
> On Architectury Loom each subproject build.gradle requires its own modImplementation

<hr>

## Java - Setup
LumynLib needs to know your mod's ID for some util classes and methods.<br>
Add this to your main mod class:

```java
public static final LumynLib Hook = new LumynLib("your_mod_id");
```
> In an Architectury Loom project put it in the common init class



Note from Millie: this documentation is very not finished