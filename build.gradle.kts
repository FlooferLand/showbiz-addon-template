import groovy.json.JsonSlurper
import java.net.URL
import java.net.URLEncoder

plugins {
    id("net.fabricmc.fabric-loom-remap")
}

enum class Props(val key: String) {
    MinecraftVersion("minecraft_version"),
    AddonDisplayName("addon_display_name"),
    AddonDescription("addon_description"),
    AddonNamespace("addon_namespace"),
    AddonVersion("addon_version"),
    AddonAuthors("addon_authors"),
    ShowbizVersion("showbiz_version"),
    LoaderVersion("loader_version");

    lateinit var value: String

    companion object {
        fun init(project: Project) {
            entries.forEach { prop ->
                val found = project.findProperty(prop.key)?.toString()
                prop.value = found ?: error("Failed to find prop '${prop.key}'!")
            }
        }
    }
}
Props.init(project)

group = Props.AddonNamespace.value
version = Props.AddonVersion.value

repositories {
    mavenCentral()
    exclusiveContent {
        forRepository { maven("https://api.modrinth.com/maven") { name = "Modrinth" } }
        filter { includeGroup("maven.modrinth") }
    }
    maven("https://maven.terraformersmc.com/releases/")
    maven("https://maven.geckolib.com/")
    maven("https://maven.ryanliptak.com/")
}

tasks.withType<ProcessResources>().configureEach {
    duplicatesStrategy = DuplicatesStrategy.WARN
    exclude("**/*.lnk", "**/*.exe", "**/*.dll", "**/*.so", "**/*.jar")
    exclude("projects")
    exclude(".vscode")

    // Inserting strings into what-not
    Props.entries.forEach() { inputs.property(it.key, it.value) }
    filesMatching("fabric.mod.json") { expand(properties) }
    filesMatching("META-INF/neoforge.mods.toml") { expand(properties) }
    filesMatching("pack.mcmeta")  { expand(properties) }
    filesMatching("data/${Props.AddonNamespace}/showbiz.addon.toml")  { expand(properties) }
}

val showbizInstall = tasks.register("showbizInstall") {
    group = "showbiz"

    var showbizVersion = Props.ShowbizVersion.value
    val modsDir = file("${project.projectDir}/run/mods")
    if (!modsDir.exists()) modsDir.mkdirs()

    doFirst {
        for (file in modsDir.listFiles()) {
            runCatching { file.delete() }
        }
    }
    doLast {

        fun downloadVersion(versions: List<Map<*, *>>) {
            val latestValid = versions.firstOrNull { (it["game_versions"] as List<String>).contains(Props.MinecraftVersion.value) }
            if (latestValid != null) {
                val files = latestValid["files"] as List<Map<String, *>>
                val fileData = files.firstOrNull() { it["primary"] == true } ?: files.first()
                val downloadUrl = fileData["url"].toString()
                val filename = fileData["filename"].toString()

                val targetFile = File(modsDir, filename)
                if (!targetFile.exists()) {
                    logger.lifecycle("Downloading mod: $filename")
                    URL(downloadUrl).openStream().use { input ->
                        targetFile.outputStream().use { input.copyTo(it) }
                    }
                } else {
                    logger.lifecycle("Found mod: $filename")
                }
            }
        }

        val projectVersions = JsonSlurper().parseText(URL("https://api.modrinth.com/v2/project/showbiz/version").readText()) as List<Map<*, *>>
        val fabricVersionEntry = projectVersions.find {
            it["version_number"] == showbizVersion && (it["loaders"] as List<*>).contains("fabric")
        } ?: error("Could not find version $showbizVersion for Showbiz")
        showbizVersion = fabricVersionEntry["id"] as String
        downloadVersion(projectVersions)

        val json = JsonSlurper().parseText(URL("https://api.modrinth.com/v2/project/showbiz/version/$showbizVersion").readText()) as Map<*, *>
        val requirements = json["dependencies"] as? List<Map<*, *>> ?: return@doLast
        requirements.forEach { req ->
            val pid = req["project_id"] as String
            val ld = URLEncoder.encode("[\"fabric\"]", "UTF-8")
            val versions = JsonSlurper().parseText(URL("https://api.modrinth.com/v2/project/$pid/version?loaders=$ld").readText()) as List<Map<*, *>>
            downloadVersion(versions)
        }
    }
}

dependencies {
    minecraft("com.mojang:minecraft:${Props.MinecraftVersion.value}")
    mappings(loom.officialMojangMappings())
    modImplementation("net.fabricmc:fabric-loader:${Props.LoaderVersion.value}")
    // modRuntimeOnly("maven.modrinth:showbiz:${Props.ShowbizVersion.value}")
}