defmodule Helix.MixProject do
  
  use Mix.Project

  def project do
    [
      app: :helix,
      version: "0.1.0",
      elixir: "~> 1.7",
      start_permanent: Mix.env() == :prod,
      deps: deps()
    ]
  end

  def application do
    [
      extra_applications: [:logger, :guardian],
      mod: {Helix.Application, []}
    ]
  end

  defp deps do
    [
      {:plug_cowboy,  "~> 2.0.1"},
      {:guardian,     "~> 1.0"  },
      {:distillery,   "~> 2.0"  },
    ]
  end
end
