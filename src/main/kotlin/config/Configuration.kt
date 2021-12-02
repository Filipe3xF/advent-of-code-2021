package config

import com.typesafe.config.ConfigFactory.load
import com.typesafe.config.ConfigFactory.parseFile
import java.io.File

class Configuration private constructor(
    val host: String,
    val year: Int,
    val sessionCookie: String
) {
    companion object {
        fun readConfiguration(): Configuration {
            val config = parseFile(File(PATH_TO_CONFIG)).withFallback(load())
            with(config) {
                return Configuration(
                    getString("$HTTP_KEY.$HOST_KEY"),
                    getInt("$HTTP_KEY.$YEAR_KEY"),
                    getString("$HTTP_KEY.$SESSION_KEY.$COOKIE_KEY")
                )
            }
        }

        private const val PATH_TO_CONFIG = "src/main/resources/configuration.conf"
        private const val HTTP_KEY = "http"
        private const val HOST_KEY = "host"
        private const val YEAR_KEY = "year"
        private const val SESSION_KEY = "session"
        private const val COOKIE_KEY = "cookie"
    }
}
