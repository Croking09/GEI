let length l =
  let aux = ref l in
  let i = ref 0 in
  while !aux <> [] do
    i := !i + 1;
    aux := List.tl !aux;
  done;
  !i
;;

let last l =
  if l = [] then failwith "last"
  else
    let aux = ref l in
    while List.tl !aux != [] do
      aux := List.tl !aux;
    done;
    List.hd !aux
;;

let nth l n =
  if n < 0 then raise (Invalid_argument "List.nth")
  else
    let aux = ref l in
    let i = ref 0 in
    while !i <> n do
      i := !i + 1;
      aux := List.tl !aux;
      if !aux = [] then raise (Failure "nth")
    done;
    List.hd !aux
;;

let rev l =
  let aux = ref l in
  let result = ref [] in
  while !aux <> [] do
    result := (List.hd !aux)::!result;
    aux := List.tl !aux;
  done;
  !result
;;

let append l1 l2 =
  let result = ref [] in
  let aux1 = ref l1 in
  let aux2 = ref l2 in
  while !aux1 <> [] do
    result := (List.hd !aux1)::!result;
    aux1 := List.tl !aux1;
  done;
  while !aux2 <> [] do
    result := (List.hd !aux2)::!result;
    aux2 := List.tl !aux2;
  done;
  rev !result (*Funcion de arriba*)
;;

let concat l =
  let result = ref [] in
  for i = 0 to ((length l) - 1) do
    let aux = ref (List.hd l) in
    while !aux <> [] do
      result := (List.hd !aux)::!result;
      aux := List.tl !aux;
    done;
  done;
  rev !result
;;

let concat l =
  let result = ref [] in
  let aux = ref l in
  while !aux <> [] do
    let aux2 = ref (List.hd !aux) in
    while !aux2 <> [] do
      result := (List.hd !aux2)::!result;
      aux2 := List.tl !aux2;
    done;
    aux := List.tl !aux;
  done;
  rev !result
;;

let for_all f l =
  let aux = ref l in
  let result = ref true in
  while !aux <> [] do
    if not (f (List.hd !aux)) then
      result := false;
    aux := List.tl !aux;
  done;
  !result
;;

let exists f l =
  let aux = ref l in
  let result = ref false in
  while !aux <> [] do
    if (f (List.hd !aux)) then
      result := true;
    aux := List.tl !aux;
  done;
  !result
;;


let find_opt f l =
  let aux = ref l in
  let result = ref None in
  let found = ref false in
  while !aux <> [] do
    if (f (List.hd !aux)) && (not !found) then
      result := Some (List.hd !aux);
      found := true;
    aux := List.tl !aux;
  done;
  !result
;;


let iter f l =
  let aux = ref l in
  while !aux <> [] do
    f (List.hd !aux);
    aux := List.tl !aux;
  done;
;;

let fold_left f init l =
  let result = ref init in
  let aux = ref l in
  while !aux <> [] do
    result := f !result (List.hd !aux);
    aux := List.tl !aux;
  done;
  !result
;;