import os
import sys
from decimal import *
import math
from collections import defaultdict
from itertools import count
import operator

# Set precision high enough
getcontext().prec = 10


def solve_one(f):
    C = f.readint()
    f.skiplines(1)
    P = dict(zip(count(1), f.readints()))
    Ps = sort_dict_on_value(P)

    p1, p2 = (0, len(Ps) - 1)

    amount = Ps[p1][1] + Ps[p2][1]
    while (amount != C):
        if (amount < C):
            p1 += 1
        else:
            p2 -= 1
        amount = Ps[p1][1] + Ps[p2][1]

    if Ps[p1][0] < Ps[p2][0]:
        return "{} {}".format(Ps[p1][0], Ps[p2][0])
    else:
        return "{} {}".format(Ps[p2][0], Ps[p1][0])


def main():
    inpath = sys.argv[1]
    if len(sys.argv) > 2:
        outpath = sys.argv[2]
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

    def readchars(self):
        return list(self.readline())

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

    def readline(self):
        return self.file.readline().strip()

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
