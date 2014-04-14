import sys


def getCommonDigits(text1, text2):
    text1List = text1.split()
    text2List = text2.split()
    outList = []
    for letter in text1List:
        if letter in text2List and letter not in outList:
            outList.append(letter)
    return outList


def solveOne(file):
    answer1 = int(file.readline())
    row1 = [file.readline() for i in range(4)][answer1 - 1]
    print("First row answer: %d = %s" % (answer1, row1))
    answer2 = int(file.readline())
    row2 = [file.readline() for i in range(4)][answer2 - 1]
    print("Second row answer: %d = %s" % (answer2, row2))
    cd = getCommonDigits(row1, row2)
    if not cd:
        return "Volunteer cheated!"
    elif len(cd) == 1:
        return cd[0]
    else:
        return "Bad magician!"

f = open(sys.argv[1], 'r')
of = open(sys.argv[2], 'w')

T = int(f.readline())
print "Running [%s] Cases" % T

for t in range(T):
    res = solveOne(f)
    of.write("Case #%d: %s\n" % (t + 1, res))

f.close()
of.close()
