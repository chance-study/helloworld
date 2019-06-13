# !/usr/bin/env python
# -*- coding: utf-8 -*-
# 2019/03/16
# 淘宝秒杀脚本，扫码登录版
from selenium import webdriver
from selenium.webdriver.chrome.options import Options
import datetime
import time


def login():
    # 打开淘宝登录页，并进行扫码登录
    browser.get("https://www.taobao.com")
    time.sleep(3)
    if browser.find_element_by_link_text("亲，请登录"):
        browser.find_element_by_link_text("亲，请登录").click()
        print("请在15秒内完成扫码")
        time.sleep(20)
        browser.get("https://cart.taobao.com/cart.htm")
    time.sleep(3)

    now = datetime.datetime.now()
    print('login success:', now.strftime('%Y-%m-%d %H:%M:%S'))


def buy(times, choose):
    # 点击购物车里全选按钮
    if choose == 2:
        print("请手动勾选需要购买的商品")
    while True:
        now = datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S.%f')
        # 对比时间，时间到的话就点击结算
        print("还没有到抢购的时间,当前时间：%s" % now)
        if now >= times:
            if choose == 1:
                while True:
                    try:
                        if browser.find_element_by_id("J_SelectAll2"):
                            browser.find_element_by_id("J_SelectAll2").click()
                            break
                    except:
                        print("找不到购买按钮")
            # 点击结算按钮
            while True:
                try:
                    if browser.find_element_by_link_text("结 算"):
                        browser.find_element_by_link_text("结 算").click()
                        print("结算成功")
                        break
                except:
                    pass
            while True:
                try:
                    if browser.find_element_by_link_text('提交订单'):
                        browser.find_element_by_link_text('提交订单').click()
                        now1 = datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S.%f')
                        print("抢购成功时间：%s" % now1)
                        break
                except:
                    print("再次尝试提交订单")
            # time.sleep(0.01)
            time.sleep(60)


if __name__ == "__main__":
    # times = input("请输入抢购时间，格式如(2018-09-06 11:20:00.000000):")
    # 时间格式："2018-09-06 11:20:00.000000"

    # 实例化一个启动参数对象
    chrome_options = Options()
    # 添加启动参数
    chrome_options.add_argument('--window-size=1366,768')
    # 将参数对象传入Chrome，则启动了一个设置了窗口大小的Chrome
    chrome_options.add_argument('--incognito')
    chrome_options.add_argument('--disable-infobars')

    prefs = {
        'profile.default_content_setting_values' : {
            # 'images': 2,
            'notifications': 2
        }
    }
    chrome_options.add_experimental_option('prefs', prefs)

    # 指定chromedriver.exe的位置
    browser = webdriver.Chrome(chrome_options=chrome_options, executable_path='chromedriver.exe')
    # browser = webdriver.Chrome()
    # browser.maximize_window()
    login()
    choose = int(input("到时间自动勾选购物车请输入“1”，否则输入“2”："))
    # 2019-06-02 10:00:00.000000
    buy("2019-06-01 16:45:00.000000", choose)