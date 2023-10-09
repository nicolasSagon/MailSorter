pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Mail Sorter"
include(":app")
include(":data:authentication")
include(":domain:authentication")
include(":ui:authentication")
include(":ui:core")
include(":ui:core")
include(":domain:navigation")
include(":ui:home")
include(":domain:mail")
include(":data:mail")
include(":domain:core")
