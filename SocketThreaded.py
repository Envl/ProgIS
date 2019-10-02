import time
import socket

from threading import Thread


class SocketThreaded:

    def __init__(self, addr='localhost', port=8001):
        self._addr = addr
        self._port = port
        self._msgqueue = []
        self._finished = False
        self._isRunning = False

    def start(self):
        try:
            print('before create socket')
            self._sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
            print("create socket succ!")
            self._sock.bind(('localhost', 8001))
            print('bind socket succ!')
            self._sock.listen(5)
            print('listen succ!')
        except:
            print("init socket error!")
        Thread(target=self.run).start()
        return self

    def run(self):
        while True:
            if self._finished:
                return
            # todo  sock. send
            print("listen for client...")
            conn, addr = self._sock.accept()
            print("get client")
            print(addr)

            conn.settimeout(30)
            szBuf = conn.recv(1024)
            self._msgqueue.append(str(szBuf, 'utf-8'))
            print("recv:" + str(szBuf, 'utf-8'))
            if "0" == szBuf:
                conn.send(b"exit")
            else:
                conn.send(b"welcome client")
            conn.close()
            print("end of servive")

    def stop(self):
        self._finished = True

    def readMsg(self):
        if self._msgqueue:
            return self._msgqueue.pop()
        return False

    def sendMsg(self, msg):
        pass

if __name__ == '__main__':
    print("start---")
    skt = SocketThreaded()
    skt.start()

    while True:
        time.sleep(1)
        msg = skt.readMsg()
        if(msg):
            print(msg)
        print('tick')
