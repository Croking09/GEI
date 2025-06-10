all: compile run

compile:
	mpicc main.c

run:
	mpirun --oversubscribe -np 3 ./a.out

clean:
	rm a.out
	clear