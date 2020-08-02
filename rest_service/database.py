import pymysql

"""Requires a MySQL database named "ff7_compendium", with the following table schemas:

CREATE TABLE `weapons` (
  `id` int(11) NOT NULL,
  `character` VARCHAR(20),
  `name` VARCHAR(50),
  `attack` int(11) NOT NULL,
  `accuracy` int(11) NOT NULL,
  `magic` int(11) NOT NULL,
  `cost` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `armors` (
  `id` int(11) NOT NULL,
  `name` VARCHAR(50),
  `def` int(11) NOT NULL,
  `def%` int(11) NOT NULL,
  `mdef` int(11) NOT NULL,
  `mdef%` int(11) NOT NULL,
  `cost` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

By default, it uses "root" as the login user with no password. However, this can be customized in
the __init__ to any credentials, as long as the user has read permissions for the above tables.

For a production system, the credentials should be stored in a secured local configuration file, read during
startup, and passed to the database manager. However, that is outside of the scope of this project.
"""

class MySQLManager:
    def __init__(self):
        # In production, we should not use root user and use a password
        self._connection = pymysql.connect(host='localhost',
                                        user='root',
                                        db='ff7_compendium',
                                        charset='utf8')



    def close(self):
        self._connection.close()

    
    def query_weapons(self, filters=''):
        """Construct a dynamic SQL query to weapons table using specified filters.

        Returns a JSON object, contained within a result list. 
        """
        sql = """
        SELECT CONCAT(
            '{',
            '\"results\": [',
            GROUP_CONCAT(
                JSON_OBJECT(
                    'character', `character`,
                    'name', `name`,
                    'attack', `attack`,
                    'accuracy', `accuracy`,
                    'magic', `magic`,
                    'cost', `cost`
                )
            ),
            ']',
            '}'
        )
        FROM `weapons`
        """
        return self._fetch_query(sql, filters)

    def query_armors(self, filters=''):
        """Construct a dynamic SQL query to armors table using specified filters.
        
        Returns a JSON object, contained within a result list. 
        """
        sql = """
        SELECT CONCAT(
            '{',
            '\"results\": [',
            GROUP_CONCAT(
                JSON_OBJECT(
                    'name', `name`,
                    'def', `def`,
                    'def%', `def%`,
                    'mdef', `mdef`,
                    'mdef%', `mdef%`,
                    'cost', `cost`
                )
            ),
            ']',
            '}'
        )
        FROM `armors`
        """
        return self._fetch_query(sql, filters)

    def _fetch_query(self, sql, filters):
        if filters:
            sql += f'\n\tWHERE {filters};'
        else:
            sql += ';'
        
        with self._connection.cursor() as cursor:
            # Increase max concat length, as default truncates results > 1024 chars
            cursor.execute("SET SESSION group_concat_max_len = 1000000;")
            cursor.execute(sql)
            res = cursor.fetchone()
            print(res)
            if res[0]:
                return True, res[0]
            else:
                return True, '{}' # return empty JSON object to represent no results