package tech.ixor.enums

/**
 * The available Google Gemini models to use in the API.
 * Currently having:
 * - gemini-1.0-pro
 * - gemini-pro-vision
 * - gemini-1.5-flash
 * - gemini-1.5-pro
 * @property modelCode The model code for the Gemini model.
 */
enum class GeminiModels(val modelCode: String) {
    GEMINI_1_0_PRO("gemini-1.0-pro"),
    GEMINI_1_0_PRO_VISION("gemini-pro-vision"),
    GEMINI_1_5_FLASH("gemini-1.5-flash"),
    GEMINI_1_5_PRO("gemini-1.5-pro"),
}
