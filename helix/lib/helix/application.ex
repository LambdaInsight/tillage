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

  def start(_type, _args) do

    log_level = Application.get_env(:logger, :level)
    http_ip   = Application.get_env(:helix, :http_ip)
    http_port = Application.get_env(:helix, :http_port)
    compress_body = Application.get_env(:helix, :compress_body)

    children = [
      { Plug.Cowboy, 
        scheme: :http, 
        plug: Helix.Router, 
        options: [
          ip: http_ip, 
          port: http_port, 
          compress: compress_body,
        ]
      }
    ]

    opts = [strategy: :one_for_one, name: Helix.Supervisor]

    Logger.info("Starting application...")

    plain_arguments = :init.get_plain_arguments() |> Enum.join(" ")
    Logger.info("Starting arguments: #{plain_arguments}")
    
    #Erlang/OTP 21 [erts-10.2] [source] [64-bit] [smp:6:6] [ds:6:6:10] [async-threads:1] [hipe] [dtrace]

    {:ok, {num_of_schedulers, num_of_schedulers_online}} = get_schedulers()
    Logger.info("number of schedulers: #{num_of_schedulers} :: number of schedulers online #{num_of_schedulers_online}")

    Logger.info("log level: #{log_level}")

    http_ip_string = http_ip |> Tuple.to_list |> Enum.join(".")
    Logger.info("http ip: #{http_ip_string} http port: #{http_port} compress body: #{compress_body}")

    get_host_name_ret = get_hostname(http_ip) 
    case get_host_name_ret do
      {:ok, host_name} -> Logger.info("http://#{host_name}:#{http_port}")
      {:error, error} -> Logger.error("#{http_ip} cannot be resolved: #{error}")  
    end

    Supervisor.start_link(children, opts)

  end
end
