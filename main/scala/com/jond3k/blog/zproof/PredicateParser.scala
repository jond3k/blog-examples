package com.jond3k.blog.zproof

import ast._
import util.parsing.combinator.RegexParsers
import util.parsing.input.CharSequenceReader
import com.jond3k.blog.RichRegexString

/**
 * Parses predicates
 */
class PredicateParser extends RegexParsers {
  implicit def richRegexString(str: String): RichRegexString = new RichRegexString(str)

  def expressions: Parser[Expression] =
    equivalence

  def equivalence: Parser[Expression] =
    implication ~ "(⇔|<=>)".ri ~ equivalence ^^ { s => OpEquivalence(s._1._1, s._2) } |
    implication

  def implication: Parser[Expression] =
    disjunction ~ "(⇒|=>)".ri ~ implication ^^ { s => OpImplies(s._1._1, s._2) } |
    disjunction

  def disjunction: Parser[Expression] =
    conjunction ~ "(∨|or)".ri ~ disjunction ^^ { s => OpOr(s._1._1, s._2) } |
    conjunction

  def conjunction: Parser[Expression] =
    equality ~ "(∧|\\^|and)".ri ~ conjunction ^^ { s => OpAnd(s._1._1, s._2) } |
    equality

  def equality: Parser[Expression] =
    expression ~ "=".ri ~ equality ^^ { s => OpEquality(s._1._1, s._2) } |
    expression

  def expression: Parser[Expression] =
    negation |
    value    |
    variable |
    grouped

  def negation: Parser[Expression] = "(¬|not)".rbi ~ expressions ^^ { s => new OpNot(s._2) }

  def grouped: Parser[Expression] = "(" ~ expressions ~ ")" ^^ { s => s._1._2 }

  def variable: Parser[Term] = """\S+""".rb ^^ { case (text) => new Variable(text) }

  def value: Parser[Term] = """[0-9]+""".rb ^^ { case (text) => new Value(text.toInt) }

  def parsing[T](s: String)(implicit p: Parser[T]): T = {
    val phraseParser = phrase(p)
   val input = new CharSequenceReader(s)
    phraseParser(input) match {
      case Success(t,_)     => t
      case NoSuccess(msg,_) => throw new IllegalArgumentException(
        "Parse failed: '" + s + "': " + msg)
    }
  }

  def parse(s: String): Expression = {
    implicit val parserToTest = expressions
    parsing(s)
  }
}
