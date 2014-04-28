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


def flip_switch(n, outlets):
    mask = 1 << n
    return map(lambda x: x ^ mask, outlets)


def count_distance(outlets, devices, n):
    mask = 1 << n
    diff = 0
    for o in range(len(outlets)):
        diff += (outlets[o] & mask) - (devices[o] & mask)

    return diff


def as_bin(l):
    return list(map(lambda x: bin(x), l))

min_s = 0


def solve_one(f):
    global min_s
    N, L = f.readints()
    O = sorted(map(lambda x: int(x, 2), f.readwords()))
    D = sorted(map(lambda x: int(x, 2), f.readwords()))
    min_s = L

    # print(as_bin(O), as_bin(D), sep="\n")

    def solve(outlets, devices, n, s):
        global min_s
        if s >= min_s:
            return min_s
        if outlets == devices:
            min_s = s
            return s
        elif n < 0:
            return L + 1

        diff = count_distance(outlets, devices, n)
        new_outlets = sorted(flip_switch(n, outlets))
        new_diff = count_distance(new_outlets, devices, n)

        if diff == 0 and new_diff == 0:
            switches = solve(outlets, devices, n-1, s)
            if (min_s > switches):
                min_s = switches
            return min(switches, solve(new_outlets, devices, n-1, s+1))
        elif new_diff == 0:
            # print("-----")
            # print(as_bin(new_outlets), as_bin(devices), sep="\n")
            switches = solve(new_outlets, devices, n-1, s+1)
            if (min_s > switches):
                min_s = switches
            return switches
        elif diff == 0:
            switches = solve(outlets, devices, n-1, s)
            if (min_s > switches):
                min_s = switches
            return switches
        else:
            return L + 1

    sol = solve(O, D, L, 0)
    return "NOT POSSIBLE" if sol == L + 1 else str(sol)


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
            print(".", end="")
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
