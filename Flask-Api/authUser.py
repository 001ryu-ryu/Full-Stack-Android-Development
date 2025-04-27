import sqlite3 as sqlt

def authenticateUser(email, password):
    conn = sqlt.connect("my_medicalShop.db")
    cursor = conn.cursor()

    cursor.execute("SELECT * FROM Users WHERE email = ? AND password = ?", (email, password))

    user = cursor.fetchone()
    conn.close() # we do not need to commit bcz we did not write any changes here

    print(user)

    return user