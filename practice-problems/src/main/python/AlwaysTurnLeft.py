import os
import sys
from decimal import *
import math
from enum import Enum
from collections import defaultdict

# Set precision high enough
getcontext().prec = 10


class Direction(Enum):
    North, East, South, West = range(4)

    def clockwise(self):
        return Direction((self.value + 1) % 4)

    def counter_clockwise(self):
        return Direction((self.value - 1) % 4)

    def opposite(self):
        return Direction((self.value + 2) % 4)

    def offset(self):
        offsets = {Direction.North: (0, -1), Direction.South: (0, 1), Direction.East: (1, 0), Direction.West: (-1, 0)}
        return offsets[self]

    def bit(self):
        bits = {Direction.North: 1, Direction.South: 2, Direction.West: 4, Direction.East: 8}
        return bits[self]


def solve_one(f):

    pos_x = 0
    pos_y = -1
    facing = Direction.South
    rooms = defaultdict(int)

    for path in f.readwords():
        for x in list(path):
            if x == 'W':
                pos_x += facing.offset()[0]
                pos_y += facing.offset()[1]
                rooms[(pos_x, pos_y)] |= facing.opposite().bit()
            elif x == 'L':
                facing = facing.counter_clockwise()
                print(facing)
            elif x == 'R':
                facing = facing.clockwise()
                print(facing)
        # Delete the exit which is registered as a room
        del rooms[(pos_x, pos_y)]
        # Turn around twice to track back
        facing = facing.clockwise().clockwise()
        print(rooms)

    xs = sorted(set(x for x, _ in rooms.keys()))
    ys = sorted(set(y for _, y in rooms.keys()))
    return "\n" + "\n".join("".join("{:x}".format(rooms[(x, y)]) for x in xs) for y in ys).strip()


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

    def skiplines(n=1):
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
