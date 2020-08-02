# Final Fantasy Compendium

Seminole State College group project for a Final Fantasy VII Compendium. This includes:

* An android application which provides a UI to search for Weapons and Armors, filtering by values for attributes such as "attack", "accuracy", "magic", and "cost". 
* A RESTful API implemented in Flask, which uses a MySQL database to interact with the Weapons and Armor data.

# Requirements

Python 3.7+
* To install required python packages, use `python -m pip install --user -r requirements.txt` in root project directory (preferably, within a virtual environment like pipenv or conda).
MySQL 5.7+
Android Studio 3.0+

# Setup

1) Start the REST service using `python start_service.py` within the project root directory.
2) Start the MySQL database, adding the table schemas mentioned in the comments of `rest_service/database.py`. Also, credentials may need to be updated to `MySQLManger.__init__` if not using the root user without a password (not recommended for production environments or publically exposed databases).
3) Import `*.csv` files in `data/` to the MySQL database. This can be done via the "Table Data Import Wizard" in MySQL Workbench (recommended), or from the CLI using `LOAD DATA LOCAL INFILE`.
4) Run the android app in Android Studio using a device with API level 18 or higher. The app was primarily tested against the Pixel 3a (API 30), but should work on other devices as well.

# Authors
Kyle Stanley (@aeros) - RESTful API, MySQL database, and JSON request
Colton Haynes (@ALPAL1009) - UX/UI and user input handling
Gregory "Allen" Griffin (@Ukazair) - UX/UI and core application logic
