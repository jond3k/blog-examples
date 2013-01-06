package com.statemachina.blog.accessors

import sun.misc.Unsafe

/**
 * Wraps sun.misc.Unsafe with boundary checks so unrecoverable, difficult to debug segfaults can be replaced
 * with manageable exceptions.
 *
 * The overhead of this has been demonstrated to be as much as 15% if AccessorPerformance is anything to go by,
 * but it can be swapped out for an UnsafeAccessor after testing.
 */
class BoundedAccessor(unsafe: Unsafe, address: Long, size: Long) extends UnsafeAccessor(unsafe) {
  val end = address + size

  private def checkBounds(l: Long) {
    if (l < address || l >= end) {
      throw new AssertionError("Preempted segfault at address 0x%x" format l)
    }
  }

  override def getByte(l: Long) =  { checkBounds(l); super.getByte(l) }

  override def putByte(l: Long, b: Byte) { checkBounds(l); super.putByte(l, b) }

  override def getShort(l: Long) =  { checkBounds(l); super.getShort(l) }

  override def putShort(l: Long, i: Short) { checkBounds(l); super.putShort(l, i) }

  override def getChar(l: Long) = { checkBounds(l); super.getChar(l) }

  override def putChar(l: Long, c: Char) { checkBounds(l); super.putChar(l, c) }

  override def getInt(l: Long) = { checkBounds(l); super.getInt(l) }

  override def putInt(l: Long, i: Int) { checkBounds(l); super.putInt(l, i) }

  override def getLong(l: Long) = { checkBounds(l); super.getLong(l) }

  override def putLong(l: Long, l2: Long) { checkBounds(l); super.putLong(l, l2) }

  override def getFloat(l: Long) = { checkBounds(l); super.getFloat(l) }

  override def putFloat(l: Long, v: Float) { checkBounds(l); super.putFloat(l, v) }

  override def getDouble(l: Long) = { checkBounds(l); super.getDouble(l) }

  override def putDouble(l: Long, v: Double) { checkBounds(l); super.putDouble(l, v) }

  override def getAddress(l: Long) = { checkBounds(l); super.getAddress(l) }

  override def putAddress(l: Long, l1: Long) { checkBounds(l); super.putAddress(l, l1) }

  override def setMemory(l: Long, l1: Long, b: Byte) { checkBounds(l); super.setMemory(l, l1, b) }
}
