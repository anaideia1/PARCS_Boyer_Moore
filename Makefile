all: run

clean:
	rm -f out/Solver.jar out/BoyerMoore.jar

out/Solver.jar: out/parcs.jar src/Solver.java
	@javac -cp out/parcs.jar src/Solver.java
	@jar cf out/Solver.jar -C src Solver.class
	@rm -f src/Solver.class

out/BoyerMoore.jar: out/parcs.jar src/BoyerMoore.java
	@javac -cp out/parcs.jar src/BoyerMoore.java
	@jar cf out/BoyerMoore.jar -C src BoyerMoore.class
	@rm -f src/BoyerMoore.class

build: out/Solver.jar out/BoyerMoore.jar

run: out/Solver.jar out/BoyerMoore.jar
	@cd out && java -cp 'parcs.jar:Solver.jar' Solver