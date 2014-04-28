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


def isWin(c1, c2, c3, c4):
    chars = [c for c in [c1, c2, c3, c4] if c != 'T']
    if any(x == '.' for x in chars):
        return None
    return chars[0] if all(c == chars[0] for c in chars) else None


def solve_one(f):
    grid = f.readgrid(4, f.readchars)
    f.skiplines(1)
    positions = [
        # Rows
        isWin(grid[0][0], grid[0][1], grid[0][2], grid[0][3]),
        isWin(grid[1][0], grid[1][1], grid[1][2], grid[1][3]),
        isWin(grid[2][0], grid[2][1], grid[2][2], grid[2][3]),
        isWin(grid[3][0], grid[3][1], grid[3][2], grid[3][3]),
        # Diagonals
        isWin(grid[0][0], grid[1][1], grid[2][2], grid[3][3]),
        isWin(grid[0][3], grid[1][2], grid[2][1], grid[3][0]),
        # Columns
        isWin(grid[0][0], grid[1][0], grid[2][0], grid[3][0]),
        isWin(grid[0][1], grid[1][1], grid[2][1], grid[3][1]),
        isWin(grid[0][2], grid[1][2], grid[2][2], grid[3][2]),
        isWin(grid[0][3], grid[1][3], grid[2][3], grid[3][3])
    ]
    win = [w for w in positions if w]
    if win:
        return win[0] + " won"
    else:
        for x in range(4):
            for y in range(4):
                if grid[x][y] == '.':
                    return "Game has not completed"

    return "Draw"


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
            print("Case #", t, ": ", sep="")
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
