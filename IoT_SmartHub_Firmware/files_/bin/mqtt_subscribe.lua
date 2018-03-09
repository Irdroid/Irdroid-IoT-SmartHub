#!/usr/bin/lua
-- ------------------------------------------------------------------------- --
-- mqtt_subscribe.lua
-- ~~~~~~~~~~~~~~~~~~
-- Please do not remove the following notices.
-- Copyright (c) 2011-2012 by Geekscape Pty. Ltd.
-- Documentation: http://http://geekscape.github.com/mqtt_lua
-- License: AGPLv3 http://geekscape.org/static/aiko_license.html
-- Version: 0.2 2012-06-01
--
-- Description
-- ~~~~~~~~~~~
-- Subscribe to an MQTT topic and display any received messages.
--
-- References
-- ~~~~~~~~~~
-- Lapp Framework: Lua command line parsing
--   http://lua-users.org/wiki/LappFramework
--
-- ToDo
-- ~~~~
-- None, yet.
-- ------------------------------------------------------------------------- --

function callback(
  topic,    -- string
  message)  -- string

  print("Topic: " .. topic .. ", message: '" .. message .. "'")
if string.len(topic) == 16
then
switch_chan = string.sub( topic,-6, -2)
status_topic = string.sub( topic, 1, 8 ) 
relay_no = string.sub( topic, -1 )
relay_no = tonumber( relay_no )
  print (relay_no)
  print (switch_chan)
  os.execute("send " .. switch_chan .. " " .. relay_no .. " " .. message .. " 265 9 15 ")
  os.execute("/bin/publish.lua -H hwgroup-bg.com -t " ..status_topic.. " -m ok ")
end
if string.len(topic) == 15
then
switch_chan = string.sub( topic,-5,  -2)
switch_chan = tonumber ( switch_chan )
status_topic = string.sub( topic, 1, 8 )
relay_no = string.sub( topic, -1 )
relay_no = tonumber( relay_no )
message = tonumber ( message )
print (relay_no)                                                             
print (switch_chan)
if switch_chan == 1000
then
  if relay_no == 1
   then
-- print (relay_no)
   
   if message == 1
    then
    os.execute("rf-one -p somfy -r 0xC0 -d 0x052420 -c on" )
    os.execute("/bin/publish.lua -H hwgroup-bg.com -t " ..status_topic.. " -m ok ")
   elseif message == 0
    then
   os.execute("rf-one -p somfy -r 0xC0 -d 0x052420 -c off" )
    os.execute("/bin/publish.lua -H hwgroup-bg.com -t " ..status_topic.. " -m ok ")
    end

  elseif relay_no == 2
   then
-- print (relay_no)

    if message == 1                          
    then                                    
    os.execute("rf-one -p somfy -r 0xA1 -d 0x022420 -c on" )
    os.execute("/bin/publish.lua -H hwgroup-bg.com -t " ..status_topic.. " -m ok ")
    elseif message == 0                      
    then     
   os.execute("rf-one -p somfy -r 0xA1 -d 0x022420 -c off" )
 os.execute("/bin/publish.lua -H hwgroup-bg.com -t " ..status_topic.. " -m ok ")                   
    end           

  elseif relay_no == 3
   then
-- print (relay_no)

    if message == 1                          
    then                                    
    os.execute("rf-one -p somfy -r 0xA2 -d 0x032420 -c on" )
     os.execute("/bin/publish.lua -H hwgroup-bg.com -t " ..status_topic.. " -m ok ")
   elseif message == 0                      
    then     
   os.execute("rf-one -p somfy -r 0xA2 -d 0x032420 -c off" )                   
    os.execute("/bin/publish.lua -H hwgroup-bg.com -t " ..status_topic.. " -m ok ") 
   end              

  elseif relay_no == 4
   then
-- print (relay_no)

   if message == 1                          
    then                                    
    os.execute("rf-one -p somfy -r 0xA3 -d 0x032421 -c on" )
     os.execute("/bin/publish.lua -H hwgroup-bg.com -t " ..status_topic.. " -m ok ")
   elseif message == 0                      
    then     
   os.execute("rf-one -p somfy -r 0xA3 -d 0x032421 -c off" )                   
    os.execute("/bin/publish.lua -H hwgroup-bg.com -t " ..status_topic.. " -m ok ") 
   end           

  elseif relay_no == 5
   then
-- print (relay_no)

   if message == 1                          
    then                                    
    os.execute("rf-one -p somfy -r 0xA4 -d 0x032422 -c on" )
     os.execute("/bin/publish.lua -H hwgroup-bg.com -t " ..status_topic.. " -m ok ")
   elseif message == 0                      
    then     
   os.execute("rf-one -p somfy -r 0xA4 -d 0x032422 -c off" )                   
    os.execute("/bin/publish.lua -H hwgroup-bg.com -t " ..status_topic.. " -m ok ")
    end
 
end

elseif switch_chan == 1001
then

   if relay_no == 1
   then
