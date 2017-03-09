from abc import ABCMeta, abstractmethod
from gcj.file import InFileWrapper, OutFileWrapper

class GCJProblem(metaclass=ABCMeta):
    @abstractmethod
    def solve_one(self, file):
        pass

    def solve(self, inpath, outpath):
        with InFileWrapper(inpath) as f, OutFileWrapper(outpath) as of:
            T = f.read_int()
            print("Running [{}] Cases".format(T))

            for t in range(1, T + 1):
                res = self.solve_one(f)
                of.write_case(t)
                of.write_string(res)
                of.write_case_end()
