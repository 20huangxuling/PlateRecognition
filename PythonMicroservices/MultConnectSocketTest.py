import selectors
import socket
import yolo as y
from concurrent.futures import ThreadPoolExecutor


# def accept(sock):
#     conn, addr = sock.accept()  # Should be ready
#     # print('accepted', conn, 'from', addr)
#     conn.setblocking(False)
#     sel.register(conn, selectors.EVENT_READ, read)


def read(conn):
    with yol.graph.as_default():
        while True:
            path_ImageToBeDetected = conn.recv(1024)  # Should be ready
            if path_ImageToBeDetected:
                path_ImageToBeDetected = path_ImageToBeDetected.decode()
                do_task(path_ImageToBeDetected, conn)
            else:
                conn.close()
        # else:
        #     print('closing', conn)
        #     sel.unregister(conn)
        #     conn.close()


def do_task(path_ImageToBeDetected, conn):
    print('before detected-- path_ImageToBeDetected: ', path_ImageToBeDetected)
    path_ImageToBeDetected = path_ImageToBeDetected.strip()
    print('after detected-- path_ImageToBeDetected: ', path_ImageToBeDetected)
    path_ImageSaved = y.detect_img(yol, path_ImageToBeDetected)
    print('开始返回')
    ret = conn.send(path_ImageSaved.encode())
    print(ret)


yol = y.YOLO()
# sel = selectors.DefaultSelector()
executor = ThreadPoolExecutor(max_workers=6)
sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
sock.bind(('localhost', 7000))
sock.listen()
# sock.setblocking(False)
# sel.register(sock, selectors.EVENT_READ, accept)

# while True:
#     events = sel.select(timeout=None)
#     print(len(events))
#     for key, mask in events:
#         callback = key.data
#         callback(key.fileobj)


while True:
    print('server socket begin to listening:')
    conn, addr = sock.accept()
    print(conn)
    executor.submit(read, conn)

