import sqlite3 as sqlt

# get the user id
def getUserId(userName):
    conn = sqlt.connect('my_medicalShop.db')
    cursor = conn.cursor()
    cursor.execute("SELECT user_id FROM Users WHERE name = ?", (userName,))
    user = cursor.fetchone()
    conn.close()
    if user:
        return user[0]
    return None

# get product id
def getProductId(prodName):
    conn = sqlt.connect('my_medicalShop.db')
    cursor = conn.cursor()
    cursor.execute("SELECT product_id FROM Products WHERE name = ?", (prodName,))
    product = cursor.fetchall()
    conn.close()
    if product:
        return product[0]
    return None