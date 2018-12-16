version       = "0.1.0"
author        = "Istvan Szukacs"
description   = "www"
license       = "MIT"

skipFiles = @["todo.markdown"]
skipDirs = @["tests"]


srcDir = "src"
bin           = @["nimrod"]

requires "jester >= 0.4.1"

