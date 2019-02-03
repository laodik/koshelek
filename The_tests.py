import unittest
import json
import os
import requests
import time
from multiprocessing import Process, Value

with open ('api-key.txt','r') as File:
	apikey = File.readline()
	
class ChildMetods:
	def dataload(self,step_time_value,finish_time_value,start_time,apikey,ind):
		step_start_time=time.time()
		if ind==0:
			start_time.value=step_start_time
		ApiRequest='https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?CMC_PRO_API_KEY='+apikey+'&limit=10&sort=volume_24h&convert=USD'
		Recent=requests.get(ApiRequest)
		step_time_value.value=float(time.time()-step_start_time)
		finish_time_value.value=float (time.time())	
			
class TestUM(unittest.TestCase):

	def test_1(self):
		start_time = time.time()
		ApiRequest='https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?CMC_PRO_API_KEY='+apikey+'&limit=10&sort=volume_24h&convert=USD'
		Recent=requests.get(ApiRequest)
		sec=float (time.time() - start_time)
		data = Recent.text
		with open ('result.txt','w') as File:
			File.write(data)
		size = os.path.getsize('result.txt')
		sizeKB=int(size) / 1024
		print ("Test №1")
		print("Время ответа сервера %.3f секунд" % sec)
		print("Размер ответа (в файле) %.2f КБ" % sizeKB)
		if (round(sizeKB,2)<=10) & ((sec*1000)<=500):
			test1_result=True
		else:
			test1_result=False
		self.assertTrue(test1_result)
		
	def test_2(self):
		print ("\nTest №2")
		ch = ChildMetods()
		step_time = []
		finish_time = []
		for number in range(8):
			step_time.append(Value('f',0.0))
			finish_time.append(Value('d',0.0))
		start_time_value=Value('d',0.0)
		procs = []
		seconds_counter = time.time()
		for number in range(8):
			proc = Process(target=ch.dataload,args=(step_time[number],finish_time[number],start_time_value,apikey,number))
			procs.append(proc)
			proc.start()
		for proc in procs:
			proc.join()
		rps=0
		latency_requests_index=1
		for ft in finish_time:
			if (float(ft.value)-float(start_time_value.value))<=1:
				rps=rps+1
		st_time = []
		for st in step_time:
			st_time.append(str(float(st.value)*1000))
		sorted_st = sorted(st_time, reverse = True)
		process_time = float (time.time()-start_time_value.value)
		print("Время выполнения теста %.3f секунд" % process_time)
		print("80-перцентиль времеми ответа сервера %.7s мс" % sorted_st[latency_requests_index])
		print("Значение rps: %i" % rps)
		if (rps>4) & (float(sorted_st[latency_requests_index])<450):
			test2_result=True
		else:
			test2_result=False
		self.assertTrue(test2_result)
		

if __name__ == '__main__':
	unittest.main()
