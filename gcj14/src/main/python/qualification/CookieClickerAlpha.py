import sys
from decimal import *


getcontext().prec = 10


def solveRecursion(Rate, C, F, X, time_elapsed, last_time_to_target):
    """Unfortunately recursion does not work, it goes to deep for the test sets."""
    time_to_farm = C / Rate
    total_time_after_farm = time_elapsed + time_to_farm + (X / (Rate + F))
    if (total_time_after_farm > last_time_to_target):
        return last_time_to_target
    else:
        return solveRecursion(Rate + F, C, F, X, time_elapsed + time_to_farm,
                              total_time_after_farm)


def solveOne(file):
    """C = CookieFarm cost, F = Extra Cookies per CookieFarm, X = Target"""
    line = file.readline().split()
    Rate = Decimal(2)
    C, F, X = map(lambda s: Decimal(s), line)
    print("C = ", C, "F = ", F, "X = ", X)

    new_rate = Rate
    time_elapsed = Decimal(0)
    time_to_farm = C / new_rate
    time_after_farm = time_to_farm + (X / (new_rate + F))
    last_time = X / new_rate

    while (time_after_farm < last_time):
        new_rate += F
        time_elapsed += time_to_farm
        time_to_farm = C / new_rate
        last_time = time_after_farm
        time_after_farm = time_elapsed + time_to_farm + (X / (new_rate + F))

    return last_time

f = open(sys.argv[1], 'r')
of = open(sys.argv[2], 'w')

T = int(f.readline())
print("Running [%s] Cases" % T)

for t in range(T):
    res = solveOne(f)
    of.write("Case #%d: %.7f\n" % (t + 1, res))

f.close()
of.close()
