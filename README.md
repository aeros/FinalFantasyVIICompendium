# Final Fantasy VII Compendium

Seminole State College group project for a Final Fantasy VII Compendium. This includes:  

* An android application which provides a UI to search for Weapons and Armors, filtering by values for attributes such as "attack", "accuracy", "magic", and "cost".  
* A RESTful API implemented in Flask, which uses a MySQL database to interact with the Weapons and Armor data.  

# Requirements

* Python 3.7+  
  * To install required python packages, use `python -m pip install --user -r requirements.txt` in root project directory (preferably, within a virtual environment like pipenv or conda).   
* MySQL 5.7+  
* Android Studio 3.0+  

# Setup

1) Start the REST service using `python start_service.py` within the project root directory.  
2) Start the MySQL instance, create a new database named `ff7_compendium`, and add the following table schemas:  

```sql
CREATE TABLE `weapons` (
  `id` INT NOT NULL,
  `character` VARCHAR(20),
  `name` VARCHAR(50),
  `attack` INT NOT NULL,
  `accuracy` INT NOT NULL,
  `magic` INT NOT NULL,
  `cost` INT NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `armors` (
  `id` INT NOT NULL,
  `name` VARCHAR(50),
  `def` INT NOT NULL,
  `def%` INT NOT NULL,
  `mdef` INT NOT NULL,
  `mdef%` INT NOT NULL,
  `cost` INT NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

Credentials may need to be adjusted in `rest_service/database.py` (within `MySQLManager.__init__`) if not using the root user without a password (not recommended for production environments or publically exposed databases).

3) Import `*.csv` files in `data/` to the MySQL database. This can be done via the "Table Data Import Wizard" in MySQL Workbench (recommended), or from the CLI using `LOAD DATA LOCAL INFILE`.  
4) Open `android_app/FinalFantasyCompendium` in Android Studio, and run the app using a emulated device with API level of 18 or higher. The app was primarily tested against the Pixel 3a (API 30), but works on other devices as well.

# Authors
Kyle Stanley ([@aeros](https://github.com/aeros)) - RESTful API, data (ETL -> MySQL DB), and JSON request  
Colton Haynes ([@Ukazair](https://github.com/Ukazair)) - UX/UI and user input handling  
Gregory "Allen" Griffin ([@ALPAL1009](https://github.com/ALPAL1009)) - UX/UI and core application logic  

# Note

Since this was a college project and intended to be an academic exercise rather than for a production environment, it does not follow many best practices (particularly from a security perspective). Also, as this was pushed to GitHub after being completed, the commit history does not portray an accurate timeline of the project's development.
