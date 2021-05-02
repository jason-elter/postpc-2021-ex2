PLAGIARISM STATEMENT:
I pledge the highest level of ethical principles in support of academic excellence.
I ensure that all of my work reflects my own abilities and not those of someone else.

ANSWER TO HYPOTHETICAL QUESTION:
We would need to make the following changes:
* An insertX function to SimpleCalculatorImpl.java (that inserts the x symbol).
* Add x as a possible operation symbol in all of SimpleCalculatorImpl.java.
* Change the implementation of insertEquals in SimpleCalculatorImpl.java so it can handle
  multiplication too.
* We would need to add functionality to SimpleCalculatorImpl.java output so that if
  an equation starts with a x then a 0 is added before.
* A new TextView in activity_main.xml with the x symbol.
* Find the new view in MainActivity.java and add an onClickListener that calls insertX and output.
* Tests in SimpleCalculatorImplTest.java: test printing an equation that has x in it,
  test printing the value of that equation, test starting an equation with * and then that the
  output is "0*", test multiple operators in a row (like ** or *- or +*),
  test multiplying a negative number, test multiplying two negative numbers.
* Tests in MainActivityTest.java: test when user clicks buttonX then activity should forward
  call to calculator and show the expected calculator output right away.
* Tests in AppFlowTest.java: test print and equals on multiple equations that contain the x operator
  For example "0*0", "-1*5", "-1*-2", "5***3", ...

No cheese, Gromit! Not a bit in the house!