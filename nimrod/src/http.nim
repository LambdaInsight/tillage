import asynchttpserver, asyncdispatch

var server = newAsyncHttpServer(reuseAddr = true, reusePort = false, maxBody = 8388608)

proc cb(req: Request) {.async.} =
  let urii = req.url
  case urii.path
  of "/echo":
    await req.respond(Http200, "Hello Echo!")
  of "/":
    await req.respond(Http200, "Hello /")
  else:
    await req.respond(Http404, "Not found! :(")

proc start_server* =
  waitFor server.serve(Port(8080), cb)

