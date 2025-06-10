let hd l =
  match l with
  | [] -> raise(Failure("Lista vacia"))
  | h :: _ -> h
;;

let tl l =
  match l with
  | [] -> raise(Failure("Lista vacia"))
  | _ :: t -> t
;;

let rec length l =
  match l with
  | [] -> 0
  | _ :: t -> 1 + length t
;;

let rec compare_lengths l1 l2 =
  match (l1, l2) with
  | ([], []) -> 0
  | ([], _) -> -1
  | (_, []) -> 1
  | (_ :: t1, _ :: t2) -> compare_lengths t1 t2
;;

let compare_length_with l x =
  let rec comparar list num limit =
    if limit = 0 then match list with
      | [] -> 0
      | _ -> 1
    else match list with
      | [] -> -1
      | _ :: t -> comparar t num (limit - 1)
  in comparar l x x
;;

let init x f =
  if x < 0 then raise(Failure("Longitud no valida"))
  else let rec hacer len func list i =
    if i = -1 then list
    else hacer len func ((f i) :: list) (i - 1)
  in hacer x f [] (x - 1)
;;

let nth l i =
  if i >= length l then raise(Failure("Lista corta"))
  else if i < 0 then raise(Failure("Posicion no valida"))
  else let rec buscar list pos =
    match (list, pos) with
    | ([], _) -> raise(Failure("No hay elementos"))
    | (h :: _, 0) -> h
    | (_ :: t, pos) -> buscar t (pos - 1)
  in buscar l i
;;

let rec append l1 l2 =
  match l1 with
  | [] -> l2
  | h :: t -> h :: (append t l2)
;;

let rec rev_append l1 l2 =
  match l1 with
  | [] -> l2
  | h :: t -> rev_append t (h :: l2)
;;

let rev l =
  let rec girar l1 l2 =
    match l1 with
    | [] -> l2
    | h :: t -> girar t (h :: l2)
  in girar l []
;;

let concat l =
  let rec hacer list dest =
    match list with
    | [] -> dest
    | h :: t -> append h (hacer t dest)
  in hacer l []
;;

let flatten l = (*Igual que concat pero con otro nombre*)
  let rec hacer list dest =
    match list with
    | [] -> dest
    | h :: t -> append h (hacer t dest)
  in hacer l []
;;

let split l =
  let rec bucle l1 l2 l =
    match l with
    | [] -> (l1, l2)
    | h :: t -> let x = bucle l1 l2 t in (fst h :: fst x, snd h :: snd x)
  in bucle [] [] l
;;

let combine l1 l2 =
  let rec bucle l1 l2 l =
    match (l1, l2) with
    | ([], []) -> l
    | ([], _ :: _) -> raise(Failure("Longitudes distintas"))
    | (_ :: _, []) -> raise(Failure("Longitudes distintas"))
    | (h1 :: t1, h2 :: t2) -> let x = bucle t1 t2 l in (h1, h2) :: x
  in bucle l1 l2 []
;;

let map f l =
  let rec hacer f list dest =
    match list with
    | [] -> dest
    | h :: t -> (f h) :: (hacer f t dest)
  in hacer f l []
;;

let map2 f l1 l2 =
  let rec bucle f l1 l2 l =
    match (l1, l2) with
    | ([], []) -> l
    | ([], _ :: _) -> raise(Failure("Longitudes distintas"))
    | (_ :: _, []) -> raise(Failure("Longitudes distintas"))
    | (h1 :: t1, h2 :: t2) -> (f h1 h2) :: (bucle f t1 t2 l)
  in bucle f l1 l2 []
;;

let rev_map f l =
  let rec hacer f list dest =
    match list with
    | [] -> dest
    | h :: t -> hacer f t ((f h) :: dest)
  in hacer f l []
;;

let rec for_all f l =
  match l with
  | [] -> true
  | h :: t -> if f h = false then false else for_all f t
;;

let rec exists f l =
  match l with
  | [] -> false
  | h :: t -> if f h = true then true else exists f t
;;

let rec mem a l =
  match l with
  | [] -> false
  | h :: t -> if h = a then true else mem a t
;;

let rec find f l =
  match l with
  | [] -> raise(Failure("Not found"))
  | h :: t -> if f h = true then h else find f t
;;

let rec filter f l =
  match l with
  | [] -> []
  | h :: t -> if f h = true then h :: (filter f t) else filter f t
;;

let rec find_all f l = (*Lo mismo que filter pero con otro nombre*)
  match l with
  | [] -> []
  | h :: t -> if f h = true then h :: (find_all f t) else find_all f t
;;

let rec partition f l =
  match l with
  | [] -> ([], [])
  | h :: t -> let (l1, l2) = partition f t in 
  if f h = true then (h :: l1, l2)
  else (l1, h :: l2)
;;

let rec fold_left f x l =
  match l with
  | [] -> x
  | h :: t -> fold_left f (f x h) t
;;

let rec fold_right f l x =
  match l with
  | [] -> x
  | h :: t -> f h (fold_right f t x)
;;

let rec assoc a l =
  match l with
  | [] -> raise(Failure("Not found"))
  | h :: t -> if fst h = a then snd h else assoc a t
;;

let rec mem_assoc a l =
  match l with
  | [] -> false
  | h :: t -> if fst h = a then true else mem_assoc a t
;;

let rec remove_assoc a l =
  match l with
  | [] -> []
  | h :: t -> if fst h <> a then h :: remove_assoc a t else remove_assoc a t
;;