import sys
from decimal import *


getcontext().prec = 10


def countDeceit(N, naomi, ken):
    won = 0
    for n in range(N):
        naomi_pop_idx = 0
        if (naomi[-1] > ken[-1]):
            naomi_pop_idx = -1
        chosen_naomi = naomi.pop(naomi_pop_idx)
        chosen_ken = ken.pop()
        if chosen_naomi > chosen_ken:
            won += 1

    return won


def countWar(N, naomi, ken):
    won = 0
    for n in range(N):
        chosen_naomi = naomi.pop()
        ken_pop_idx = -1
        if (chosen_naomi > ken[-1]):
            ken_pop_idx = 0
        chosen_ken = ken.pop(ken_pop_idx)
        if chosen_naomi > chosen_ken:
            won += 1

    return won


def solveOne(file):
    N = int(file.readline())
    naomi = sorted(map(lambda s: Decimal(s), file.readline().split()))
    ken = sorted(map(lambda s: Decimal(s), file.readline().split()))

    print("Naomi = ", naomi)
    print("Ken = ", ken)
    return (countDeceit(N, list(naomi), list(ken)), countWar(N, naomi, ken))

f = open(sys.argv[1], 'r')
of = open(sys.argv[2], 'w')

T = int(f.readline())
print "Running [%s] Cases" % T

for t in range(T):
    res = solveOne(f)
    of.write("Case #%d: %d %d\n" % (t + 1, res[0], res[1]))

f.close()
of.close()
