package com.jond3k.blog.zproof.mutations

import com.jond3k.blog.zproof.ast.{Expression, Op}

/**
 * For operators of the same type with the associativity property, we flatten them in to a single structure
 */
class AssociativityMutation {

  private def newOp[T](clazz: Class[T], expressions: Seq[Expression]): T =
    clazz.getConstructor(classOf[Seq[Expression]]).newInstance(expressions)

  private def associativeOperands(clazz: Class[_], op: Op): Expression = {

  }

  def traverse(expr: Expression): Expression = expr match {
    case op: Op if op.isAssociative => newOp(op.getClass, (traverse))
    case op: Op => newOp(op.getClass, op.expressions.map(traverse))
    case _ => expr
  }
}
