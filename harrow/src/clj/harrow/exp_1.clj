(ns harrow.exp-1)

(defn pair 
  [] 
  [(rand) (rand)])

(defn pairs 
  [n] 
  (let [pairs (take n (repeatedly pair))] 
    pairs))
 
(defn within 
  [pair] 
  (let [fst (first pair) 
        snd (second pair) 
         r (+ (* fst fst) (* snd snd)) ] 
    (if (<= r 1.0) 
      :in 
      :out)))
 
(defn pi 
  [size]
  (double (* 4 (/ (count (filter #(= :in %) (map within (pairs size)))) size))))
