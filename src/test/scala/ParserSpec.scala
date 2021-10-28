package proclang

import weaver.SimpleIOSuite

// Suites must be "objects" for them to be picked by the framework
object ParserSpec extends SimpleIOSuite {
  pureTest("Parsers.boolean: can parse 'true' as a true boolean"){
    expect(Parsers.boolean.parseAll("true") == Right(true))
  }

  pureTest("Parsers.boolean: can parse 'false' as a false boolean"){
    expect(Parsers.boolean.parseAll("false") == Right(false))
  }

  pureTest("Parsers.boolean: fails if given a non 'true' or 'false' value"){
    expect(Parsers.boolean.parseAll("foo").isLeft)
  }

  pureTest("Parsers.boolean: fails if given an empty string"){
    expect(Parsers.boolean.parseAll("").isLeft)
  }

  pureTest("""Parsers.string: can parse '"foo"' as the string "foo""""){
    expect(Parsers.string.parseAll("\"foo\"") == Right("foo"))
  }

  pureTest("Parsers.string: fails if starting quote is missing"){
    expect(Parsers.string.parseAll("foo\"").isLeft)
  }

  pureTest("Parsers.string: fails if ending quote is missing"){
    expect(Parsers.string.parseAll("\"foo").isLeft)
  }

  pureTest("Parsers.string: fails if all quotes are missing"){
    expect(Parsers.string.parseAll("foo").isLeft)
  }

  pureTest("""Parsers.string: can parse '`foo`' as the string "foo""""){
    expect(Parsers.string.parseAll("`foo`") == Right("foo"))
  }

  pureTest("Parsers.string: fails if starting backquote is missing"){
    expect(Parsers.string.parseAll("foo`").isLeft)
  }

  pureTest("Parsers.string: fails if ending backquote is missing"){
    expect(Parsers.string.parseAll("`foo").isLeft)
  }

  pureTest("Parsers.string: fails if all backquote are missing"){
    expect(Parsers.string.parseAll("foo").isLeft)
  }

  pureTest("Parsers.string: fails if given an empty string"){
    expect(Parsers.string.parseAll("").isLeft)
  }

  pureTest("Parsers.number: can parse '123' as the number 123"){
    expect(Parsers.number.parseAll("123") == Right(123L))
  }

  pureTest("Parsers.number: fails if given an empty string"){
    expect(Parsers.number.parseAll("").isLeft)
  }

  pureTest("Parsers.number: fails if given a char that is not a number"){
    expect(Parsers.number.parseAll("foo").isLeft)
  }

  pureTest("Parsers.primative: can parse all the primatives with a single parser"){
    expect(Parsers.primative.parseAll("true") == Right(true))
    expect(Parsers.primative.parseAll("\"foo\"") == Right("foo"))
    expect(Parsers.primative.parseAll("123") == Right(123L))
  }

  pureTest("Parsers.primative: fails if given an incorrect thing to parse"){
    expect(Parsers.primative.parseAll("").isLeft)
  }

  pureTest("Parsers.structKeyValue: can parse a key value pair with whitespace"){
    expect(Parsers.structKeyValue.parseAll("\"a\" : 1") == Right(("a", 1L)))
  }

  pureTest("Parsers.structKeyValue: can parse a key value pair with no whitespace"){
    expect(Parsers.structKeyValue.parseAll("\"a\":1") == Right(("a", 1L)))
  }

  pureTest("Parsers.struct: can parse an empty struct"){
    expect(Parsers.struct.parseAll("{}") == Right(Map.empty))
  }

  pureTest("Parsers.struct: can parse a single pair struct"){
    expect(Parsers.struct.parseAll("{\"a\" : 1}") == Right(Map("a" -> 1L)))
  }

  pureTest("Parsers.struct: can parse a multiple pair struct"){
    expect(Parsers.struct.parseAll("{\"a\" : 1, \"b\": true}") == Right(Map("a" -> 1L, "b" -> true)))
  }

  pureTest("Parsers.struct: can parse a struct over multiple lines"){
    expect(Parsers.struct.parseAll("{\n  \"a\" : 1,\n  \"b\": true}") == Right(Map("a" -> 1L, "b" -> true)))
  }

  pureTest("Parsers.sequence: can parse an empty sequence"){
    expect(Parsers.sequence.parseAll("[]") == Right(List.empty))
  }

  pureTest("Parsers.sequence: can parse a single value sequence"){
    expect(Parsers.sequence.parseAll("[1]") == Right(List(1L)))
  }

  pureTest("Parsers.sequence: can parse a multiple value sequence"){
    expect(Parsers.sequence.parseAll("[1, true]") == Right(List(1L, true)))
  }

  pureTest("Parsers.sequence: can parse a sequence over multiple lines"){
    expect(Parsers.sequence.parseAll("[\n  1,\n  true]") == Right(List(1L, true)))
  }

  pureTest("Parsers.collection: can parse all the collections with a single parser"){
    expect(Parsers.collecton.parseAll("{\"a\" : 1}") == Right(Map("a" -> 1L)))
    expect(Parsers.collecton.parseAll("[1]") == Right(List(1L)))
  }

  pureTest("Parsers.collection: fails if given an incorrect thing to parse"){
    expect(Parsers.collecton.parseAll("").isLeft)
  }
}
