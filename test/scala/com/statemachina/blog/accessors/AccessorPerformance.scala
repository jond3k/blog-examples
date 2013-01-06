package com.statemachina.blog.accessors

import com.statemachina.blog.{UnsafeHelper, PerformanceTestBase}

/**
 * Writes a bunch of longs as fast as we can.
 */
object AccessorPerformance extends App with PerformanceTestBase with UnsafeHelper {

  def iterations = 5

  def printIterations = false

  def printTests = true

  private val slabSize  = 1024 * 1024 * 1024 * 5L
  private val slab      = allocateAndZero(slabSize)
  private val increment = 8L

  private val unsafeAccessor  = new UnsafeAccessor(unsafe)
  private val boundedAccessor = new BoundedAccessor(unsafe, slab, slabSize)

  time("warmup - fillWithUnsafe")(fillWithUnsafe)
  time("fillWithBoundedAccessor")(fillWithBoundedAccessor)
  time("fillWithUnsafeAccessor")(fillWithUnsafeAccessor)
  time("fillWithUnsafe")(fillWithUnsafe)

  unsafe.freeMemory(slab)

  def fillWithUnsafe() {
    var offset = 0L
    while (offset < slabSize) {
      unsafe.putLong(slab + offset, Long.MaxValue)
      offset = offset + increment
    }
  }

  def fillWithUnsafeAccessor() {
    var offset = 0L
    while (offset < slabSize) {
      unsafeAccessor.putLong(slab + offset, Long.MaxValue)
      offset = offset + increment
    }
  }

  def fillWithBoundedAccessor() {
    var offset = 0L
    while (offset < slabSize) {
      boundedAccessor.putLong(slab + offset, Long.MaxValue)
      offset = offset + increment
    }
  }
}
