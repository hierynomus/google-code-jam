import sys

R = 0
C = 0
M = 0


def gridToString(grid):
    return '\n'.join([''.join(line) for line in grid])


def transposedGridToString(grid):
    return '\n'.join([''.join([grid[y][x] for y in range(R)]) for x in range(C)])


def fillGrid(grid):
    if M == 0:
        return
    elif M == 1:
        grid[0][0] = '*'
        return

    x, y = (0, 0)
    m = M
    # Fill left to right until one but last row or 1 mine left
    while m > 1 and y < R - 2:
        grid[y][x] = '*'
        m -= 1
        x = x + 1
        if (x == C):
            x = 0
            y += 1
    # Fill top to bottom until one mine left
    while m > 1:
        grid[y][x] = '*'
        m -= 1
        if y == R - 2:
            y = y + 1
        else:
            y = y - 1
            x = x + 1

    # If last mine would be placed at one-but-last
    # And there are still 2 rows underneath empty
    # Move to first of new row
    if x == C - 2 and y < R - 2:
        grid[y + 1][0] = '*'
    else:
        grid[y][x] = '*'

    # # Last mine
    # if x + 1 == C - 1 and y + 1 < R:
    #     # Now in second to last column and not on last row
    #     grid[y + 1][0] = '*'
    # elif x == 1 and C > 2:
    #     # Let's place the last one at the end of the row
    #     grid[y][C - 1] = '*'
    # else:
    #     grid[y][x] = '*'


neig = [(i, j) for i in (-1, 0, 1) for j in (-1, 0, 1) if not (i == j == 0)]


def get_adjacent_cells(grid, x, y):
    for dx, dy in neig:
        if 0 <= (x + dx) < C and 0 <= y + dy < R:
            yield grid[y + dy][x + dx]


def cleanCells(grid, x, y):
    for dx, dy in neig:
        ny = y + dy
        nx = x + dx
        if 0 <= nx < C and 0 <= ny < R and not grid[ny][nx] == 'c':
            grid[ny][nx] = '.'


def hasNeighbourMines(grid, x, y):
    l = list(get_adjacent_cells(grid, x, y))
    # print x, y, l
    return '*' in l


def doPlacement(grid):
    # We can always place right-bottom
    if R * C > M:
        grid[R - 1][C - 1] = 'c'
    else:
        # Already done all are mines
        return

    for x in reversed(range(C)):
        for y in reversed(range(R)):
            if grid[y][x] in ['?', 'c', '.'] and not hasNeighbourMines(grid, x, y):
                cleanCells(grid, x, y)
            if '*' == grid[y][x]:
                break


def solveOne(file):
    global R, C, M
    R, C, M = map(lambda s: int(s), file.readline().split())
    print("R = ", R, "C = ", C, "M = ", M)

    grid = [['?' for x in range(C)] for y in range(R)]
    fillGrid(grid)
    doPlacement(grid)
    s = gridToString(grid)

    if '?' in s:
        # Transpose
        R, C = C, R
        grid = [['?' for x in range(C)] for y in range(R)]
        fillGrid(grid)
        doPlacement(grid)
        s = transposedGridToString(grid)

    print s
    return s if '?' not in s else "Impossible"


f = open(sys.argv[1], 'r')
of = open(sys.argv[2], 'w')

T = int(f.readline())
print("Running [%s] Cases" % T)

for t in range(T):
    res = solveOne(f)
    of.write("Case #%d:\n%s\n" % (t + 1, res))

f.close()
of.close()
