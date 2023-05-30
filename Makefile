all: run

clean:
	rm -f out/Solver.jar out/Boyer-Moore.jar

out/Solver.jar: out/parcs.jar src/Solver.java
	@javac -cp out/parcs.jar src/Solver.java
	@jar cf out/Solver.jar -C src Solver.class -C src
	@rm -f src/Solver.class

out/Boyer-Moore.jar: out/parcs.jar src/Boyer-Moore.java
	@javac -cp out/parcs.jar src/Boyer-Moore.java
	@jar cf out/Boyer-Moore.jar -C src Boyer-Moore.class -C src
	@rm -f src/Boyer-Moore.class

build: out/Solver.jar out/Boyer-Moore.jar

run: out/Solver.jar out/Boyer-Moore.jar
	@cd out && java -cp 'parcs.jar:Solver.jar' Solver