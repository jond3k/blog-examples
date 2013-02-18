package com.jond3k.blog.zproof

import ast._
import org.scalatest.FlatSpec
import org.scalatest.matchers.MustMatchers

/**
 * 
 */
class SimplePredicatesTest extends FlatSpec with MustMatchers {
  val parser = new PredicateParser

  it must "recognise a single variable" in {
    implicit val use = parser.variable
    parser.parsing("a") must equal(Variable("a"))
    parser.parsing("abc") must equal(Variable("abc"))
  }

  it must "recognise numbers" in {
    implicit val use = parser.value
    parser.parsing("2") must equal(Value(2))
    parser.parsing("234") must equal(Value(234))
  }

  it must "recognise a single variable in brackets" in {
    implicit val use = parser.grouped
    parser.parsing("(a)") must equal(Variable("a"))
  }

  it must "recognise a negated single variable" in {
    implicit val use = parser.negation
    parser.parsing("not a") must equal(OpNot(Variable("a")))
  }

  it must "recognise the disjunction of two expressions" in {
    implicit val use = parser.disjunction
    parser.parsing("abc ∨ bcd") must equal(OpOr(Variable("abc"), Variable("bcd")))
    parser.parsing("abc or bcd") must equal(OpOr(Variable("abc"), Variable("bcd")))
  }

  it must "recognise the conjunction of two expressions" in {
    implicit val use = parser.conjunction
    parser.parsing("abc ^ bcd") must equal(OpAnd(Variable("abc"), Variable("bcd")))
    parser.parsing("abc ∧ bcd") must equal(OpAnd(Variable("abc"), Variable("bcd")))
    parser.parsing("abc and bcd") must equal(OpAnd(Variable("abc"), Variable("bcd")))
  }

  it must "recognise the equality of two expressions" in {
    implicit val use = parser.equality
    parser.parsing("abc = bcd") must equal(OpEquality(Variable("abc"), Variable("bcd")))
  }

}
