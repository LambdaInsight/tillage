defmodule Helix.Router do
  use Plug.Router

  plug(:match)
  plug(:dispatch)

  get "/" do
    conn
    |> put_resp_content_type("text/html")
    |> send_resp(200, "<h2>/</h2>")
  end

  get "/bubu" do 
    conn
    |> put_resp_content_type("text/html")
    |> send_resp(200, "<h2>Bubu</h2>")
  end

  get "/hello/:name" do 
    conn
    |> put_resp_content_type("text/html")
    |> send_resp(200, "<h2>hello #{name}</h2>")
  end

  forward "/echo", to: Helix.EchoPlug

  match(_, do: send_resp(conn, 404, "Oops!"))
end
