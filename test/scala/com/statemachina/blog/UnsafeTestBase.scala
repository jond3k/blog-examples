package com.statemachina.blog

import accessors.{BoundedAccessor, UnsafeAccessor}
import org.scalatest.{BeforeAndAfterAll, FlatSpec, BeforeAndAfter}
import org.scalatest.matchers.MustMatchers

/**
 * 
 */
abstract class UnsafeTestBase
  extends FlatSpec
  with MustMatchers
  with BeforeAndAfter
  with BeforeAndAfterAll
  with UnsafeHelper {

  def mustBeZeroLong(address: Long) {
    unsafe.getLong(address) must equal(0)
  }

  /**
   * this is interesting.. tests were segfaulting at random. after inspecting input and output it looks like
   * freememory can be called before the test has finished. i suspect this is because the jvm doesn't track
   * write order dependency of these calls so unsafe.put* can be reordered to run after unsafe.freememory!
   *
   * for now i've solved the problem by deferring destruction to the end of the test and putting a sleep in
   * but further investigation would be great as this is a really interesting problem!!
   */
  //var dealloc = List.empty[Long]

  override def afterAll() {
    super.afterAll()
    //Thread.sleep(100)
    //dealloc.foreach(unsafe.freeMemory(_))
  }

  var slab: Long = 0

  var accessor: UnsafeAccessor = null

  // override at will
  def slabSize: Int = 1024

  before {
    slab = allocateAndZero(slabSize)
    accessor = new BoundedAccessor(unsafe, slab, slabSize)
    //dealloc = dealloc :+ slab
    //println("started 0x%x" format slab)
  }

  after {
    unsafe.freeMemory(slab)
  }

}
