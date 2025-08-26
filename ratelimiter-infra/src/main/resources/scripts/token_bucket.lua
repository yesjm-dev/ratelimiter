-- KEYS[1]: 버킷 키
-- ARGV[1]: now (ms)
-- ARGV[2]: refillInterval (ms)
-- ARGV[3]: refillAmount
-- ARGV[4]: capacity

local key = KEYS[1]
local now = tonumber(ARGV[1])
local refillInterval = tonumber(ARGV[2])
local refillAmount = tonumber(ARGV[3])
local capacity = tonumber(ARGV[4])

local bucket = redis.call("HMGET", key, "tokens", "lastRefill")
local tokens = tonumber(bucket[1])
if tokens == nil then tokens = capacity end

local lastRefill = tonumber(bucket[2]) or now
if lastRefill == nil then lastRefill = now end

local elapsed = now - lastRefill
local refillCount = math.floor(elapsed / refillInterval)
if refillCount > 0 then
  tokens = math.min(capacity, tokens + refillCount * refillAmount)
  lastRefill = now
end

if tokens > 0 then
  tokens = tokens - 1
  redis.call("HMSET", key, "tokens", tokens, "lastRefill", lastRefill)
  redis.call("PEXPIRE", key, refillInterval * 2)
  return 1
else
  redis.call("HMSET", key, "tokens", tokens, "lastRefill", lastRefill)
  redis.call("PEXPIRE", key, refillInterval * 2)
  return 0
end