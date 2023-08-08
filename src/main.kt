fun main() {
    val problem = loadProblem("c101.txt")
    var previousTime = 0L
    vrpSolver.addEventListener {
        println("New best score of ${it.newBestScore} took ${it.timeMillisSpent - previousTime}ms")
        previousTime = it.timeMillisSpent
    }
    val solution = vrpSolver.solve(problem)
    println(solution.score)
}