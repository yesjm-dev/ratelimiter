-- KEYS[1] : 윈도우 키
-- ARGV[1] : now (ms)
-- ARGV[2] : windowMillis
-- ARGV[3] : capacity

local key = KEYS[1]
local now = tonumber(ARGV[1])
local windowMillis = tonumber(ARGV[2])
local capacity = tonumber(ARGV[3])

local windowStart = now - windowMillis

-- key가 ZSET이 아닐 경우 초기화
if redis.call("TYPE", key).ok ~= "zset" then
    redis.call("DEL", key)
end

-- 오래된 데이터 삭제
redis.call("ZREMRANGEBYSCORE", key, 0, windowStart)

-- 현재 요청 수 확인
local count = tonumber(redis.call("ZCARD", key)) or 0

if count < capacity then
    redis.call("ZADD", key, now, now)
    redis.call("PEXPIRE", key, windowMillis * 2)
    return 1
else
    return 0
end