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

  pureTest("Parsers.primatives: can parse all the primatives with a single parser"){
    expect(Parsers.primatives.parseAll("true") == Right(true))
    expect(Parsers.primatives.parseAll("\"foo\"") == Right("foo"))
    expect(Parsers.primatives.parseAll("123") == Right(123L))
  }

  pureTest("Parsers.primatives: fails if given an incorrect thing to parse"){
    expect(Parsers.primatives.parseAll("").isLeft)
  }
}
