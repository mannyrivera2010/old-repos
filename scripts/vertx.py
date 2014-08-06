#Python3
import sys
import os
import subprocess
import signal
import psutil

VERTX_PATH = 'C:\\Programs\\vert.x-2.1.2\\bin'
VERTX_EXECUTABLE = VERTX_PATH + '\\vertx.bat'  
#run ${project_loc} ${resource_path}
print('Number of arguments:', len(sys.argv), 'arguments.')
print('Argument List:', str(sys.argv))
print("*"*10)
project_loc = sys.argv[2]
print('Project Loc='+sys.argv[2])
print('File='+sys.argv[3])
fileIn = "\\".join(sys.argv[3].split('\\')[2:])
print('FileIN='+ fileIn)

os.chdir(project_loc)
print(VERTX_EXECUTABLE)
proc1 = subprocess.Popen([VERTX_EXECUTABLE,sys.argv[1], fileIn])
proc1pid = proc1.pid
print('Parent PID' + str(proc1pid))
try:
	input('Press Enter to Kill\n---------------------\n')
except Exception as E:
	print(E)

p = psutil.Process(proc1pid)
child_pid = p.children(recursive=True)
print(child_pid)
for pid in child_pid:
    os.kill(pid.pid, signal.SIGTERM)
	
print('Tried to Kill with SIGINT Signal')