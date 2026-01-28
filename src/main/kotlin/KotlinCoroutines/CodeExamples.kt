package KotlinCoroutines

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.time.withTimeout
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull
import java.lang.Thread.sleep
import kotlin.concurrent.thread
import kotlin.time.Duration

fun old_main() {

    thread {
        sleep(1000)
        print("World")
    }

    print("Hello")
    Thread.sleep(1500)
}
// first example
// old_main starts to run
// it starts another thread and puts it to sleep for 1 sec
// then prints "Hello, " puts that thread to sleep and then the other/2nd thread runs after the sleep is done.
// and prints World. then it completes.
// the "sleep" method on the Thread class is blocking and that thread can not run

suspend fun new_main() {
    GlobalScope.launch {
        delay(2000)
        println("World")
    }
    print("Hello, ")
    delay(2000)
}
// Using coroutines
//the GlobalScope.launch  is a coroutine that sets the context on which to run GlobalScope is the context right now is using the entire application
// .launch is a coroutine it doesn't start it's own thread, it runs on whats available
// delay() is a coroutine method
// a coroutine method can only be called from within another coroutine
// i.e. suspend method can only be called within another suspend or other coroutine.
//the difference between the .sleep() witch is part of the java library and the delay() which is a kotlin coroutine is
// the .sleep() is blocking the thread from running. the delay() method only suspends the method running and the thread
// is still available for other tasks to run. when the coroutine picks back up. It may run on that one or a different one.
// it depends on what is available at the time by the os.

/////////example of coroutines is faster and more efficient

const val num_tasks = 10_000
const val loops = 500
const val wait_ms = 10L
suspend fun eff_main() {

    val jobs = mutableListOf<Job>()
    for (i in 1..num_tasks) {
        jobs.add(
            GlobalScope.launch {
                for (i in 1..loops) {
                    delay(wait_ms)
                }
            }
        )
    }
    jobs.forEach { it.join() }
}
// this is simulating starting 10k tasks at the same time in the first loop, then each loop inside of that is using delay()
// coroutine. so each of the 10k jobs runs 500 loops each loop has a delay of 10ms once those loops finish it adds it to the
// jobs list the join() method is a coroutine method so once the jobs.add finishes it puts it in a completed list. the
// .join it there to make sure the method doesn't proceed until the jobs is done.
// the launch method runs in the background before you call it.
// the mental model is
// a chef makes 10 orders, the cook makes them all at the same time. the chef writes down each order on a note pad i.e. the mutableList of
// the .join() is saying I'm waiting here until I get all ten orders.

// the delay method is non-blocking on the thread. it will free up the thread until its needed

//////////
fun main2() = runBlocking {
    println("Start")
    launch {

        println("Inside launch")
    }
    printMessage("Andrew")
    println("Finish main")

}

suspend fun printMessage(txt: String) {
    delay(100)
    println(txt)
}

//the above runs and Finish main will print before Inside Launch
// because launch starts and runs asynchronously not with in the main thread
// so main starts launch runs is the background and main keeps executing and then launch finishes after main is done.
// but since its with in the runBlocking context it does wait for it to finish. and not just quit

fun main3() = runBlocking {
    val job = launch {
        delay(1000)
        println("World")
    }
    print("Hello ,")
    println("Running ${job.isActive} Completed ${job.isCompleted}")
    job.join()
}
//The above code is showcasing the Job type the coroutines are Job types it is an interface implemented by the framework.
//.join() is a blocking method it is waiting for the coroutine to finish. the .join() is more efficent than inserting delays
// I'm thinking of the join as the coroutine is joining back on to the thread. as in the coroutine is finished. .join()
// will wait until the coroutine is done, if it hasn't finished.

fun main4() = runBlocking {
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
//this code has withTimeout on it that we can set.
//when the code times out it will throw an exception that you will need to handle.
//probably in a try catch block like the above

fun main5() = runBlocking {

    val ret = withTimeoutOrNull(100) {
        repeat(1000) {
            delay(10)
            println("><")
        }
    }
    if (ret == null) println("TIMED OUT") else println("Completed")
}
// This code is using withTimeoutOrNull once it hits the timeout parameter value it will return null

fun main6() = runBlocking {
    val job = launch {
        repeat(1000) {
            delay(100)
            print(".")
        }
    }
    delay(2500)
    job.cancel()
    job.join()
//    job.cancelAndJoin() you can make this a single call
}
//.cancel() is co-operative within the coroutine. meaning it works with the other suspend methods to cancel the work being done
// so it doesn't hang or leave tasks open.
// should use .join() because you want to make sure the coroutine finishes because .cancel() is not a suspend method so it
//doesn't know if it is done yet.

fun main7() = runBlocking {
    println("Starting")
    doWork()
    println("doWork Complete")
}
suspend fun doWork() {
    coroutineScope {
        launch {
            println("Launch 1")
            delay(1000)
            println("Launch 1 Finished")
        }
        launch {
            println("Launch 2")
            delay(2000)
            println("Launch 2 Finished")
        }
        println("Launched both coroutines")
    }
    println("Exiting co Scope")
}
//this code is showing the coroutineScope method. the doWork method will not return/exit until all the coroutines are finished
// the main thread starts the doWork method starts to run  first hits the scope an the launches both launch methods.
// they take turns on the same thread. as each one hits the delay it suspends free up the thread for other work to be done

fun main8() {
    var count = 0
    val coroutineScope = MainScope()
    fun onDock() {

        coroutineScope.launch {
            while (true){
                delay(1000)
                count++
                println("count $count")
            }
        }
    }
    fun onUndock(){
        coroutineScope.cancel()
    }
}
// this is setting my own coroutine scope
// I initialized the scope using a factory method called MainScope
// then we pretend that onDock got called and we run a coroutine of launch which is tied to that scope. Inside the launch
// we are just incrementing a counter. until the unDock method gets called when that happens the coroutineScope calls the
// cancel() method and the coroutine stops the execution. the suspend methods cooperate with the cancel to stop it.
//
fun main9() = runBlocking {
    println(Thread.currentThread().name)
    runBlocking {
    println(Thread.currentThread().name)
    }
    val job =launch {
        println(Thread.currentThread().name)
    }
    job.join()
}
//this example when run demonstrates that all these will run on the main thread
// all three of these print for the println() value
//main
//main
//main



