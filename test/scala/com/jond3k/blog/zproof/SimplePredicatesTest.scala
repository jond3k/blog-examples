package com.jond3k.blog.zproof

import ast._
import org.scalatest.FlatSpec
import org.scalatest.matchers.MustMatchers

/**
 * 
 */
class SimplePredicatesTest extends FlatSpec with MustMatchers {
  val parser = new PredicateParser

  "variables" must "recognise a single variable" in {
    implicit val use = parser.variable
    parser.parsing("a") must equal(Variable("a"))
    parser.parsing("abc") must equal(Variable("abc"))
  }

  "value" must "recognise numbers" in {
    implicit val use = parser.value
    parser.parsing("2") must equal(Value(2))
    parser.parsing("234") must equal(Value(234))
  }

  "grouped" must "recognise a single variable in brackets" in {
    implicit val use = parser.grouped
    parser.parsing("(a)") must equal(Variable("a"))
  }

  "negation" must "recognise a negated single variable" in {
    implicit val use = parser.negation
    parser.parsing("not a") must equal(OpNot(Variable("a")))
  }

  "disjunction" must "recognise the disjunction of two expressions" in {
    implicit val use = parser.disjunction
    parser.parsing("abc ∨ bcd") must equal(OpOr(Variable("abc"), Variable("bcd")))
    parser.parsing("abc or bcd") must equal(OpOr(Variable("abc"), Variable("bcd")))
  }

  "conjunction" must "recognise the conjunction of two expressions" in {
    implicit val use = parser.conjunction
    parser.parsing("abc ^ bcd") must equal(OpAnd(Variable("abc"), Variable("bcd")))
    parser.parsing("abc ∧ bcd") must equal(OpAnd(Variable("abc"), Variable("bcd")))
    parser.parsing("abc and bcd") must equal(OpAnd(Variable("abc"), Variable("bcd")))
  }

  "equality" must "recognise the conjunction of two expressions" in {
    implicit val use = parser.equality
    parser.parsing("abc = bcd") must equal(OpEquality(Variable("abc"), Variable("bcd")))
  }


}
