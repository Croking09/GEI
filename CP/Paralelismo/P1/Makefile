all: compile run clean

compile:
	mpicc main.c

run:
	mpirun -np 4 ./a.out

clean:
	rm a.out
	clear