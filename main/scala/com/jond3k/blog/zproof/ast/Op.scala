package com.jond3k.blog.zproof.ast

/**
 * An operator
 */
abstract class Op extends Expression {

  def expressions: Seq[Expression]

  /**
   * True if brackets have no effect. e.g. op (q op r) <=> (p op q) op r
   */
  def isAssociative: Boolean

  /**
   * True if (p op q) and (q op r) <=> (p op r)
   */
  def isTransitive:  Boolean

  /**
   * True if multiple evaluations have no effect. e.g. p op p <=> p
   */
  def isIdempotent:  Boolean

  /**
   * True if the ordering has no effect. e.g. p op q <=> q op p
   */
  def isCommutative: Boolean
}
