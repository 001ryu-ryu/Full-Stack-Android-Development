from flask import Flask, jsonify, request

from createTableOperation import createTable
from addOperation import *
from authUser import authenticateUser
from readOperation import *
from updateOperation import *
from getIds import *

app = Flask(__name__)  # this name is the file name

@app.route('/', methods=['GET'])
def hello():
    return jsonify({
        "name" : "Iftikar", "phoneNo" : {
            "country" : "+91",
            "number" : 600000000
        }
    })


@app.route('/createUser', methods=['POST'])
def signIn():
    try:
        name = request.form['name']
        password = request.form['password']
        phoneNumber = request.form['phoneNumber']
        email = request.form['email']
        pinCode = request.form['pinCode']
        address = request.form['address']

        response = createUser(name, password, phoneNumber, email, pinCode, address)


        return response
    except Exception as e:
        return jsonify({
        'message' : str(e),
        'status' : 400
        })

@app.route('/login', methods=['POST'])
def logIn():
    try:
        email = request.form['email']
        password = request.form['password']

        user = authenticateUser(email, password)

        if user:
            return jsonify({'status' : 200, 'message' : user[1]})
        else:
            return jsonify({'status' : 401, 'message' : "Invalid email or password"})

    
    except Exception as e:
        return jsonify({
        'message' : str(e),
        'status' : 400
        })

# This is to get all the users, and this will be used in admin side
@app.route('/getAllUsers', methods=['GET'])
def getAllUsers():

    try:
        return readAllUsers()
    except Exception as e:
        return jsonify({
        'message' : str(e),
        'status' : 400
        })


@app.route('/getSpecificUser', methods = ['POST'])
def get_specific_user():
    try:
        userId = request.form['user_id']
        user = specificUser(userId)
        return user
    except Exception as e:
        return jsonify({
        'message' : str(e),
        'status' : 400
        })

@app.route('/approveUser', methods = ['PATCH'])
def approve_user():
    try:
        user_id = request.form['user_id']
        approve = request.form['approve']

        access = updateApproveUser(userId=user_id, isApprove=approve)

        return jsonify({'message' : "User has given access", 'status' : 200})
    
    except Exception as e:
        return jsonify({
        'message' : str(e),
        'status' : 400
        })

# for blocking
@app.route('/blockUser', methods = ['PATCH'])
def block_user():
    try:
        user_id = request.form['user_id']
        block_status = request.form['block']
        message = updateBlockUser(user_id, block_status)

        #print(message)

        return jsonify({'message' : message, 'status' : 200})
    except Exception as e:
        return jsonify({
        'message' : str(e),
        'status' : 400
        })
        

# Function to update details of an user
@app.route('/updateUser', methods = ['PATCH'])
def update_user():

    try:
        user_id = request.form['user_id']
        name = request.form['name']
        email = request.form['email']
        phone_number = request.form['phone_number']

        updateUser(user_id, name, email, phone_number)

        return jsonify({'message' : "User updated successfully", 'status' : 200})
    
    except Exception as e:
        return jsonify({
        'message' : str(e),
        'status' : 400
        })




# HERE WILL BE LOGIC FOR THE PRODUCTS

#insert products
@app.route('/insertProducts', methods = ['POST'])
def insert_product():
    try:
        name = request.form['name']
        price = request.form['price']
        category = request.form['category']
        stock = request.form['stock']

        respond = insertProduct(name, price, category, stock)

        return respond
    except Exception as e:
        return jsonify({
        'message' : str(e),
        'status' : 400
        })



#get all products
@app.route('/getAllProducts', methods = ['GET'])
def getAllProducts():
    try:
        return readAllProducts()
    except Exception as e:
        return jsonify({
        'message' : str(e),
        'status' : 400
        })

# get a specific product
@app.route('/getSpecificProd', methods = ['GET'])
def get_specific_product():
    try:
        productId = request.form['product_id']

        return getSpecificProduct(productId)
    except Exception as e:
        return jsonify({
        'message' : str(e),
        'status' : 400
        })


# here will be logic for order details
@app.route('/getId', methods = ['GET'])
def getId():
    return getUserId()

#insert order details
@app.route('/insertOrderDetails', methods = ['POST'])
def insert_order_details():
    try:
        userId = request.form['user_id']
        productId = request.form['product_id']
        quantity = request.form['quantity']
        price = request.form['price']
        totalAmount = request.form['total_amount']
        productName = request.form['product_name']
        userName = request.form['user_name']
        message = request.form['message']
        category = request.form['category']

        response = insertOrderDetails(userId, productId, quantity, price, totalAmount, productName, userName, message, category)

        return response
    except Exception as e:
        return jsonify({
        'message' : str(e),
        'status' : 400
        })
    
#get all order_details
@app.route('/getAllOrders', methods = ['GET'])
def get_all_orders():
    try:
        return getAllOrderDetails()
    except Exception as e:
        return f"Error fetching orders dut to {str(e)}"

# get specific order for a specific user
@app.route('/getSpecificUserOrder/<user_id>', methods = ['GET'])
def specific_user_order(user_id):
    try:
        orders = getOrderForSpecificUser(user_id)
        return jsonify({'orders': orders, 'status': 200})
    except Exception as e:
        return jsonify({'message': str(e), 'status': 400})




# get specific order for a specific product
@app.route('/getSpecificProductOrder/<product_id>', methods = ['GET'])
def get_specific_product_order(product_id):
    try:
        orders = getOrderForSpecificProduct(product_id)
        return jsonify({'orders' : orders, 'status' : 200})
    except Exception as e:
        return jsonify({'message': str(e), 'status': 400})




# here will be logic for sell history
@app.route('/insertSellHistory', methods = ['POST'])
def insert_sell_history():
    try:
        productId = request.form['product_id']
        quantity = request.form['quantity']
        remainingStock = request.form['remaining_stock']
        totalAmount = request.form['total_amount']
        price = request.form['price']
        productName = request.form['product_name']
        userId = request.form['user_id']
        userName = request.form['user_name']

        response = insertSellHistory(productId, quantity, remainingStock, totalAmount, price, productName, userId, userName)

        return response
    except Exception as e:
        return jsonify({
        'message' : str(e),
        'status' : 400
        })

# get all sell history
@app.route('/getAllSellHistory', methods = ['GET'])
def get_all_sell_history():
    try:
        return getSellHistory()
    except Exception as e:
        return jsonify({
        'message' : str(e),
        'status' : 400
        })

# insert available products
@app.route('/insertAvailableProducts', methods = ['POST'])
def insert_available_products():
    try:
        productId = request.form['product_id']
        productName = request.form['product_name']
        category = request.form['category']
        price = request.form['price']
        stock = request.form['stock']
        userId = request.form['user_id']
        userName = request.form['user_name']

        response = insertAvailableProducts(productId, productName, category, price, stock, userId, userName)

        return response
    except Exception as e:
        return jsonify({
        'message' : str(e),
        'status' : 400
        })
# get all available products
@app.route('/getAvailableProducts', methods = ['GET'])
def get_available_products():
    try:
        return getAvailableProducts()
    except Exception as e:
        return jsonify({
        'message' : str(e),
        'status' : 400
        })

if __name__ == '__main__':  # here if file name is main, only then run it
    createTable()
    app.run(debug=True)




