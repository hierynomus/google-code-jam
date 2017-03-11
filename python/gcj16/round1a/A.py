from gcj.gcj import GCJProblem

class A(GCJProblem):
    def solve_one(self, file):
        cs = file.read_chars()
        last_word = cs[0]
        for c in cs[1:]:
            if last_word[0] <= c:
                last_word = c + last_word
            else:
                last_word += c

        return last_word
