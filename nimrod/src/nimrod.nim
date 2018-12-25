# local import
import http, exec
# global import
import os, logging

# Logging
var L = newConsoleLogger(fmtStr = "$datetime :: ")
addHandler(L)

# Exec
let runResult = run()
info("Run result : ", runResult)

info("Starting web server...")
start_server()
