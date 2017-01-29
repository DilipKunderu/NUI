JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES =   pdollar.java PDollarRecognizer.java Point.java RecognizerResults.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
