package com.statemachina.blog.accessors

import sun.misc.Unsafe

/**
 * Wraps sun.misc.Unsafe. Unlike Unsafe, it can be overridden.
 *
 * Using this class should cause no slowdown thanks to JVM optimisations but this can be verified with
 * the AccessorPerformance script.
 */
class UnsafeAccessor(unsafe: Unsafe) {

  def getByte(l: Long) = unsafe.getByte(l)

  def putByte(l: Long, b: Byte) { unsafe.putByte(l, b) }

  def getShort(l: Long) = unsafe.getShort(l)

  def putShort(l: Long, i: Short) { unsafe.putShort(l, i) }

  def getChar(l: Long) = unsafe.getChar(l)

  def putChar(l: Long, c: Char) { unsafe.putChar(l, c) }

  def getInt(l: Long) = unsafe.getInt(l)

  def putInt(l: Long, i: Int) { unsafe.putInt(l, i) }

  def getLong(l: Long) = unsafe.getLong(l)

  def putLong(l: Long, l2: Long) { unsafe.putLong(l, l2) }

  def getFloat(l: Long) = unsafe.getFloat(l)

  def putFloat(l: Long, v: Float) { unsafe.putFloat(l, v) }

  def getDouble(l: Long) = unsafe.getDouble(l)

  def putDouble(l: Long, v: Double) { unsafe.putDouble(l, v) }

  def getAddress(l: Long) = unsafe.getAddress(l)

  def putAddress(l: Long, l1: Long) { unsafe.putAddress(l, l1) }

  def setMemory(l: Long, l1: Long, b: Byte) { unsafe.setMemory(l, l1, b) }

}
