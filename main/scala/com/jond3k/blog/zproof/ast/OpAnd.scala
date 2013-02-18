package com.jond3k.blog.zproof.ast

/**
 * Represents logical conjunction
 */
case class OpAnd(expressions: Expression*) extends Op {

  /**
   * True if brackets have no effect. e.g. op (q op r) <=> (p op q) op r
   */
  def isAssociative = true

  /**
   * True if (p op q) and (q op r) <=> (p op r)
   */
  def isTransitive = false

  /**
   * True if multiple evaluations of the same thing have no effect. e.g. p op p <=> p
   */
  def isIdempotent = true

  /**
   * True if the ordering has no effect. e.g. p op q <=> q op p
   */
  def isCommutative = true
}