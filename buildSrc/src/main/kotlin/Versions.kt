import kotlin.String

/**
 * Find which updates are available by running
 *     `$ ./gradlew syncLibs`
 * This will only update the comments.
 *
 * YOU are responsible for updating manually the dependency version. */
object Versions {

    const val kotlinx_serialization_runtime_native: String = "0.11.0"

    const val ktor_client_serialization_iosx64: String = "1.2.2"

    const val ktor_client_json_native: String = "1.2.2"

    const val io_ktor_ktor_client_ios: String = "1.2.2" //available: "1.2.2"

    const val kotlinx_coroutines_core_native: String = "1.2.2"

    const val io_ktor_ktor_client_android: String = "1.2.2" //available: "1.2.2"

    const val io_ktor_ktor_client_okhttp: String = "1.2.2" //available: "1.2.2"

    const val io_ktor_ktor_client_json_jvm: String = "1.2.2" //available: "1.2.2"

    const val io_ktor_ktor_client_core_jvm: String = "1.2.2" //available: "1.2.2"

    const val kotlinx_serialization_runtime: String = "0.11.0" //available: "0.11.1-1.3.40-eap-107"

    const val io_ktor_ktor_client_serialization_jvm: String = "1.2.2" //available: "1.2.2"

    const val kotlin_stdlib: String = "1.3.50"

    const val io_ktor_ktor_client_serialization: String = "1.2.2" //available: "1.2.2"

    const val io_ktor_ktor_client_json: String = "1.2.2"

    const val io_ktor_ktor_client_core: String = "1.2.2"

    const val kotlinx_serialization_runtime_common: String = "0.11.0" //available: "0.11.1-1.3.40-eap-107"

    const val kotlinx_coroutines_core: String = "1.3.1" //available: "1.3.1"

    const val kotlinx_coroutines_core_common: String = "1.3.1" //available: "1.3.1"

    const val kotlin_stdlib_common: String = "1.3.50" //available: "1.3.50-eap-2"

    const val material: String = "1.0.0"

    const val core_testing: String = "1.1.1" 

    const val appcompat: String = "1.1.0-rc01" // exceed the version found: 1.0.2

    const val cardview: String = "1.0.0"

    const val constraintlayout: String = "2.0.0-beta2" // exceed the version found: 1.1.3

    const val core_ktx: String = "1.0.2"

    const val espresso_core: String = "3.2.0"

    const val androidx_test_ext_junit: String = "1.1.0" //available: "1.1.1" 

    const val uiautomator: String = "2.2.0" 

    const val androidx_test: String = "1.2.0" 

    const val com_android_tools_build_gradle: String = "3.4.2" //available: "3.5.0" 

    const val lint_gradle: String = "26.4.2" //available: "26.5.0" 

    const val retrofit2_kotlin_coroutines_adapter: String = "0.9.2" 

    const val com_squareup_okhttp3: String = "4.0.0" //available: "4.1.0" 

    const val com_squareup_retrofit2: String = "2.5.0" //available: "2.6.1" 

    const val arrow_core: String =
            "0.8.1" // No update information. Is this dependency available on jcenter or mavenCentral?

    const val io_fabric_tools_gradle: String = "1.31.0" 

    const val io_gitlab_arturbosch_detekt: String = "1.0.0-RC14" 
            /* Could not find io.gitlab.arturbosch.detekt:detekt-cli:1.0.0-RC14.
            Searched in the following locations:
              - https://dl.google.com/dl/android/maven2/io/gitlab/arturbosch/detekt/detekt-cli/1.0.0-RC14/detekt-cli-1.0.0-RC14.pom
              - https://dl.google.com/dl/android/maven2/io/gitlab/arturbosch/detekt/detekt-cli/1.0.0-RC14/detekt-cli-1.0.0-RC14.jar 
            .... */

    const val io_mockk: String = "1.9.2" //available: "1.9.3" 

    const val jmfayard_github_io_gradle_kotlin_dsl_libs_gradle_plugin: String = "0.2.6" 

    const val junit_junit: String = "4.12" 

    const val org_jetbrains_kotlin: String = "1.3.50"

    const val kotlinx_coroutines_android: String = "1.3.1" //available: "1.3.1"

    const val org_koin: String = "2.0.1"

    const val android_arch_lifecycle_extensions: String = "1.1.1"

    /**
     *
     *   To update Gradle, edit the wrapper file at path:
     *      ./gradle/wrapper/gradle-wrapper.properties
     */
    object Gradle {
        const val runningVersion: String = "5.1.1"

        const val currentVersion: String = "5.6"

        const val nightlyVersion: String = "6.0-20190820220256+0000"

        const val releaseCandidate: String = ""
    }
}
