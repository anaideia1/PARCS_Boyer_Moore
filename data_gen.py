import random
import string

s = ''
for _ in range(5000000):
    for _ in range(1, random.randint(5, 25)):
        s += random.choice(string.ascii_lowercase)
    s += ' '

with open('out/test_2.txt', 'w') as f:
    f.write(s)


with open('out/test_2.txt', 'r') as f:
    print(len(f.read()))
