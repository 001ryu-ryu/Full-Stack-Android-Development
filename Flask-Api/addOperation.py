import sqlite3 as sqlt
import uuid
from datetime import date

from flask import jsonify


def createUser(name, password, phoneNumber, email, pinCode, address):


    try:
        conn = sqlt.connect("my_medicalShop.db")
        cursor = conn.cursor()

        userId = str(uuid.uuid4())
        dateOfAccountCreation = date.today()

        cursor.execute('''
        INSERT INTO Users(user_id, password, date_of_account_creation, isApproved, block, name, address, email, phone_number, pin_code) VALUES(?,?,?,?,?,?,?,?,?,?)
        ''' , (userId, password, dateOfAccountCreation, 0, 0, name, address, email, phoneNumber, pinCode))
    
        conn.commit()
        conn.close()

        return jsonify({
        'message' : userId,
        'status' : 200
        })
    
    except Exception as e:
        return jsonify({
        'message' : e,
        'status' : 400
        })
    


# insert products
def insertProduct(name, price, category, stock):
    try:
        conn = sqlt.connect('my_medicalShop.db')
        cursor = conn.cursor()

        productId = str(uuid.uuid4())

        cursor.execute('''
        INSERT INTO Products(product_id, name, price, category, stock) VALUES(?, ?, ?, ?, ?)
''', (productId, name, price, category, stock))
        
        conn.commit()
        conn.close()

        return jsonify({'message' : f"Product {name} inserted with product id {productId}", 'status' : 200})
    except Exception as e:
        return jsonify({
        'message' : e,
        'status' : 400
        })
    

# here will be logic for order details
def insertOrderDetails(userId, productId, quantity, price, totalAmount, productName, userName, message, category):
    try:
        conn = sqlt.connect('my_medicalShop.db')
        cursor = conn.cursor()

        orderId = str(uuid.uuid4())
        dateOfOrderCreation = date.today()

        cursor.execute('''
        INSERT INTO Order_Details(order_id, user_id, product_id, isApproved, quantity, date_of_order_creation, price, total_amount, product_name, user_name, message, category) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        ''', (orderId, userId, productId, 0, quantity, dateOfOrderCreation, price, totalAmount, productName, userName, message, category))
        conn.commit()
        conn.close()
        return jsonify({'message' : f"Order details inserted with order id {orderId}", 'status' : 200})
    except Exception as e:
        return jsonify({
        'message' : str(e),
        'status' : 400
        })



# here will be adding logic for sell history
def insertSellHistory(productId, quantity, remainingStock, totalAmount, price, productName, userId, userName):
    try:
        conn = sqlt.connect('my_medicalShop.db')
        cursor = conn.cursor()

        sellId = str(uuid.uuid4())
        dateOfSell = date.today()

        cursor.execute('''
INSERT INTO Sell_History(sell_id, product_id, quantity, remaining_stock, date_of_sell, total_amount, price, product_name, user_id, user_name) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
''', (sellId, productId, quantity, remainingStock, dateOfSell, totalAmount, price, productName, userId, userName))
        conn.commit()
        conn.close()

        return jsonify({'message' : f"Selled order inserted with sell id {sellId}", 'status' : 200})
    except Exception as e:
        return jsonify({
        'message' : str(e),
        'status' : 400
        })

# here will be logic for available products
def insertAvailableProducts(productId, productName, category, price, stock, userId, userName):
    try:
        conn = sqlt.connect('my_medicalShop.db')
        cursor = conn.cursor()

        availableProductId = str(uuid.uuid4())

        cursor.execute('''
INSERT INTO Available_Products(product_id, product_name, category, price, stock, user_id, user_name) VALUES(?, ?, ?, ?, ?, ?, ?)
''', (productId, productName, category, price, stock, userId, userName))
        conn.commit()
        conn.close()

        return jsonify({'message' : f"Available product inserted with available product id {availableProductId}", 'status' : 200})
    except Exception as e:
        return jsonify({
        'message' : str(e),
        'status' : 400
        })