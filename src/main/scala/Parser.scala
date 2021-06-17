package proclang

import cats.parse.{Parser => P, Parser0 => P0}

object Parsers {
  val whitespace: P[Unit] = P.charIn(" \t\r\n").void
  val whitespaces0: P0[Unit] = whitespace.rep0.void

  val boolean: P[Boolean] = P.string("true").as(true)
    .orElse(P.string("false").as(false))

  val string: P0[String] =
    P.oneOf0(
      P.charsWhile0(c => c == '`').surroundedBy(P.char('`')) ::
      P.charsWhile0(c => c == '"').surroundedBy(P.char('"')) ::
      Nil
    )

  val primatives: P0[Boolean | String] = P.oneOf0(boolean :: string :: Nil)
}
