import com.sun.source.tree.Scope
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull

fun main(args: Array<String>) = runBlocking {

    try {


        val ret = withTimeout(100) {
            repeat(1000) {
                delay(10)
                println("><")
            }

        }
    } catch (ex: TimeoutCancellationException) {
        print(ex.message)
    }
}
