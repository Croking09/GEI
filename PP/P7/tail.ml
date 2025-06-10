let to0from n =
  let rec aux n l =
    if n < 0 then List.rev l
    else aux (n-1) (n::l)
  in aux n []
;;

let fromto m n =
  let rec aux l i =
    if i > n then []
    else if i = n then List.rev (i::l)
    else aux (i::l) (i+1)
  in aux [] m
;;

let remove x l =
  let rec aux x o d =
    match o with
    [] -> List.rev d
    | h::t -> if x = h then List.rev (List.rev_append t d) else aux x t (h::d)
  in aux x l []
;;

let compress l =
  let rec aux o d =
    match o with
    [] -> List.rev d
    | h::[] -> aux [] (h::d)
    | h1::h2::t -> if h1 = h2 then aux t (h2::d)
    else aux t (h2::h1::d)
  in aux l []
;;

let append' l1 l2 = List.rev_append (List.rev l1) l2;;

let map' f l =
  let rec aux o d =
    match o with
    [] -> List.rev d
    | h::t -> aux t ((f h)::d)
  in aux l []
;;

let fold_right' f l init =
  let rec aux l init =
    match l with
    | [] -> init
    | h::t -> aux t (f h init)
  in aux l init
;;

let incseg l =
  let rec aux o d =
    match o with
    [] -> List.rev d
    | h1::t -> let nueva_suma =
      (match d with
      [] -> 0
      | h2::_ -> h2) + h1
    in aux t (nueva_suma::d)
  in aux l []
;;