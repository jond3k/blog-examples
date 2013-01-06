package com.statemachina.blog

import scala.collection.mutable

/**
 *
 */
trait PerformanceTestBase {

  /**
   * The number of iterations to make for each test
   */
  def iterations: Int

  /**
   * Print the iterations
   */
  def printIterations: Boolean

  /**
   * Print the tests
   */
  def printTests: Boolean

  /**
   * The time taken for the first test run, used to make relative comparisons
   */
  var baseline: Double = 0

  /**
   * Something to do before each test
   */
  def preTest()  {}

  /**
   * Something to do after each test
   */
  def postTest() {}

  /**
   * Something to do before each test iteration
   */
  def preIteration() {}

  /**
   * Something to do after each test iteration
   */
  def postIteration() {}

  /**
   * The results
   */
  val results = mutable.HashMap.empty[String, mutable.ListBuffer[Long]].
    withDefault(s => mutable.ListBuffer.empty[Long])

  /**
   * Store the iteration results
   */
  def addIterationResult(name: String, took: Long) {
    results(name) += took
  }

  /**
   * Print the test iteration results
   */
  def printIteration(name: String, took: Long) {
    if (printIterations) {
      println("%-46s %6sms" format (name, took))
    }
  }

  /**
   * Print the test results
   */
  def printTest(name: String, took: Long) {
    if (printTests) {
      println("total %-40s %6sms\t%s" format (name, took, took/baseline))
    }
  }

  /**
   * Update the baseline if possible
   */
  def updateBaseline(took: Long) {
    if (baseline == 0) {
      baseline = took
    }
  }

  /**
   * Time a function. A number of iterations will be run, pre and post test events are run
   * for both the test itself and individual iterations.
   *
   * @param name The name of the test in reports
   * @param fn   The function to run
   */
  def time(name: String)(fn: () => Unit) {
    preTest()
    val testStarted = System.currentTimeMillis()
    var iteration   = 0

    while (iteration < iterations) {
      preIteration()
      val iterationStarted = System.currentTimeMillis()
      fn()
      val iterationTime = System.currentTimeMillis() - iterationStarted
      printIteration(name, iterationTime)
      addIterationResult(name, iterationTime)
      postIteration()
      iteration = iteration + 1
    }

    val totalTime = System.currentTimeMillis() - testStarted
    updateBaseline(totalTime)
    printTest(name, totalTime)
    postTest()
  }
}
