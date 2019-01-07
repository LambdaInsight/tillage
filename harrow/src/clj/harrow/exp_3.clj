(ns harrow.exp-3)

(defn pi-error
  [pi-estimated]
  (let [pi-known 3.141592653589793238462643383280]
    (* (Math/abs (/ (- pi-estimated pi-known) pi-known)) 100)))

(defn experiment 
  [] 
  (if (<= (+ (Math/pow (rand) 2) (Math/pow (rand) 2)) 1) 1 0))

(defn pi-estimate 
  [n] 
  (* 4 (float (/ (reduce + (take n (repeatedly experiment))) n))))  
