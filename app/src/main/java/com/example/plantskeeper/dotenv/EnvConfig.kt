import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.dotenv

class EnvConfig {
    companion object {
        private val env: Dotenv

        init {
            env = dotenv {
                directory = "/assets"
                filename = "env"
            }
        }

        fun getServerPathWithPort(): String {
            return "${getServerPath()}:${getServerPort()}"
        }

        fun getServerPath(): String {
            return env["SERVER_BASE_PATH"]
        }

        fun getServerPort(): String {
            return env["PORT"]
        }
    }
}