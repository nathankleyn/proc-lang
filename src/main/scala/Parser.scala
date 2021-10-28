package proclang

import scala.collection.mutable

import cats.parse.{Appender, Accumulator0, Numbers, Parser => P, Parser0 => P0}

object Parsers {
  type Primative = Boolean | String | Long
  type Identifier = String

  // FIXME: Currently getting a recursive definition error if including self
  // types within these collections - see https://github.com/lampepfl/dotty/issues/10136
  // Needs resolving but for now collections will be flat to make progress
  type Struct = Map[Identifier, Primative]
  type Sequence = List[Primative]
  type Collection = Sequence | Struct

  val whitespace: P[Unit] = P.charIn(" \t\r\n").void
  val whitespaces0: P0[Unit] = whitespace.rep0.void

  val boolean: P[Boolean] = P.string("true").as(true)
    .orElse(P.string("false").as(false))

  val string: P[String] =
    P.oneOf(
      (P.char('`').backtrack *> P.until(P.char('`')) <* P.char('`')) ::
      (P.char('"').backtrack *> P.until(P.char('"')) <* P.char('"')) ::
      Nil
    )

  val number: P[Long] = Numbers.digits.map(_.toLong)

  val primative: P[Primative] = P.oneOf(boolean :: string :: number :: Nil)

  val structKeyValue: P[(String, Primative)] =
    string ~ (P.char(':').surroundedBy(whitespaces0) *> primative)

  val struct: P[Struct] =
    P.char('{') *>
      whitespaces0 *>
      structKeyValue.repSep0(P.char(',').surroundedBy(whitespaces0)).map(_.toMap) <*
      whitespaces0 <*
      P.char('}')

  val sequence: P[Sequence] =
    P.char('[') *>
      whitespaces0 *>
      primative.repSep0(P.char(',').surroundedBy(whitespaces0)) <*
      whitespaces0 <*
      P.char(']')

  val collecton: P[Struct | Sequence] =
    P.oneOf(struct :: sequence :: Nil)
}
