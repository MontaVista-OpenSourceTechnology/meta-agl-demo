-- V4L2 monitor config file; with selecting by default USB camera --

v4l2_monitor = {}
v4l2_monitor.properties = {}

v4l2_monitor.rules = {
  -- An array of matches/actions to evaluate.
  {
    -- Rules for matching a device or node. It is an array of
    -- properties that all need to match the regexp. If any of the
    -- matches work, the actions are executed for the object.
    matches = {
      {
        -- This matches all cards.
        { "device.name", "matches", "v4l2_device.*" },
      },
    },
    -- Apply properties on the matched object.
    apply_properties = {
      -- ["device.nick"] = "My Device",
    },
  },
  {
    matches = {
      {
        -- Matches all sources.
        { "node.name", "matches", "v4l2_input.*" },
      },
      {
        -- Matches all sinks.
        { "node.name", "matches", "v4l2_output.*" },
      },
    },
    apply_properties = {
      --["node.nick"] = "My Node",
      --["priority.driver"] = 100,
      --["priority.session"] = 100,
      --["node.pause-on-idle"] = false,
    },
  },
  {
  matches = {
    {
      { "node.name", "matches", "v4l2_input*usb*" },
    },
  },
  apply_properties = {
    ["priority.driver"]        = 1300,
    ["priority.session"]       = 1300,
  },
  },
}

function v4l2_monitor.enable()
  load_monitor("v4l2", {
    properties = v4l2_monitor.properties,
    rules = v4l2_monitor.rules,
  })
end
