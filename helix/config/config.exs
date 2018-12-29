use Mix.Config

# use in code: Application.get_env(:helix, :http_port)
config :helix, http_ip: {127, 0, 0, 1}
config :helix, http_port: 8080
config :helix, compress_body: true
config :logger, 
  level: :info,
  compile_time_purge_matching: [
    [level_lower_than: :info]
  ]

# It is also possible to import configuration files, relative to this
# directory. For example, you can emulate configuration per environment
# by uncommenting the line below and defining dev.exs, test.exs and such.
# Configuration from the imported file will override the ones defined
# here (which is why it is important to import them last).
#
#     import_config "#{Mix.env()}.exs"
