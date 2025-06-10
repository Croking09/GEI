let come (i1,j1) (i2,j2) = (* Funcion para saber si 2 casillas se amenazan *)
  i1 = i2 || j1 = j2 || abs (i2-i1) = abs (j2-j1)
;;

let compatible p l = (* Funcion que comprueba si una posicion p no se come con el resto de posiciones utilizadas (contenidas en la lista l) *)
  not (List.exists (come p) l)
;;

let queens n =
  let rec completa path i j =
    if i > n then [path]
    else if j > n then []
    else if compatible (i, j) path then
      let filaSig = completa ((i, j) :: path) (i + 1) 1 in
      let colSig = completa path i (j + 1) in
      filaSig @ colSig
    else completa path i (j + 1)
  in completa [] 1 1
;;

let come_n n (i1,j1) (i2,j2) =
  (i1 = i2 || j1 = j2 || abs (i2-i1) = abs (j2-j1)) && (i1 <> i2 && j1 <> j2) && (i1 <= n && i2 <= n && j1 <= n && j2 <= n)
  (*Si son iguales no se amenazan y si estan fuera del tablero si se amenazan*)
;;

let compatible_n n p l =
  not (List.exists (come_n n p) l)
;;

let is_queens_sol n l =
  let rec aux l2 =
    match l2 with
    [] -> false
    | h::[] -> compatible_n n h l
    | h::t -> aux t
  in 
  if n = 0 && l = [] then true
  else aux l
;;