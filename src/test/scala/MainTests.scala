import org.specs2.mutable._

class Week1Tests extends Specification {
  val solution = new Main

/* Testing the pyramid function */
  "The pyramid function" should {
    "do nothing for non positive n" in {
       solution.pyramid(-1) must have size(0)
    }
    "draw a nice pyramid" in {
      solution.pyramid(5) must be("""    *
   ***
  *****
 *******
*********
      """)
    }
  }
/* testing the uniq function */
  "The uniq function" should {
    "do nothing if there are no dups." in {
      solution.uniq("ababa") must be("ababa")
    }

    "remove only consecutive dup." in {
      solution.uniq("aabbb") must be("ab")
    }
  }

  /* testing isLycherel function */
  "The isLycherel function" should {
    "return false if is not Lycherel " in {
      solution.isLycherel(???) must be(false)
    }

    "return true if it is Lycherel" in {
      solution.isLycherel(???) must be(true)
    }
  }

}
