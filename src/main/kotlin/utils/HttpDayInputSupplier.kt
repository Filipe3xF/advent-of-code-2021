package utils

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import config.Configuration
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking

class HttpDayInputSupplier(
    private val configuration: Configuration = Configuration.readConfiguration()
) : DayInputSupplier {

    private val client = HttpClient(CIO) { expectSuccess = false }

    override fun getDayInput(dayNumber: Int): Either<Throwable, List<String>> = runBlocking {
        client.use { httpClient ->
            httpClient.get<HttpStatement>("${configuration.host}/${configuration.year}/day/$dayNumber/input") {
                cookie("session", configuration.sessionCookie)
            }.execute {
                val content: String = it.receive()

                if (it.status == HttpStatusCode.OK)
                    handleSuccessResponse(content).right()
                else
                    handleFailureResponse(content).left()
            }
        }
    }

    private fun handleSuccessResponse(responseContent: String) = responseContent.trim().split("\n")

    private fun handleFailureResponse(responseContent: String) = HttpDayInputRequestError(responseContent)

    class HttpDayInputRequestError(message: String) : Throwable(message)
}
