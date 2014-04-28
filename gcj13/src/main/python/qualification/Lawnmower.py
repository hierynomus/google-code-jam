import os
import sys
from decimal import *
import math
from collections import defaultdict
from itertools import count
import operator
import getopt
from pprint import pprint


# Set precision high enough
getcontext().prec = 10

# Set recursion limit somewhat less restrictive
sys.setrecursionlimit(1500)


def solve_one(f):
    N, M = f.readints()
    lawn = f.readgrid(N, f.readints)
    our_lawn = new_grid(N, M, 100)

    for r in range(N):
        max_length = max(lawn[r])
        for c in range(M):
            our_lawn[r][c] = max_length

    for c in range(M):
        max_length = max([lawn[r][c] for r in range(N)])
        for r in range(N):
            if our_lawn[r][c] > max_length:
                our_lawn[r][c] = max_length

    return "YES" if lawn == our_lawn else "NO"


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
