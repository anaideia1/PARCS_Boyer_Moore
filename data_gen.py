import random
import string

s = ''
for _ in range(10000000):
    for _ in range(1, random.randint(5, 25)):
        s += random.choice(string.ascii_lowercase)
    s += ' '

with open('test_3.txt', 'w') as f:
    f.write(s)


with open('test_3.txt', 'r') as f:
    print(len(f.read()))
