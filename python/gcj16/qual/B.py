from gcj.gcj import GCJProblem

class B(GCJProblem):
    def solve_one(self, file):
        l = file.read_line()
        prev = l[0]
        flip = 0
        for p in l:
            if p == prev:
                continue
            else:
                prev = p
                flip += 1
        if l[-1] == '-':
            flip += 1
        return str(flip)
