(*x - y;;*) (*Error porque no hay valores en x ni y*)
(*Error: Unbound value x*)

let x = 1;;
(*val x : int = 1*)

(*x - y;;*) (*Error porque y sigue sin valor*)
(*Error: Unbound value y*)

let y = 2;;
(*val y : int = 2*)

x - y;;
(*- : int = -1*)

(*let x = y in x - y;;*)
(*- : int = 0*)

(function x -> x - y) (y);;

x - y;;
(*- : int = -1*)

(*z;;*) (*Error porque no estamos asignando un valor a z*)
(*Error: Unbound value z*)

let x = 5;;
(*val x : int = 5*)

x + y;;
(*- : int = 7*)

(*let x = x + y in let y = x * y in x + y + z;;*) (*Como no hemos dado valor a z no podemos operar con ella*)
(*Error: Unbound value z*)

(*(function x -> ((function y -> x + y + z) (x * y))) (x + y);;*)

(*x + y + z;;*) (*Error porque no damos valor a z*)
(*Error: Unbound value z*)

function x -> 2 * x;;
(*- : int -> int = <fun>*)

(function x -> 2 * x) (2 + 1);;
(*- : int = 6*)

(function x -> 2 * x) 2 + 1;;
(*- : int = 5*)

let f = function x -> 2 * x;;
(*val f : int -> int = <fun>*)

f;;
(*- : int -> int = <fun>*)

f (2 + 1);;
(*- : int = 6*)

f 2 + 1;;
(*- : int = 5*)

f x;;
(*- : int = 10*)

let x = 100;;
(*val x : int = 100*)

f x;;
(*- : int = 200*)

let m = 1000;;
(*val m : int = 1000*)

let g = function x -> x + m;;
(*val g : int -> int = <fun>*)

g;;
(*- : int -> int = <fun>*)

g 3;;
(*- : int = 1003*)

(*g 3.0;;*) (*Error de tipo porque intentamos usar un float como argumento cuando operamos con int*)
(*Error: Expected an int but argument was type float*)

let m = 7;;
(*val m : int = 7*)

g 3;;
(*- : int = 1003*)
(*Aunque declaremos una nueva m no se actualiza en la funcion por lo que sigue dando el mismo resultado*)

let istrue = function true -> true;;
(*Warning 8 [partial-match]: this pattern-matching is not exhaustive.
Here is an example of a case that is not matched:
false
val istrue : bool -> bool = <fun>
*)
(*Da un warning porque la funcion no maneja todos los casos posibles, es decir, solo funciona si la entrada es true*)

istrue;;
(*- : bool -> bool = <fun>*)

istrue (1 < 2);;
(*- : bool = true*)

(*istrue (2 < 1);;*) (*Error de ejecucion porque damos a la funcion un valor que no maneja*)
(*Excepcion: Match_failure ("//toplevel//", 1, 13).*)

(*istrue 0;;*) (*Error de tipo porque el argumento deberia ser de tipo bool*)
(*This expresion has type int but type bool was expected*)

let iscero_v1 = function 0 -> true;;
(*Warning 8 [partial-match]: this pattern-matching is not exhaustive.
Here is an example of a case that is not matched:
1
val iscero_v1 : int -> bool = <fun>
*)
(*Da un warning porque la funcion solo maneja el 0*)

iscero_v1 0;;
(*- : bool = true*)

(*iscero_v1 0.;;*) (*Error de tipo porque damos un argumento float cuando deberia ser tipo int*)
(*This expresion has type float but type int was expected*)

(*iscero_v1 1;;*) (*Error de ejecucion porque damos a la funcion un argumento que no maneja*)
(*Excepcion: Match_failure ("//toplevel//", 1, 16).*)

let iscero_v2 = function 0 -> true | _ -> false;;
(*val iscero_v2 : int -> bool = <fun>*)

iscero_v2 0;;
(*- : bool = true*)

iscero_v2 1;;
(*- : bool = false*)

let all_to_true = function true -> true | false -> true;;
(*val all_to_true : bool -> bool = <fun>*)

all_to_true (1 < 2);;
(*- : bool = true*)

all_to_true (2 < 1);;
(*- : bool = true*)

(*all_to_true 0;;*) (*Error de tipo porque el argumento es de tipo int cuando deberia de ser de tipo bool*)
(*This expresion has type int but type bool was expected*)

let first_all_to_true = all_to_true;;
(*val first_all_to_true : bool -> bool = <fun>*)

let all_to_true = function x -> true;;
(*val all_to_true : 'a -> bool = <fun>*)

all_to_true (1 < 2);;
(*- : bool = true*)

all_to_true (2 < 1);;
(*- : bool = true*)

all_to_true 0;;
(*- : bool = true*)

(*first_all_to_true 0;;*) (*Error de tipos porque el argumento es del tipo equivocado*)
(*This expresion has type int but type bool was expected*)
