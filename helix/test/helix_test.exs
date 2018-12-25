defmodule HelixTest do
  use ExUnit.Case
  doctest Helix

  test "greets the world" do
    assert Helix.hello() == :world
  end
end
