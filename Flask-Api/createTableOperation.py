import sqlite3 as sqlt

def createTable():
    conn = sqlt.connect("my_medicalShop.db")

    cursor = conn.cursor()

    cursor.execute('''
CREATE TABLE IF NOT EXISTS Users(
                   id INTEGER PRIMARY KEY AUTOINCREMENT,
                   user_id VARCHAR(255),
                   password VARCHAR(255),
                   date_of_account_creation DATE,
                   isApproved BOOLEAN,
                   block BOOLEAN,
                   name VARCHA(255),
                   address VARCHAR(255),
                   email VARCHAR(255),
                   phone_number VARCHAR(255),
                   pin_code VARCHAR(255)
                   )
'''

    )

    # Table for products

    cursor.execute('''
CREATE TABLE IF NOT EXISTS Products(
                   id INTEGER PRIMARY KEY AUTOINCREMENT,
                   product_id VARCHAR(255),
                   name VARCHAR(255),
                   price FLOAT,
                   category VARCHAR(255),
                   stock INTEGER(255)
                   )
''')
    
    cursor.execute('''
CREATE TABLE IF NOT EXISTS Order_Details(
                   id INTEGER PRIMARY KEY AUTOINCREMENT,
                   order_id VARCHAR(255),
                   user_id VARCHAR(255),
                   product_id VARCHAR(255),
                   isApproved BOOLEAN,
                   quantity INT,
                   date_of_order_creation DATE,
                   price FLOAT,
                   total_amount FLOAT,
                   product_name VARCHAR(255),
                   user_name VARCHAR(255),
                   message VARCHAR(1000),
                   category VARCHAR(255)


                   )
''')
    
    cursor.execute('''
CREATE TABLE IF NOT EXISTS Sell_History(
                   id INTEGER PRIMARY KEY AUTOINCREMENT,
                   sell_id VARHCHAR(255),
                   product_id VARCHAR(255),
                   quantity INT,
                   remaining_stock INT,
                   date_of_sell DATE,
                   total_amount FLOAT,
                   price FLOAT,                  
                   product_name VARCHAR(255),
                   user_id VARCHAR(255),
                   user_name VARHCHAR(255)


                   )
''')
    
    cursor.execute('''
CREATE TABLE IF NOT EXISTS Available_Products(
                   id INTEGER PRIMARY KEY AUTOINCREMENT,
                   product_id VARCHAR(255),
                   product_name VARCHAR(255),
                   category VARCHAR(255),
                   price FLOAT, 
                   stock INT,
                   user_id VARCHAR(255),
                   user_name VARHCHAR(255)


                   )
''')

    conn.commit()
    conn.close()