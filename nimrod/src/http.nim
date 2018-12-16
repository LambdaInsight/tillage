import jester, posix

onSignal(SIGABRT):
  echo "<2>Received SIGABRT"
  quit(1)

settings:
    port = Port(3003)

routes:
  get "/":
    resp "OK"

proc start():
  var j = initJester(match)
  j.serve()

export start()
