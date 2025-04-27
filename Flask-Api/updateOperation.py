import sqlite3 as sqlt
from flask import jsonify, request

def updateApproveUser(userId, isApprove):

    conn = sqlt.connect('my_medicalShop.db')
    cursor = conn.cursor()

    cursor.execute("UPDATE Users SET isApproved = ? WHERE user_id = ?", (isApprove, userId))

    conn.commit()
    conn.close()

# for blocking and user

def updateBlockUser(userId, isBlocked):
    conn = sqlt.connect('my_medicalShop.db')
    cursor = conn.cursor()

    cursor.execute("UPDATE Users SET block = ? WHERE user_id = ?", (isBlocked, userId))

    conn.commit()
    conn.close()

    if isBlocked == 1:
        #print(isBlocked)
        return "User has been blocked or is already blocked"
    elif isBlocked == 0:
        #print(isBlocked)
        return "User has been unblocked or is already unblocked"

# To update any details the user wants
def updateUser(userId, name, email, phoneNumber, ):
    

    updatedUser = {}

    for key, value in request.form.items():
        if key != userId:
            updatedUser[key] = value
    
    # one more for loop to update the user in database
    conn = sqlt.connect('my_medicalShop.db')
    cursor = conn.cursor()

    for key, value in updatedUser.items():
        if key == 'name':
            cursor.execute("UPDATE Users SET name = ? WHERE user_id = ?", (value, userId))
        elif key == 'email':
            cursor.execute("UPDATE Users SET email = ? WHERE user_id = ?", (value, userId))
        elif key == 'phone_number':
            cursor.execute("UPDATE Users SET phone_number = ? WHERE user_id = ?", (value, userId))
    
    conn.commit()
    conn.close()


            

