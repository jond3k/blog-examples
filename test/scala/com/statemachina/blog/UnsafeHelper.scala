package com.statemachina.blog

import sun.misc.Unsafe

/**
 * Makes working with unsafe a bit easier
 */
trait UnsafeHelper {
  val unsafe = try {
    val field = classOf[Unsafe].getDeclaredField("theUnsafe")
    field.setAccessible(true)
    field.get(null).asInstanceOf[Unsafe]
  } catch{
    case e: Exception => throw new RuntimeException(e)
  }

  def zero(slab: Long, size: Long) {
    unsafe.setMemory(slab, size, 0)
  }

  def allocateAndZero(size: Long): Long = {
    val slab = unsafe.allocateMemory(size)
    if (slab == 0) {
      throw new OutOfMemoryError("Oh dear, our slab is 0x0!")
    }
    unsafe.setMemory(slab, size, 0)
    slab
  }
}
