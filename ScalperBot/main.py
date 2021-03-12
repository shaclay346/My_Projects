import time
from selenium import webdriver

# used an api called selenium to access a web browser
from selenium.common.exceptions import NoSuchElementException
from selenium.common.exceptions import TimeoutException
# from config import keys

# using this to access an api from Amazon
import boto3

# save my firefox profile so once the browser is laucnhed it will have me logged in and let me use autofill to make checking out fast
profile = webdriver.FirefoxProfile(
    r'C:\Users\Shanners\AppData\Roaming\Mozilla\Firefox\Profiles\n3ktxmv8.default-release')
driver = webdriver.Firefox(profile)

# go to the link for the RTX 3070
driver.get(
    "https://www.bestbuy.com/site/xfx-amd-radeon-rx-5700-xt-raw-ii-8gb-gddr6-pci-express-4-0-graphics-card-with-zero-db-black/6375963.p?skuId=6375963")


# https://www.bestbuy.com/site/evga-geforce-rtx-3070-xc3-black-gaming-8gb-gddr6x-pci-express-4-0-graphics-card/6439300.p?skuId=6439300

foundButton = False

cvv = "___"

try:  # while the item is not available to add to cart I contiuosly loop
    while not foundButton:
        addToCartButton = driver.find_element_by_class_name("add-to-cart-button")
        # if an item is not in stock in the code of the website page
        if ("btn-disabled" in addToCartButton.get_attribute("class")):
            # refresh the page every second to recheck
            time.sleep(1)
            driver.refresh()
        # once the item is in stock go through the checkout process
        else:
            foundButton = True
            # click the add to cart button
            addToCartButton.click()
            time.sleep(2)

            # find the go to cart button had to use class name since different products all had different options for going to cart. this works for all of them
            goToCart = driver.find_element_by_class_name("go-to-cart-button")
            # again click and sleep
            goToCart.click()
            time.sleep(2)

            # selects the the option of shipping to your address
            ship = driver.find_element_by_xpath(
                "/html/body/div[1]/main/div/div[2]/div[1]/div/div/span/div/div[2]/div[1]/section[1]/div[4]/ul/li/section/div[2]/div[2]/form/div[2]/fieldset/div[2]/div[1]/div/div")
            ship.click()
            time.sleep(0.5)

            # find the go to checkout button and click it
            toCheckout = driver.find_element_by_xpath(
                "/html/body/div[1]/main/div/div[2]/div[1]/div/div/span/div/div[2]/div[1]/section[2]/div/div/div[3]/div/div[1]/button")
            # here is where the firefox profile is needed it, it will automatically sign you in after clicking this and fill your addess from your account
            toCheckout.click()
            time.sleep(5)

            # enter the cvv for the card I have save into the firefox browser
            enterCVV = driver.find_element_by_id("credit-card-cvv")
            enterCVV.send_keys(cvv)

            # and lastly hit the place the order button
            placeOrder = driver.find_element_by_xpath(
                "/html/body/div[1]/div[2]/div/div[2]/div[1]/div[1]/main/div[2]/div[2]/div/div[4]/div[3]/div/button")
            placeOrder.click()
# catch if the page timesout or if the webdriver can't find the element we are looking for
except TimeoutException or NoSuchElementException as exception:
    pass


# using an amazon web service account I am using an API to send myself a text message once the item has been purchased
if foundButton:
    #spam myself to make sure I see it
    for i in range(5):
        client = boto3.client('sns', 'us-west-1')
        client.publish(PhoneNumber='+1 (720) 635-6562', Message='the item has been purchased')

