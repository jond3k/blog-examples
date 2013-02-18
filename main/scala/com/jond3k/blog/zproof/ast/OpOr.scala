package com.jond3k.blog.zproof.ast

/**
 * 
 */
case class OpOr(expressions: Expression*) extends Op {
  /**
   * True if brackets have no effect. e.g. op (q op r) <=> (p op q) op r
   */
  def isAssociative = true

  /**
   * True if (p op q) and (q op r) <=> (p op r)
   */
  def isTransitive = false

  /**
   * True if multiple evaluations have no effect. e.g. p op p <=> p
   */
  def isIdempotent = false

  /**
   * True if the ordering has no effect. e.g. p op q <=> q op p
   */
  def isCommutative = false
}