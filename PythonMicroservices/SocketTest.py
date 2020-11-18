import socket
import yolo as y
import selectors

yol = y.YOLO()
HOST = '127.0.0.1'
PORT = 7000

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
    s.bind((HOST, PORT))
    while True:
        s.listen()
        print('Listening Port: 7000 ....')
        conn, addr = s.accept()
        with conn:
            print('Connected by', addr)
            while True:
                path_ImageToBeDetected = conn.recv(1024)
                if not path_ImageToBeDetected:
                    break
                path_ImageToBeDetected = path_ImageToBeDetected.decode()
                path_ImageToBeDetected = path_ImageToBeDetected.replace("\r", '')
                path_ImageToBeDetected = path_ImageToBeDetected.replace("\n", '')
                print('main path_ImageToBeDetected: ', path_ImageToBeDetected)
                y.detect_img(yol, path_ImageToBeDetected)
                name_ImageToBeDetected = path_ImageToBeDetected.split('/')[-1]
                path_ImageDected = r'J:/test_database/okPic/' + name_ImageToBeDetected
                print(path_ImageDected)
                conn.sendall(path_ImageDected.encode())
                break
