import yolo as y
import tensorflow as tf
import os
import sys
from PIL import Image, ImageFont, ImageDraw

def initSession():
    os.environ["CUDA_VISIBLE_DEVICES"] = '0'  # 指定第一块GPU可用
    config = tf.ConfigProto()
    config.gpu_options.per_process_gpu_memory_fraction = 0.9  # 程序最多只能占用指定gpu50%的显存
    config.gpu_options.allow_growth = True  # 程序按需申请内存
    sess = tf.Session(config=config)


def detect_imagee(path):
    print('Begin:  detect_imagee(path)')
    yol = y.YOLO()
    #img = input('Input image filename:')
    try:
        print('open image')
        image = Image.open(path)
        print('opened image')
    except:
        print('Open Error! ')
    else:
        print('begin yol.detect_image(image)')
        r_image = yol.detect_image(image)
        r_image.show()
    yol.close_session()

detect_imagee(sys.argv[1])

if __name__ == '__main__':
    detect_imagee()