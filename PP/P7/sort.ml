let rec insert x = function
  [] -> [x]
  | h::t -> if x <= h then x::h::t
            else h::insert x t
;;

let rec isort = function
  [] -> []
  | h::t -> insert h (isort t)
;;

let bigl = List.init 175000 (fun x -> 175000 - x);;
(*Esta lista produce stack overflow*)

(* insert_t: a' -> a' list -> a' list *)
let insert_t x l =
  let rec aux l1 l2 =
    match l2 with
    [] -> List.rev_append l1 [x]
    | h::t -> if x <= h then List.rev_append l1 (x::h::t)
              else aux (h::l1) t
  in aux [] l
;;

(* isort_t: a' list -> a' list *)
let isort_t l =
  let rec aux l a =
    match l with
    [] -> a
    | h::t -> aux t (insert_t h a)
  in aux l []
;;

let rec rlist n =
  Random.self_init();
  if n < 0 then raise (Failure "Argumento no valido")
  else if n = 0 then []
  else Random.int (2*n) :: rlist (n - 1)
;;

let crono f x =
  let t = Sys.time () in
  let _ = f x in
  Sys.time () -. t
;;

let lc1 = List.init 10000 (fun x -> x);;
let lc2 = List.init 20000 (fun x -> x);;

(*
# crono isort lc1;;
- : float = 0.00123500000000009713
# crono isort lc2;;
- : float = 0.00166099999999991255
# crono isort_t lc1;;
- : float = 2.039122
# crono isort_t lc2;;
- : float = 10.4548929999999984   

En este caso la funcion isort es mas rapida porque inserta por el principio, este es el peor caso de la funcion isort_t
*)

let ld1 = List.init 10000 (fun x -> 10000 - x);;
let ld2 = List.init 20000 (fun x -> 20000 - x);;

(*
# crono isort ld1;;
- : float = 1.37461299999999831
# crono isort ld2;;
- : float = 7.08937699999999893
# crono isort_t ld1;;
- : float = 0.00053399999999825809
# crono isort_t ld2;;
- : float = 0.00132599999999882812

En este caso ya notamos una diferencia apreciable entre los tiempos de isort e isort_t porque ya necesitamos ordenar
los elementos lo cual es mas eficiente con isort_t que es recursiva terminal
*)

let lr1 = rlist 10000;;
let lr2 = rlist 20000;;

(*
# crono isort lr1;;
- : float = 1.00698000000000221
# crono isort lr2;;
- : float = 4.97260400000000047
# crono isort_t lr1;;
- : float = 0.458026000000000266
# crono isort_t lr2;;
- : float = 1.88969799999999921

Este caso seria un caso medio entre los 2 anteriores, de todas formas, necesitamos ordenar los elementos y por tanto
la funcion insert_t es mas eficiente
*)

let insert_g ro x l =
  let rec aux l1 l2 =
    match l2 with
    [] -> List.rev_append l1 [x]
    | h::t -> if (ro x h) then List.rev_append l1 (x::h::t)
              else aux (h::l1) t
  in aux [] l
;;

(* (a' -> a' -> bool) -> a' list -> a' list *)
let isort_g ro l =
  let rec aux l a =
    match l with
    [] -> a
    | h::t -> aux t (insert_g ro h a)
  in aux l []
;;

let rec split l =
  match l with
  h1::h2::t -> let t1, t2 = split t in h1::t1, h2::t2
  | _ -> l, []
;;

let rec merge (l1,l2) =
  match l1, l2 with
  [], l | l, [] -> l
  | h1::t1, h2::t2 -> if h1 <= h2 then h1::merge (t1,l2)
  else h2::merge (l1, t2)
;;

let rec msort l =
  match l with
  [] | [_] -> l
  | _ -> let l1, l2 = split l in merge (msort l1, msort l2)
;;

let bigl2 = List.init 175000 (fun x -> 175000 - x);;
(*Esta lista produce stack overflow*)

let split_t l =
  let rec aux l s1 s2 =
    match l with
    [] -> s1, s2
    | h::[] -> (h::s1), s2
    | h1::h2::t -> aux t (h1::s1) (h2::s2)
  in aux l [] []
;;

let merge_t (l1, l2) =
  let rec aux l1 l2 d =
    match l1, l2 with
    [], [] -> List.rev d
    | [], h::t | h::t, [] -> aux [] t (h::d)
    | h1::t1, h2::t2 -> if h1 <= h2 then aux t1 (h2::t2) (h1::d) else aux (h1::t1) t2 (h2::d)
  in aux l1 l2 []
;;

let rec msort' l =
  match l with
  [] | _::[] -> l
  | _ -> let l1, l2 = split_t l in merge_t (msort' l1, msort' l2)
;;

let bigl3 = [];;
(*
La funcion msort' no produce stack overflow a pesar de no ser recursiva terminal porque esta funcion no
deja operaciones pendientes, solo es una sucesion de operaciones split_t y merge_t que si son
recursivas terminales
*)

(*
# crono msort' lc1;;
- : float = 0.00653300000000001102
# crono msort' lc2;;
- : float = 0.016123

# crono msort' ld1;;
- : float = 0.00718299999999993943
# crono msort' ld2;;
- : float = 0.0149700000000000388

# crono msort' lr1;;
- : float = 0.00689499999999998447
# crono msort' lr2;;
- : float = 0.0155110000000000525

Los tiempos obtenidos son mucho inferiores al algoritmo de ordenacion anterior debido a su complejidad, mientras que
el algoritmo de ordenacion por insercion suele ser de complejidad cuadratica, O(n^2), el algoritmo de
ordenacion por fusion suele ser O(nlogn)
*)

(*
# crono msort lr1;;
- : float = 0.00851000000000001755
# crono msort lr2;;
- : float = 0.0162459999999999827

Los tiempos de ordenacion entre msort y msort' en vectores pseudoaleatorios de tamaño 10000 y 20000 respectivamente
son muy parecidos a pesar de que msort' utiliza funciones auxiliares que emplean la recursividad terminal
*)

let merge_g ro (l1, l2) =
  let rec aux l1 l2 d =
    match l1, l2 with
    [], [] -> List.rev d
    | [], h::t | h::t, [] -> aux [] t (h::d)
    | h1::t1, h2::t2 -> if (ro h1 h2) then aux t1 (h2::t2) (h1::d) else aux (h1::t1) t2 (h2::d)
  in aux l1 l2 []
;;

(* ('a -> 'a -> bool) -> 'a list -> 'a list = <fun> *)
let rec msort_g ro l =
  match l with
  [] | _::[] -> l
  | _ -> let l1, l2 = split_t l in merge_g ro ((msort_g ro l1), (msort_g ro l2))
;;

(*La funcion split_g seria igual que split_t por lo que no es necesario reescribirla*)