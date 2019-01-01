defmodule Helix.Application do
  @moduledoc false

  use Application
  require Logger

  # I want to limit use to ipv4 ip addresses
  @type ipv4() :: {0..255, 0..255, 0..255, 0..255}

  def get_hostname(ipv4) do
    get_host_by_address = :inet_res.gethostbyaddr(ipv4)
    case get_host_by_address do
      {:ok, {:hostent, host_name, _, _, _, _,}} -> {:ok, host_name}
      {:error, error} -> {:error, error}
    end
  end

  def get_schedulers() do
    num_of_schedulers         = :erlang.system_info(:schedulers) 
    num_of_schedulers_online  = :erlang.system_info(:schedulers_online)
    {:ok, {num_of_schedulers, num_of_schedulers_online}}
  end

  def print_startup_messages() do
    Logger.info("Starting application...")
    plain_arguments = :init.get_plain_arguments() |> Enum.join(" ")
    Logger.info("Starting arguments: #{plain_arguments}")
    {:ok, {num_of_schedulers, num_of_schedulers_online}} = get_schedulers()
    Logger.info("number of schedulers: #{num_of_schedulers} :: number of schedulers online #{num_of_schedulers_online}")
  end

  def start(_type, _args) do

    log_level       = Application.get_env(:logger, :level)
    http_ip         = Application.get_env(:helix, :http_ip)
    http_port       = Application.get_env(:helix, :http_port)
    compress_body   = Application.get_env(:helix, :compress_body)
    num_acceptors   = Application.get_env(:helix, :num_acceptors)
    max_connections = Application.get_env(:helix, :max_connections)
    max_keepalive   = Application.get_env(:helix, :max_keepalive)
    children = [
      { Plug.Cowboy, 
        scheme: :http, 
        plug: Helix.Router, 
        options: [
          ip: http_ip, 
          port: http_port, 
          compress: compress_body,
          transport_options: [
            num_acceptors: num_acceptors,
            max_connections: max_connections,
          ],
          protocol_options: [
            max_keepalive: max_keepalive
          ],
        ]
      }
    ]

    opts = [strategy: :one_for_one, name: Helix.Supervisor]
    print_startup_messages()
    http_ip_string = http_ip |> Tuple.to_list |> Enum.join(".")
    Logger.info("http ip: #{http_ip_string} http port: #{http_port} compress body: #{compress_body}")
    get_host_name_ret = get_hostname(http_ip) 
    case get_host_name_ret do
      {:ok, host_name} -> Logger.info("http://#{host_name}:#{http_port}")
      {:error, error} -> Logger.error("#{inspect http_ip} cannot be resolved: #{error}")  
    end
    Logger.info("log level: #{log_level}")
    Supervisor.start_link(children, opts)

  end
end
