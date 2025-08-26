-- KEYS[1]: 윈도우 키
-- ARGV[1]: capacity
-- ARGV[2]: windowMillis

local key = KEYS[1]
local capacity = tonumber(ARGV[1])
local windowMillis = tonumber(ARGV[2])

local count = tonumber(redis.call("GET", key) or "0")

if count < capacity then
  count = count + 1
  redis.call("SET", key, count, "PX", windowMillis)
  return 1
else
  return 0
end