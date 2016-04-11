import os
import sys
from decimal import *
import math
from collections import defaultdict
from itertools import count
import operator
import getopt


# Set precision high enough
getcontext().prec = 10

# Set recursion limit somewhat less restrictive
sys.setrecursionlimit(1500)


def find_last_unhappy(pancakes):
    for i in range(len(pancakes) - 1, -1, -1):
        if pancakes[i] == '-':
            return i
    return -1


def flip_stack(pancakes, i):
    new_pancakes = ['-'] * len(pancakes)
    for j in range(0, i + 1):
        new_pancakes[i - j] = '+' if pancakes[j] == '-' else '-'

    if len(pancakes) > i + 1:
        for j in range(i + 1, len(pancakes)):
            new_pancakes[j] = pancakes[j]

    return new_pancakes


def happy_streak(pancakes, i):
    for j in range(0, i):
        if pancakes[j] == '-':
            return j - 1
    return i - 1


def solve_one(f):
    pancakes = f.readchars()
    flips = 0
    while '-' in pancakes:
        print(pancakes)
        last_unhappy = find_last_unhappy(pancakes)
        if pancakes[0] == '+':
            pancakes = flip_stack(pancakes, happy_streak(pancakes, last_unhappy))
            flips += 1
        else:
            pancakes = flip_stack(pancakes, last_unhappy)
            flips += 1
    print(flips)
    return str(flips)


def main():
    validate = False
    opts, args = getopt.gnu_getopt(sys.argv[1:], "v", [])
    if ("-v", '') in opts:
        validate = True

    inpath = args[0]
    if len(args) > 1:
        outpath = args[1]
    else:
        outpath = os.path.splitext(os.path.basename(inpath))[0] + ".out"

    with InFileWrapper(inpath) as f, OutFileWrapper(outpath) as of:
        T = f.readint()
        print("Running [{}] Cases".format(T))

        for t in range(1, T + 1):
            res = solve_one(f)
            of.write_case(t)
            of.write_string(res)
            of.write_case_end()

    if validate:
        correct = True
        expected_path = os.path.splitext(inpath)[0] + ".expected"
        with open(outpath, "r") as out, open(expected_path, "r") as expected:
            for line in out:
                expected_line = expected.readline()
                if line.strip() != expected_line.strip():
                    correct = False
                    print("Output [{}]\nExpected [{}]".format(line.strip(), expected_line.strip()))
        if not correct:
            raise Exception("Did not validate")


def new_grid(rows, cols, value):
    return [([value] * cols) for i in range(rows)]


def transpose_grid(grid):
    """
    Transposes a grid (ie. switches the rows and the columns)
    """
    return list(zip(*grid))


def fold(f, l, a):
    """
    f: the function to apply
    l: the list to fold
    a: the accumulator, who is also the 'zero' on the first call
    """
    return a if(len(l) == 0) else fold(f, l[1:], f(a, l[0]))


def sort_dict_on_value(d, reverse=False):
    return sorted(d.items(), key=operator.itemgetter(1), reverse=reverse)


def point_distance(p1, p2):
    """Works for both lists and tuples"""
    return math.sqrt((p1[0] - p2[0])**2 + (p1[1] - p2[1])**2)


class FileWrapper:
    def __init__(self, path, mode):
        self.file = open(path, mode)

    def close(self):
        self.file.close()

    def __enter__(self):
        return self

    def __exit__(self, exc_type, exc_value, traceback):
        self.close()


class InFileWrapper(FileWrapper):
    def __init__(self, path):
        FileWrapper.__init__(self, path, 'r')

    def readchars(self, strip=True):
        return list(self.readline(strip))

    def readint(self):
        return int(self.readline())

    def readints(self):
        return [int(z) for z in self.readline().split()]

    def readfloat(self):
        return float(self.file.readline())

    def readfloats(self):
        return [float(z) for z in self.readline().split()]

    def readwords(self):
        return self.readline().split()

    def readdecimal(self):
        return [Decimal(d) for d in self.readline().split()]

    def readline(self, strip=True):
        if strip:
            return self.file.readline().strip()
        else:
            return self.file.readline()

    def readgrid(self, rows, read_func):
        return [read_func() for x in range(rows)]

    def skiplines(self, n=1):
        for x in range(n):
            self.readline()


class OutFileWrapper(FileWrapper):
    def __init__(self, path):
        FileWrapper.__init__(self, path, 'w')

    def write_case(self, n):
        self.file.write("Case #{}: ".format(n))
        return self

    def write_string(self, s):
        self.file.write(s)
        return self

    def write_decimal(self, d):
        self.file.write("{:.7f}".format(d))

    def write_case_end(self):
        self.file.write("\n")

if __name__ == "__main__":
    main()
