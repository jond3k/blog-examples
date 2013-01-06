package com.statemachina.blog.accessors

import com.statemachina.blog.{UnsafeHelper, PerformanceTestBase}

/**
 * Demonstrates the performance of Unsafe, a facade to Unsafe (UnsafeAccessor) and
 * a bounded facade (BoundedAccessor) that will throw an exception before a
 * segmentation fault can occur.
 */
object AccessorPerformance extends App with PerformanceTestBase with UnsafeHelper {

  // configure PerformanceTestBase
  def iterations = 5
  def printIterations = false
  def printTests = true

  // allocate an off-heap slab with Unsafe. reuse this in all tests.
  private val slabSize  = 1024 * 1024 * 1024 * 10L
  private val slab      = allocateAndZero(slabSize)

  // the size of a long. don't play :-)
  private val increment = 8L

  // create the accessors; means the overhead of allocation and gc isn't included
  private val unsafeAccessor  = new UnsafeAccessor(unsafe)
  private val boundedAccessor = new BoundedAccessor(unsafe, slab, slabSize)

  // run the tests, see PerformanceTestBase for more
  time("warmup - fillWithUnsafe")(fillWithUnsafe)
  time("fillWithBoundedAccessor")(fillWithBoundedAccessor)
  time("fillWithUnsafeAccessor")(fillWithUnsafeAccessor)
  time("fillWithUnsafe")(fillWithUnsafe)

  // cleanup
  unsafe.freeMemory(slab)

  /**
   * Write longs directly, using sun.misc.Unsafe
   */
  def fillWithUnsafe() {
    var offset = 0L
    while (offset < slabSize) {
      unsafe.putLong(slab + offset, Long.MaxValue)
      offset = offset + increment
    }
  }

  /**
   * Write longs with the UnsafeAccessor. Just redirects to sun.misc.Unsafe, so should be 0% slower
   */
  def fillWithUnsafeAccessor() {
    var offset = 0L
    while (offset < slabSize) {
      unsafeAccessor.putLong(slab + offset, Long.MaxValue)
      offset = offset + increment
    }
  }

  /**
   * Write longs with the BoundedAccessor. Overrides UnsafeAccessor's methods to do bounds checking.
   * May be slower
   */
  def fillWithBoundedAccessor() {
    var offset = 0L
    while (offset < slabSize) {
      boundedAccessor.putLong(slab + offset, Long.MaxValue)
      offset = offset + increment
    }
  }
}
