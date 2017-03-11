from gcj.gcj import GCJProblem

class A(GCJProblem):
    def solve_one(self, file):
        not_seen = set([0, 1, 2, 3, 4, 5, 6, 7, 8, 9])
        N = file.read_int()
        n = N
        for i in range(1, 1000):
            not_seen -= set(map(int, list(str(n))))
            print(not_seen)
            if len(not_seen) == 0:
                return str(n)
            n += N
        return "INSOMNIA"
