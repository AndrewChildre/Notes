
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

// Loop explanation
//start with the values at the top
//        temp = 1
//        prevprev = 0
//        prev = 0
//        add prev and prevprev which is one
//
//        then the next loop starts
//        temp = 1 which is the current number
//        prevprev = 0
//        prev = 1
//        add prev + prevprev = 1
//
//        next loop is
//        temp = 1 is the current
//        prevprev = 1 is the previous prev
//        prev = 1 is the temp
//        add prev + prevprev = 2
//
//        next loop is
//
//        temp = 2 which is the current
//        prevprev = 1 which is from the previous loop
//        prev = 2 is the temp
//        add prev + prevprev is 3
//
//        next loop is
//
//        temp = 3 add from the prevoius loop
//        prevprev = 2 prev from the previous loop
//        prev = 3 is the temp
//        add prev + prevprev = 5
//
//        next loop is
//
//        temp = 5 which is the add from above (end of the list)
//        prevprev = 3 is the prev from the prevoius loop (the one next to it)
//        prev = 5 is the temp
//        add prev + prevprev = 8
//
//        I have temp which is the value from the previous loop its the last entry in the list of numbers
//        I have prevprev which is the prev from the prevoius loop
//        I have prev which is now the temp
//        then I add prev and prevprev

