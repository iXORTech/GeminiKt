package tech.ixor.exceptions

class ConfigFileCreatedException: Exception() {
    override val message: String
        get() = "Configuration file not found. A new configuration file has been created at the root of the project. Please fill in the necessary details."
}
