fun main(args: Array<String>) {

    val numbers = listOf(10, 23, 3, 42, 51, 123, 6, 8, 99)

    val bigNum = numbers.any{it > 99}
    println(bigNum)

    val allGreater = numbers.all { it > 50 }
    println(allGreater)

    val greater50 = numbers.filter { it > 50 }
    greater50.forEach { println(it) }
println()
println()
println()
   val sq = greater50.map { it * 2 }.forEach{ println(it) }


}
