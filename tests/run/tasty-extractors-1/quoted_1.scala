import scala.quoted._
import dotty.tools.dotc.quoted.Toolbox._

import scala.tasty._

object Macros {

  implicit inline def printTree[T](x: => T): Unit =
    ~impl('(x))(TopLevelSplice.tastyContext) // FIXME infer TopLevelSplice.tastyContext within top level ~

  def impl[T](x: Expr[T])(implicit tasty: Tasty): Expr[Unit] = {
    import tasty._
    import Context._

    val tree = x.toTasty
    val treeStr = tree.show
    val treeTpeStr = tree.tpe.show

    '{
      println(~treeStr.toExpr)
      println(~treeTpeStr.toExpr)
      println()
    }
  }
}
