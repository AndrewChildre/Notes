
interface Process {
    fun execute(value: Int)
}




fun main(){
    fibonacci(4, object : Process{
        override fun execute(value: Int) {
            println(value)
        }
    })
    fibonacci2(4)
    fibonacci3(4,{ println(it) })

}

fun fibonacci (limit: Int, process: Process) {
    var prev = 0
    var prevprev = 0
    var current = 1

    for (i:Int in 1..limit){
        process.execute(current)

        var temp = current
        prevprev = prev
        prev = temp
        current = prev + prevprev
    }
}

class EXE1: Process {
    override fun execute(value: Int) {
        println(value)
    }

}
val exe = EXE1()
fun fibonacci2 (limit: Int) {
    var prev = 0
    var prevprev = 0
    var current = 1

    for (i:Int in 1..limit){
        exe.execute(current)
        var temp = current
        prevprev = prev
        prev = temp
        current = prev + prevprev
    }
}

fun fibonacci3 (limit: Int, action: (Int) -> Unit) {
    var prev = 0
    var prevprev = 0
    var current = 1

    for (i:Int in 1..limit){
        action(current)
        var temp = current
        prevprev = prev
        prev = temp
        current = prev + prevprev
    }
}