-- print (relay_no)
   
   if message == 1
    then
    os.execute("rf-one -p somfy -r 0xA5 -d 0x032423 -c on" )
     os.execute("/bin/publish.lua -H hwgroup-bg.com -t " ..status_topic.. " -m ok ") 
  elseif message == 0
    then
   os.execute("rf-one -p somfy -r 0xA5 -d 0x032423 -c off" )
    os.execute("/bin/publish.lua -H hwgroup-bg.com -t " ..status_topic.. " -m ok ")   
 end

  elseif relay_no == 2
   then
-- print (relay_no)

    if message == 1                          
    then                                    
    os.execute("rf-one -p somfy -r 0xA6 -d 0x032424 -c on" )
    os.execute("/bin/publish.lua -H hwgroup-bg.com -t " ..status_topic.. " -m ok ")
   elseif message == 0                      
    then     
   os.execute("rf-one -p somfy -r 0xA6 -d 0x032424 -c off" )                   
    os.execute("/bin/publish.lua -H hwgroup-bg.com -t " ..status_topic.. " -m ok ")
    end           

  elseif relay_no == 3
   then
-- print (relay_no)

    if message == 1                          
    then                                    
    os.execute("rf-one -p somfy -r 0xA7 -d 0x032425 -c on" )
     os.execute("/bin/publish.lua -H hwgroup-bg.com -t " ..status_topic.. " -m ok ")
   elseif message == 0                      
    then     
   os.execute("rf-one -p somfy -r 0xA7 -d 0x032425 -c off" )                   
    os.execute("/bin/publish.lua -H hwgroup-bg.com -t " ..status_topic.. " -m ok ")
    end              

  elseif relay_no == 4
   then
-- print (relay_no)

   if message == 1                          
    then                                    
    os.execute("rf-one -p somfy -r 0xAF -d 0x032426 -c on" )
     os.execute("/bin/publish.lua -H hwgroup-bg.com -t " ..status_topic.. " -m ok ")
   elseif message == 0                      
    then     
   os.execute("rf-one -p somfy -r 0xAF -d 0x032426 -c off" )                   
    os.execute("/bin/publish.lua -H hwgroup-bg.com -t " ..status_topic.. " -m ok ")   
 end           

  elseif relay_no == 5
   then
-- print (relay_no)

   if message == 1                          
    then                                    
    os.execute("rf-one -p somfy -r 0xB0 -d 0x032427 -c on" )
    os.execute("/bin/publish.lua -H hwgroup-bg.com -t " ..status_topic.. " -m ok ")
   elseif message == 0                      
    then     
   os.execute("rf-one -p somfy -r 0xB0 -d 0x032427 -c off" )                   
    os.execute("/bin/publish.lua -H hwgroup-bg.com -t " ..status_topic.. " -m ok ") 
   end                                    
end
-- os.execute("send " .. switch_chan .. " " .. relay_no .. " " .. message .. " 265 9 15 ")
--  os.execute("/bin/publish.lua -H hwgroup-bg.com -t " ..status_topic.. " -m ok ")
end
end


 





if string.len(topic) < 14
then
switch_chan = string.sub( topic,-2, -2)
status_topic = string.sub( topic, 1, 8 )
relay_no = string.sub( topic, -1 )
switch_chan =  tonumber( switch_chan )
  relay_no = tonumber( relay_no )
  print (relay_no)
  print (switch_chan)
  os.execute("send1 " .. switch_chan .. " " .. relay_no .. " " .. message .. " 260 9 15 " )  
  os.execute("/bin/publish.lua -H hwgroup-bg.com -t " ..status_topic.. " -m ok ")

end
end

-- ------------------------------------------------------------------------- --

function is_openwrt()
  return(os.getenv("USER") == "root")  -- Assume logged in as "root" on OpenWRT
end

-- ------------------------------------------------------------------------- --

print("[mqtt_subscribe v0.2 2012-06-01]")


local lapp = require("pl.lapp")

local args = lapp [[
  Subscribe to a specified MQTT topic
  -d,--debug                                Verbose console logging
  -H,--host          (default localhost)    MQTT server hostname
  -i,--id            (default mqtt_sub)     MQTT client identifier
  -k,--keepalive     (default 60)           Send MQTT PING period (seconds)
  -p,--port          (default 1883)         MQTT server port number
  -t,--topic         (string)               Subscription topic
  -w,--will_message  (default .)            Last will and testament message
  -w,--will_qos      (default 0)            Last will and testament QOS
  -w,--will_retain   (default 0)            Last will and testament retention
  -w,--will_topic    (default .)            Last will and testament topic
]]

local MQTT = require("mqtt_library")

if (args.debug) then MQTT.Utility.set_debug(true) end

if (args.keepalive) then MQTT.client.KEEP_ALIVE_TIME = args.keepalive end



local mqtt_client = MQTT.client.create( '85.187.99.35', 1883, callback)

if (args.will_message == "."  or  args.will_topic == ".") then
  mqtt_client:connect(args.id)
else
  mqtt_client:connect(
    args.id, args.will_topic, args.will_qos, args.will_retain, args.will_message
  )
end

mqtt_client:subscribe({args.topic})

local error_message = nil

while (error_message == nil) do
  error_message = mqtt_client:handler()
  socket.sleep(1.0)  -- seconds
end

if (error_message == nil) then
  mqtt_client:unsubscribe({args.topic})
  mqtt_client:destroy()
else
  print(error_message)
end

