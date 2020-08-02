#!/usr/bin/env python

import requests
import bs4
import pandas as pd


def get_html(site_url):
    response = requests.get(site_url)
    return bs4.BeautifulSoup(response.text, 'html.parser')


def get_weapon_data(tables):
    obj_data = []
    # for the weapons, each character has their own table
    characters = iter(['Cloud', 'Barret', 'Tifa', 'Aeris', 'Red XIII',
                       'Yuffie', 'Cait Sith', 'Vincent', 'Cid', 'Sephiroth'])

    for table in tables:
        current_character = next(characters)
        # Ignore first row w/ field names (assume tables have same format)
        rows = table.find_all("tr")[1:]
        for row in rows:
            object_info = _parse_weapon_fields(row)
            if object_info is not None:
                obj_data.append([current_character]+object_info)

    return obj_data


def _parse_weapon_fields(row):
    # Ignore rows with 2 or less elements: the wiki uses these for additional
    # info about the weapon
    if len(row) <= 2:
        return

    name = row.find('span', class_='attach').get_text()
    cells = row.find_all('td')
    atk = cells[0].get_text()
    acc = cells[1].get_text()
    mag = cells[2].get_text()
    cost = cells[4].get_text() if cells[4].get_text() != '' else '0'
    # Trim whitespace from front and back of the text
    weapon_info = [i.strip() for i in (name, atk, acc, mag, cost)]
    return weapon_info


if __name__ == '__main__':
    url = "https://finalfantasy.fandom.com/wiki/Final_Fantasy_VII_weapons"
    html = get_html(url)

    tables = html.find_all('table', class_="full-width article-table FFVII")
    obj_data = get_weapon_data(tables)
    df = pd.DataFrame(obj_data,
                      columns=['character', 'name', 'attack', 'accuracy',
                               'magic', 'cost'])
    print(df)
    df.to_csv("ff7_weapons_data.csv")
