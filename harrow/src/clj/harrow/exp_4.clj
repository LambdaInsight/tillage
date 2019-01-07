(ns harrow.exp-4)

(defn pi-error
  ^Double [^Double pi-estimated]
  (let [pi-known 3.141592653589793238462643383280]
    (* (Math/abs (/ (- pi-estimated pi-known) pi-known)) 100)))

(defn rand-pow-2
  ^Double []
  (Math/pow (rand) 2))

(defn experiment 
  ^Long [] 
  (if 
    (<= (+ (rand-pow-2) (rand-pow-2)) 1) 
      1 
      0))

(defn pi-estimate 
  ^Double [^Long n] 
  (* 4 (double (/ (reduce + (take n (repeatedly experiment))) n))))
