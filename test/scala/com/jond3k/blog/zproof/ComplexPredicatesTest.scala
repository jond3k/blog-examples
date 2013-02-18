package com.jond3k.blog.zproof

import ast._
import org.scalatest.FlatSpec
import org.scalatest.matchers.MustMatchers

/**
 *
 */
class ComplexPredicatesTest extends FlatSpec with MustMatchers {
  val parser = new PredicateParser

  it must "be able to parse (a ∧ b)" in {
    parser.parse("(a ∧ b)") must equal(
      OpAnd(
        Variable("a"),
        Variable("b"))
    )
  }

  it must "be able to parse (a ∨ b)" in {
    parser.parse("(a ∨ b)") must equal(
      OpOr(
        Variable("a"),
        Variable("b"))
    )
  }

  it must "be able to parse a ∧ (b ∨ c)" in {
    parser.parse("a ∧ ( b ∨ c ) ") must equal(
      OpAnd(
        Variable("a"),
        OpOr(
          Variable("b"),
          Variable("c"))
      )
    )
  }

  it must "be able to parse (a ∧ b) ∨ (a ∧ c) ⇔ a ∧ (b ∨ c)" in {
    val expected = OpEquivalence(
      OpOr(
        OpAnd(
          Variable("a"),
          Variable("b")),
        OpAnd(
          Variable("a"),
          Variable("c")
        )
      ),
      OpAnd(
        Variable("a"),
        OpOr(
          Variable("b"),
          Variable("c"))
      )
    )
    parser.parse("(a ∧ b) ∨ (a ∧ c) ⇔ a ∧ (b ∨ c)") must equal(expected)
  }
}
