let comprobar m n (i1, j1) (i2, j2) = (*Esta funcion comprueba si 2 casillas son validas para la solucion*)
  (i1 < m && i2 < m && j1 < n && j2 < n) && (*Se comprueba si ambas casillas estan dentro del tablero*)
  ((i2, j2) = (i1, j1 + 1) || (i2, j2) = (i1, j1 - 1) ||
  (i2, j2) = (i1 + 1, j1) || (i2, j2) = (i1 - 1, j1)) (*Se comprueba tambien si la segunda casilla es adyacente a la primera*)
;; (*Si se cumplen ambas condiciones entonces la segunda casilla puede ser la siguiente a la primera*)

let valid_path m n ini fin path =
  let rec aux l =
    match l with
    [] -> false
    | h::[] -> if h = fin then true
               else false
    | h1::h2::t ->
      if comprobar m n h1 h2 then aux (h2::t)
      else false
  in
    match path with
    [] -> false
    | h::t -> if h = ini then aux path
              else false
;;

let path_weight v path =
  let rec aux l suma =
    match l with
    [] -> suma
    | (i, j)::t -> aux t (suma + v.(i).(j))
  in aux path 0
;;

let min_weight_path v ini fin =
;;

(*Funcion para medir tiempos*)
let crono f x =
  let t = Sys.time () in
  f x;
  Sys.time () -. t;;
;;