# 创建服务器用到的模块
import socketserver
import traceback
import numpy as np
import yolo as y
from PIL import Image
import sys
import json
import platform

yol = y.YOLO()

okPic_path_linux = r'/usr/local/okPic/'
okPic_path_windows = r'J:\test_database\okPic\\'
okPic_path = ''
if platform.system()=='Windows':
    okPic_path = okPic_path_windows
elif platform.system()=='Linux':
    okPic_path = okPic_path_linux


class MySelfServer(socketserver.StreamRequestHandler):

    def handle(self):
        global path_ImageToBeSave, path_ImageToBeDetected
        with yol.graph.as_default():
            print('准备接收数据')
            label = []
            # 接收数据
            data = self.request.recv(1024)  # Should be ready
            if data:
                path_ImageToBeDetected = data.decode()
            path_ImageToBeDetected = path_ImageToBeDetected.strip()
            name_ImageToBeDetected = (path_ImageToBeDetected.split('/')[-1]).replace("\n", '')
            if '\\' in name_ImageToBeDetected:
                name_ImageToBeDetected = (path_ImageToBeDetected.split('\\')[-1])
            print('YOLO image name: ', name_ImageToBeDetected)
            print('YOLO path_ImageToBeDetected:', path_ImageToBeDetected)
            try:
                image = Image.open(path_ImageToBeDetected, 'r')
            except:
                exType, exValue, exTrace = sys.exc_info()
                print(exValue)
                print('Open Error! ')
                for trace in traceback.extract_tb(exTrace):
                    print(trace)
            else:
                r_image, label = yol.detect_image(image)
                path_ImageToBeSave = okPic_path + name_ImageToBeDetected
                r_image.save(path_ImageToBeSave)
            print("{} wrote:".format(self.client_address[0]))
            if not data:
                print(self.client_address, '的链接断开了！')
            str = {'path_Image_Detected': r"http://182.92.226.1/okPic/" + name_ImageToBeDetected, 'label': label}
            ret = json.dumps(str)
            self.request.sendall(ret.encode())


# class MyThreadingTCPServer(socketserver.ThreadingUnixStreamServer):
#     request_queue_size = 100


if __name__ == "__main__":
    # try:
    #     image = Image.open('G:/bring.jpg', 'r')
    #     yol.detect_image(image)
    # except:
    #     exType, exValue, exTrace = sys.exc_info()
    #     print(exValue)
    #     print('Open Error! ')
    #     for trace in traceback.extract_tb(exTrace):
    #         print(trace)
    HOST, PORT = "localhost", 7000
    server = socketserver.ThreadingTCPServer((HOST, PORT), MySelfServer)
    #server = socketserver.TCPServer((HOST, PORT), MySelfServer)
    server.request_queue_size = 100
    print('服务器打开: ')
    server.serve_forever()
    print('服务器打开: ')
