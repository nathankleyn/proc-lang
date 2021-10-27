package proclang

import cats.parse.{Numbers, Parser => P, Parser0 => P0}

object Parsers {
  type Primative = Boolean | String | Long
  type Identifier = String

  // FIXME: Currently getting a recursive definition error if including self
  // types within these collections - see https://github.com/lampepfl/dotty/issues/10136
  // Needs resolving but for now collections will be flat to make progress
  type Struct = Map[Identifier, Primative | Fn]
  type Sequence = List[Primative | Fn]
  type Collection = Sequence | Struct

  // FIXME: Implement body type
  case class Fn(argDefs: Struct, body: Any) {
    // FIXME: Implement return type
    def run(args: Struct): Any = {
      // FIXME: Implement calling of function
    }
  }

  val whitespace: P[Unit] = P.charIn(" \t\r\n").void
  val whitespaces0: P0[Unit] = whitespace.rep0.void

  val boolean: P[Boolean] = P.string("true").as(true)
    .orElse(P.string("false").as(false))

  val string: P0[String] =
    P.oneOf0(
      P.charsWhile0(c => c != '`').surroundedBy(P.char('`')) ::
      P.charsWhile0(c => c != '"').surroundedBy(P.char('"')) ::
      Nil
    )

  val number: P[Long] = Numbers.digits.map(_.toLong)

  val primatives: P0[Primative] = P.oneOf0(boolean :: string :: number :: Nil)

  val map: P[Map[String, Primative]] =
    P.char('{') *>
      whitespaces0 *>
      P.repAs(mapValue) <*
      whitespaces0 <*
      P.char('}')
}
