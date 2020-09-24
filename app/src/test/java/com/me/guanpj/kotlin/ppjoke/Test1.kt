package com.me.guanpj.kotlin.ppjoke

sealed class Expr {
    data class Num(val value: Int): Expr()

    data class Operate(val opName: String, val left: Expr, val right: Expr): Expr()
}

fun simplyExpr(expr: Expr): Expr = when(expr) {
    is Expr.Num -> expr
    is Expr.Operate -> when(expr) {
        Expr.Operate("+", Expr.Num(0), expr.right) -> expr.right
        Expr.Operate("+", expr.left, Expr.Num(0)) -> expr.left
        else -> expr
    }

}

fun main() {
    val simplyExpr = simplyExpr(Expr.Num(0))
}