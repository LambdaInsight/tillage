import jester, posix
import runForever from asyncdispatch

onSignal(SIGABRT):
  echo "<2>Received SIGABRT"
  quit(1)

routes:
  get "/":
    resp "OK"

export runForever
