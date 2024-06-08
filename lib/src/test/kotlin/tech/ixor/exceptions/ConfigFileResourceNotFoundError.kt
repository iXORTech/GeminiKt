package tech.ixor.exceptions

class ConfigFileResourceNotFoundError: Error() {
    override val message: String
        get() = "Configuration file resource not found."
}
