open Bintree

let rec is_bst ord tree =
  match tree with
  Empty -> true
  | Node (r, Empty, Empty) -> true
  | Node (r, Node (r1, ii, id), Empty) -> if (ord r1 r) then is_bst ord (Node (r1, ii, id)) else false
  | Node (r, Empty, Node (r2, id, dd)) -> if (ord r r2) then is_bst ord (Node (r2, id, dd)) else false
  | Node (r, Node (r1, ii, id), Node (r2, di, dd)) ->
    if (ord r1 r) && (ord r r2) then is_bst ord (Node (r1, ii, id)) && is_bst ord (Node (r2, di, dd))
    else false
;;

let bfs tree =
  let rec aux noVisitados =
    match noVisitados with
    [] -> []
    | h::t ->
      match h with
      Empty -> aux t
      | Node (r, i, d) -> r::aux (t @ [i; d])
  in
    match tree with
    Empty -> []
    | _ -> aux [tree] 
;;

let bfs' tree =
  let rec aux noVisitados l =
    match noVisitados with
    [] -> List.rev l
    | h::t ->
      match h with
      Empty -> aux t l
      | Node (r, i, d) -> aux (t @ [i; d]) (r::l)
  in
    match tree with
    Empty -> []
    | _ -> aux [tree] []
;;

let perfecto tree =
  let rec numNodos tree =
    match tree with
    Empty -> 0
    | Node (r, i, d) -> 1 + numNodos i + numNodos d
  in
  let rec altura = function
    Empty -> 0
    | Node (r, i, d) -> 1 + max (altura i) (altura d)
  in
    (float_of_int (numNodos tree)) = 2.**(float_of_int (altura tree)) -. 1.
;;

let casi_completo tree =
  let rec numNodos tree =
    match tree with
    Empty -> 0
    | Node (r, i, d) -> 1 + numNodos i + numNodos d
  in
    let rec alturaNodo nodo =
      match nodo with
      Empty -> 0
      | Node (r, i, d) -> 1 + max (alturaNodo i) (alturaNodo d)
  in
    let rec comprobar tree =
      match tree with
      Empty -> true
                          (*En caso de que un nodo este en el penultimo nivel y que el hijo izquierdo este vacio pero no el derecho entonces no es casi completo*)
      | Node (r, i, d) -> if alturaNodo (Node (r, i, d)) = 2 && d <> Empty && i = Empty then false
                          (*Como se comprueba para todos los nodos esten o no en el penultimo nivel entonces debemos comprobar los hijos*)
                          else (comprobar i) && (comprobar d)
  in
    (*Compruebo si el arbol tiene mas o iguales nodos a los que admite un arbol de 1 altura menos*)
    if (float_of_int (numNodos tree)) >= 2.**(float_of_int ((alturaNodo tree) - 1)) -. 1. then
      (*En caso afirmativo significa que tenemos un posible arbol casi completo por lo que compruebo los nodos del ultimo nivel*)
      comprobar tree
    else false
;;