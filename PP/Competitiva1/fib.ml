let multiply_matrix f m = (*Esta funcion multiplica 2 matrices 2*2 sin ningun tipo de recursion ni bucle*)
  let result = 
    ((Big_int_Z.add_big_int (Big_int_Z.mult_big_int (fst (fst f)) (fst (fst m))) (Big_int_Z.mult_big_int (snd (fst f)) (fst (snd m))),
    Big_int_Z.add_big_int (Big_int_Z.mult_big_int (fst (fst f)) (snd (fst m))) (Big_int_Z.mult_big_int (snd (fst f)) (snd (snd m)))),
    (Big_int_Z.add_big_int (Big_int_Z.mult_big_int (fst (snd f)) (fst (fst m))) (Big_int_Z.mult_big_int (snd (snd f)) (fst (snd m))),
    Big_int_Z.add_big_int (Big_int_Z.mult_big_int (fst (snd f)) (snd (fst m))) (Big_int_Z.mult_big_int (snd (snd f)) (snd (snd m)))))
  in
  result
;;

let rec power_matrix f n = (*Esta funcion recursiva eleva la matriz f a n*)
  if n <> 0 && n <> 1 then
    let m = ((Big_int_Z.unit_big_int,Big_int_Z.unit_big_int),(Big_int_Z.unit_big_int,Big_int_Z.zero_big_int)) in (*En caso de que el exponente sea mayor de 1 entonces creamos la matriz ((1,1),(1,0)) como indica el metodo*)
    let y = power_matrix f (n / 2) in
    let x = multiply_matrix y y in
    if n mod 2 = 0 then x (*Y seguimos las reglas del metodo para n par o impar*)
    else multiply_matrix x m
  else f (*En caso de que el exponente sea 0 o 1 entonces devolvemos la misma matriz pues ya sabemos el resultado*)
;;

let fib n =
  if n = 0 then Big_int_Z.zero_big_int (*En caso de que calculemos el termino 0 devolvemos 0 sin ningun calculo*)
  else let f = ((Big_int_Z.unit_big_int,Big_int_Z.unit_big_int),(Big_int_Z.unit_big_int,Big_int_Z.zero_big_int)) in
  fst (fst (power_matrix f (n - 1))) (*En caso contrario devolvemos el primer elemento de la matriz f elevada al indice del elemento a calcular -1*)
;;                                   (*pues el metodo de exponenciacion de matrices deja como resultado fib (n+1) en el primer elemento de la matriz*)

(*Funcion para medir tiempos*)
let crono f x =
  let t = Sys.time () in
  f x;
  Sys.time () -. t;;
;;

(*Print usando la implementacion de arriba*)
let printFib n = print_endline ((Big_int_Z.string_of_big_int (fib n)));; (*Esta funcion simplemente imprime el resultado de la funcion que*)
                                                                         (*calcula el termino n-esimo de la sucesion de Fibonacci*)

(*Print de tiempo de ejecucion
let printFib n = print_endline ((string_of_float (crono fib n)));;*) (*Esta funcion simplemente imprime el resultado de la funcion crono aplicada a fib n*)

if Array.length(Sys.argv) = 2 then (*Comprueba si el numero de argumentos es el adeacuado.*)
  if (int_of_string (Array.get Sys.argv(1))) < 0 then (*Si hay 2 argumentos (el propio nombre del ejecutable y un numero)*)
    raise (Failure "Argumento no valido")             (*entonces comprueba que el numero sea positivo o 0*)
  else printFib (int_of_string (Array.get Sys.argv(1))) (*Si es asi entonces calcula el termino n-esimo de la sucesion de Fibonacci*)
else raise (Failure "Numero de argumentos no valido") (*En caso de que no se cumplan algunas de las 2 condiciones entonces lanza una excepcion*)
;;

(*Principio matematico:*)
(*Para calcular el termino n-esimo de la sucesion de Fibonacci estoy aplicando el metodo de exponenciacion de matrices. Este dice que si elevamos
la matriz ((1,1),(1,0)) a n entonces la matriz resultantes será de la forma: ((fib (n + 1),fib n),(fib n,0))*)

(*Comando para ejecucion:
ocamlfind ocamlc -linkpkg -package zarith -o fib fib.ml*)