# -*- coding: utf-8 -*-
import subprocess

tables = [
    ("news_category", "category_id, category_name, HEX(category_name), CHAR_LENGTH(category_name)"),
    ("news_article", "article_id, title, HEX(title), CHAR_LENGTH(title)"),
    ("activity", "activity_id, activity_name, HEX(activity_name), CHAR_LENGTH(activity_name)"),
    ("channel", "channel_id, channel_name, HEX(channel_name), CHAR_LENGTH(channel_name)"),
]

for table, cols in tables:
    sql = f"SELECT {cols} FROM {table};"
    result = subprocess.run(
        ['mysql', '-uroot', '-p123456', 'ry', '--default-character-set=utf8mb4', '-e', sql],
        capture_output=True
    )
    out = result.stdout.decode('utf-8', errors='replace')
    print(f"=== {table} ===")
    print(out)
