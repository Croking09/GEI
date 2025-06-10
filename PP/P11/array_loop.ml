let append v1 v2 =
  if (Array.length v1) + (Array.length v2) > Sys.max_array_length then
    raise (Invalid_argument "Array.append")
  else
    let result = ref (Array.init ((Array.length v1) + (Array.length v2)) (function x -> v1.(0))) in
    for i = 0 to ((Array.length v1) - 1) do
      !result.(i) <- v1.(i);
    done;
    for i = (Array.length v1) to ((Array.length v2) + (Array.length v1) - 1) do
      !result.(i) <- v2.(i-(Array.length v1));
    done;
    !result
;;

let sub a pos len =
  if pos < 0 || len < 0 || (pos + len > Array.length a) then
    raise (Invalid_argument "Array.sub")
  else
    let result = ref (Array.init len (function x -> a.(0))) in
    for i = 0 to (len - 1) do
      !result.(i) <- a.(i+pos);
    done;
    !result
;;

let copy a =
  let result = ref (Array.init (Array.length a) (function x -> a.(0))) in
  for i = 0 to ((Array.length a) - 1) do
    !result.(i) <- a.(i);
  done;
  !result
;;

let fill a pos len x =
  if pos < 0 || len < 0 || (pos + len > Array.length a) then
    raise (Invalid_argument "Array.fill")
  else
    for i = pos to (pos + len - 1) do
      a.(i) <- x;
    done;
;;

let blit src src_pos dst dst_pos len =
  if src_pos < 0 || len < 0 || dst_pos < 0 || (src_pos + len > Array.length src) || (dst_pos + len > Array.length dst) then
    raise (Invalid_argument "Array.blit")
  else
    let aux = ref dst_pos in
    for i = src_pos to (src_pos + len - 1) do
      dst.(!aux) <- src.(i);
      aux := !aux + 1;
    done;
;;

let to_list a =
  let result = ref [] in
  for i = ((Array.length a) - 1) downto 0 do
    result := (a.(i))::!result;
  done;
  !result
;;

let iter f a =
  for i = 0 to ((Array.length a) - 1) do
    f (a.(i));
  done;
;;

let fold_left f init a =
  let result = ref init in
  for i = 0 to ((Array.length a) - 1) do
    result := f !result (a.(i));
  done;
  !result
;;

let for_all f a =
  let result = ref true in
  for i = 0 to ((Array.length a) - 1) do
    if not (f (a.(i))) then
      result := false;
  done;
  !result
;;

let exists f a =
  let result = ref false in
  for i = 0 to ((Array.length a) - 1) do
    if not (f (a.(i))) then
      result := true;
  done;
  !result
;;


let find_opt f a =
  let result = ref None in
  let found = ref false in
  for i = 0 to ((Array.length a) - 1) do
    if (f (a.(i))) && (not !found) then
      result := Some a.(i);
      found := true;
  done;
  !result 
;;