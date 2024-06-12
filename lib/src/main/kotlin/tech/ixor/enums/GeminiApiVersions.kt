package tech.ixor.enums

/**
 * The available versions of the Gemini API.
 * Currently having:
 * - v1
 * - v1beta
 * @property versionUrl The URL path for the version.
 */
enum class GeminiApiVersions(val versionUrl: String) {
    V1("v1"),
    V1_BETA("v1beta"),
}
