package com.jond3k.blog.zproof.mutations

import org.scalatest.FlatSpec
import org.scalatest.matchers.MustMatchers
import com.jond3k.blog.zproof.ast._

/**
 * 
 */
class AssociativityMutationTest extends AssociativityMutation with FlatSpec with MustMatchers {

  it must "not mutate an expression with no associative operators" in {
    traverse(
      OpImplies(
        Variable("p"),
        Variable("q"))) must equal(
      OpImplies(
        Variable("p"),
        Variable("q")))
  }

  it must "not mutate an expression with associative operators of different types" in {
    traverse(
      OpOr(
        Variable("p"),
        OpAnd(
          Variable("q"),
          Variable("r")
        ))
    ) must equal(
      OpOr(
        Variable("p"),
        OpAnd(
          Variable("q"),
          Variable("r")
        )))
  }

  it must "mutate an expression with associative operators of the same types (right association)" in {
    traverse(
      OpOr(
        Variable("p"),
        OpOr(
          Variable("q"),
          Variable("r")
        ))
    ) must equal(
      OpOr(
        Variable("p"),
        Variable("q"),
        Variable("r")))
  }
}
