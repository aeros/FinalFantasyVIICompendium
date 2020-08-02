from flask import Flask, request, jsonify
import json

# local import
from . import database

app = Flask(__name__)

"""Example JSON format:
{
    'type': 'weapons',
    'attributes': {
        'attack': ['>=', 30],
        'magic': ['<', 100],
    }

}
# The above would return all weapons with attack >= 30, and magic < 100.
"""


@app.route('/FF7/api', methods=['GET', 'POST'])
def main():
    request_data = request.get_json(force=True)
    print(f"request_data = {request_data}")
    response_data = get_entity_json(request_data)
    print(f"response_data = {response_data}")
    response = jsonify(json.loads(response_data))
    response.headers['Content-Type'] = 'application/json; charset=utf-8'
    return response


def get_entity_json(request_data):
    query = build_query(request_data)
    # false indicates an error occurred
    if not query[0]:
        return {'error': query[1]}
    else:
        return query[1]


def build_query(rdata):
    try:
        entity_type = rdata['type']
        attributes = rdata['attributes']
    except KeyError:
        return False, "request JSON must have 'type' and 'attributes' keys"

    if not isinstance(attributes, dict):
        return False, "'attributes' key must contain a JSON object"

    try:
        filters = join_filters(attributes)
    except IndexError:
        return False, "each key in attributes must be in format of: " \
                      "'attr_name: [symbol, value]'"
    except ValueError:
        return False, "symbol must be one of ('>','>=','=','<=','<', 'LIKE')"

    dbm = database.MySQLManager()
    if entity_type == 'weapons':
        return dbm.query_weapons(filters)
    elif entity_type == 'armors':
        return dbm.query_armors(filters)
    elif entity_type == 'items':
        return False, "not implemented yet for items"
    else:
        return False, "type must be one of (weapons, armors, items)"


def join_filters(attributes):
    filters = []
    for name, sym_val in attributes.items():
        symbol = sym_val[0]
        if not isinstance(symbol, str) or symbol not in ('>','>=','=','<=','<', 'LIKE'):
            raise ValueError
        value = sym_val[1]
        filter_str = f"`{name}` {symbol} {value}"
        filters.append(filter_str)
    return " AND ".join(filters)


def run():
    app.run(debug=True)


if __name__ == '__main__':
    run()
