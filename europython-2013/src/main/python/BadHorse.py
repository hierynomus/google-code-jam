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


def Zsolve_one(f):
    print("-------------------------")
    N = f.readint()

    def solve_rec(thug_tuples, group_1, group_2):
        # print(thug_tuples, group_1, group_2, sep="\n")
        if not thug_tuples:  # empty
            return group_1.isdisjoint(group_2)

        t, o = thug_tuples[0]
        if t in group_1 and o in group_1:
            return False
        elif t in group_2 and o in group_2:
            return False
        elif t in group_1:
            return solve_rec(thug_tuples[1:], group_1, group_2 | {o})
        elif t in group_2:
            return solve_rec(thug_tuples[1:], group_1 | {o}, group_2)
        else:
            return solve_rec(thug_tuples[1:], group_1 | {t}, group_2 | {o}) or solve_rec(thug_tuples[1:], group_1 | {o}, group_2 | {t})

    thugs = []
    for n in range(N):
        thugs += [tuple(f.readwords())]

    return "Yes" if solve_rec(thugs[1:], {thugs[0][0]}, {thugs[0][1]}) else "No"


def solve_one(f):
    print("-------------------------")
    N = f.readint()

    def solve_rec(thug_tuples, group_1, group_2):
        if not thug_tuples:  # empty
            return group_1.isdisjoint(group_2)

        t, l = thug_tuples[0]
        if t in group_1 and not group_1.isdisjoint(l):
            return False
        elif t in group_2 and not group_2.isdisjoint(l):
            return False
        elif t in group_1:
            return solve_rec(thug_tuples[1:], group_1, group_2 | l)
        elif t in group_2:
            return solve_rec(thug_tuples[1:], group_1 | l, group_2)
        else:
            return solve_rec(thug_tuples[1:], group_1 | {t}, group_2 | l) or solve_rec(thug_tuples[1:], group_1 | l, group_2 | {t})

    thug_dict = defaultdict(set)
    for n in range(N):
        x, y = tuple(f.readwords())
        thug_dict[x].add(y)
        thug_dict[y].add(x)

    return "Yes" if solve_rec(list(thug_dict.items()), set(), set()) else "No"


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
