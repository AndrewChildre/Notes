import KotlinCoroutines.doWork
import KotlinCoroutines.scope
import com.sun.source.tree.Scope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.random.Random
import kotlin.system.measureTimeMillis


fun main(args: Array<String>) = runBlocking {
    val job = launch {
        val time = measureTimeMillis {
            val r1 = async { doWork2(1000) }
            val r2 = async { doWork2(2000) }

            println("ANSWER_ ${r1.await() + r2.await()}")
        }
        println("It took $time ms")
    }

}

suspend fun doWork2(work: Long): Int {
    delay(work)
    return Random(0).nextInt()
}