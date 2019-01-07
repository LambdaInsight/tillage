(ns harrow.exp-2)

(def pi-seq 
  (pmap first 
    (iterate 
      (fn [[pi within total]] 
        [ (double (* (/ within total) 4)) 
          (if (<= (+ (Math/pow (rand) 2) (Math/pow (rand) 2)) 1) (inc within) within)
          (inc total) ] ) 
      [0.0 0N 1N])))

