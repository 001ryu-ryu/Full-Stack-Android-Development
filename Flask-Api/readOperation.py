import sqlite3 as sqlt


def readAllUsers():
    conn = sqlt.connect("my_medicalShop.db")
    cursor = conn.cursor()

    cursor.execute("SELECT * FROM Users")

    users = cursor.fetchall()
    conn.close()
    userJson = []

    for user in users:
        tempUser = {
            'id' :user[0],
            'user_id' : user[1],
            'password' : user[2],
            'date_of_account_creation' : user[3],
            'isApproved' : user[4],
            'block' : user[5],
            'name' : user[6],
            'address' : user[7],
            'email' : user[8],
            'phone_number' : user[9],
            'pin_code' : user[10]
        }
        userJson.append(tempUser)
    return userJson


   

def specificUser(userId):
    conn = sqlt.connect("my_medicalShop.db")
    cursor = conn.cursor()

    cursor.execute("SELECT * FROM Users WHERE user_id = ?", (userId,))

    user = cursor.fetchone()
    conn.close()

    tempUser = {
            'id' :user[0],
            'user_id' : user[1],
            'password' : user[2],
            'date_of_account_creation' : user[3],
            'isApproved' : user[4],
            'block' : user[5],
            'name' : user[6],
            'address' : user[7],
            'email' : user[8],
            'phone_number' : user[9],
            'pin_code' : user[10]
        }
    userList = []
    userList.append(tempUser)
    return userList

    


# Here Will be logic for products

#get all products

def readAllProducts():
    conn = sqlt.connect('my_medicalShop.db')
    cursor = conn.cursor()
    cursor.execute("SELECT * FROM Products")

    products = cursor.fetchall()
    conn.close()

    productJson = []

    for product in products:
        tempProd = {
            'id' : product[0],
            'product_id' : product[1],
            'name' : product[2],
            'price' : product[3],
            'category' : product[4],
            'stock' : product[5]
        }

        productJson.append(tempProd)
    
    return productJson


# get a specific product

def getSpecificProduct(productId):
    conn = sqlt.connect('my_medicalShop.db')
    cursor = conn.cursor()

    cursor.execute("SELECT * FROM Products WHERE product_id = ?", (productId,))

    product = cursor.fetchone()
    conn.close()

    tempProd = {
        'id' : product[0],
        'product_id' : product[1],
        'name' : product[2],
        'price' : product[3],
        'category' : product[4],
        'stock' : product[5]
    }

    specificProduct = []

    specificProduct.append(tempProd)

    return specificProduct


# get order details
def getAllOrderDetails():
    conn = sqlt.connect('my_medicalShop.db')
    cursor = conn.cursor()

    cursor.execute("SELECT * FROM Order_Details")

    all_orders = cursor.fetchall()
    conn.close()

    orders = []
    for order in all_orders:
        tempOrder = {
            'id' : order[0],
            'order_id' : order[1],
            'user_id' : order[2],
            'product_id' : order[3],
            'isApproved' : order[4],
            'quantity' : order[5],
            'date_of_order_creation' : order[6],
            'price' : order[7],
            'total_amount' : order[8],
            'product_name' : order[9],
            'user_name' : order[10],
            'message' : order[11],
            'category' : order[12]
        }
        
        orders.append(tempOrder)
    return orders


# get order details for a specific user
def getOrderForSpecificUser(userId):
    conn = sqlt.connect('my_medicalShop.db')
    cursor = conn.cursor()

    cursor.execute("SELECT * FROM Order_Details WHERE user_id = ?", (userId,))

    orders = cursor.fetchall()

    ordersForSpecificUser = []

    for order in orders:
        tempOrder = {
            'id' : order[0],
            'order_id' : order[1],
            'user_id' : userId,
            'product_id' : order[3],
            'isApproved' : order[4],
            'quantity' : order[5],
            'date_of_order_creation' : order[6],
            'price' : order[7],
            'total_amount' : order[8],
            'product_name' : order[9],
            'user_name' : order[10],
            'message' : order[11],
            'category' : order[12]
        }

        ordersForSpecificUser.append(tempOrder)
    return ordersForSpecificUser

    


# get specific order details for a specific product
def getOrderForSpecificProduct(productId):
    conn = sqlt.connect('my_medicalShop.db')
    cursor = conn.cursor()

    cursor.execute("SELECT * FROM Order_Details WHERE product_id = ?", (productId,))

    orders = cursor.fetchall()

    orderForSpecificProduct = []

    for order in orders:
        tempOrder = {
            'id' : order[0],
            'order_id' : order[1],
            'user_id' : order[2],
            'product_id' : productId,
            'isApproved' : order[4],
            'quantity' : order[5],
            'date_of_order_creation' : order[6],
            'price' : order[7],
            'total_amount' : order[8],
            'product_name' : order[9],
            'user_name' : order[10],
            'message' : order[11],
            'category' : order[12]
        }

        orderForSpecificProduct.append(tempOrder)
    return orderForSpecificProduct

# get sell history
def getSellHistory():
    conn = sqlt.connect('my_medicalShop.db')
    cursor = conn.cursor()

    cursor.execute("SELECT * FROM Sell_History")

    sellHistory = cursor.fetchall()
    conn.close()

    sellHistoryJson = []

    for sell in sellHistory:
        tempSell = {
            'id' : sell[0],
            'sell_id' : sell[1],
            'product_id' : sell[2],
            'quantity' : sell[3],
            'date_of_sell_creation' : sell[4],
            'price' : sell[5],
            'total_amount' : sell[6]
        }
        sellHistoryJson.append(tempSell)
    
    return sellHistoryJson

# get all available products
def getAvailableProducts():
    conn = sqlt.connect('my_medicalShop.db')
    cursor = conn.cursor()

    cursor.execute("SELECT * FROM Products WHERE stock > 0")

    availableProducts = cursor.fetchall()
    conn.close()

    availableProductsJson = []

    for product in availableProducts:
        tempProd = {
            'id' : product[0],
            'product_id' : product[1],
            'name' : product[2],
            'price' : product[3],
            'category' : product[4],
            'stock' : product[5]
        }
        availableProductsJson.append(tempProd)
    
    return availableProductsJson