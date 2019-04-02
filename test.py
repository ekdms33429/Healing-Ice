#-*-coding: utf-8-*- 
# 한글 주석 이용
import RPi.GPIO as pi
import time



DAT =5 #DOUT
CLK=12
DAT2=6 #DOUT2
CLK2=22

num=0
num2=0
wei_avg=0 # 우유통 무게(%)



pi.setmode(pi.BCM)
pi.setwarnings (False)
pi.setup(CLK,pi.OUT)
pi.setup(CLK2,pi.OUT)

def weight(): #우유통의 무게를 측정해서 %로 출력
    global wei_avg
    i=0
    j=0
    num=0
    num2=0
    
    pi.setup(DAT, pi.OUT)
    pi.setup(DAT2, pi.OUT)
    pi.output(DAT,1)
    pi.output(DAT2,1)
    pi.output(CLK,0)
    pi.output(CLK2,0) 
    pi.setup(DAT, pi.IN)
    pi.setup(DAT2, pi.IN)
    
    while pi.input(DAT) == 1:
        i=0
        
    while pi.input(DAT2) == 1:
        j=0
        
    for i in range(24):
        pi.output(CLK,1)
        num=num<<1
        pi.output(CLK,0)
        
        if pi.input(DAT)== 0:
            num=num+1
            
    for j in range(24):
        pi.output(CLK2,1)
        num2=num2<<1
        pi.output(CLK2,0)
        
        if pi.input(DAT2)== 0:
            num2=num2+1
            
    pi.output(CLK,1)
    pi.output(CLK2,1)
    
    num=num^0x800000
    num2=num2^0x800000
    
    pi.output(CLK,0)
    pi.output(CLK2,0)
    
    wei=0
    wei2=0
    wei=((num)/(1406))
    wei2=((num2)/(1406))
    wei_avg=(5900-((wei+wei2)/2))/3.3 # wei_avg -> 우유통에 들어있는 유유의 양 (%)
    
    if(wei_avg>=95):
      wei_avg=100
    elif(wei_avg<=5):
      wei_avg=0

    print"wei_avg:",(int)(wei_avg),"%"
    
while 1:
    weight()
    time.sleep(1)

