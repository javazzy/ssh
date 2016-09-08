@echo off
cd /D %~dp0/files/redis/windows/Redis-3.2.100
start redis-server.exe
exit