import os
import getopt
import sys
import importlib

def main():
    validate = False
    opts, args = getopt.gnu_getopt(sys.argv[1:], "v", [])
    if ("-v", '') in opts:
        validate = True

    parts = args[0].split('/')
    name = parts[-1]
    mod = importlib.import_module(args[0].replace('/', '.'))
    gcj_class = getattr(mod, name)
    problem = gcj_class()

    inpath = args[1]
    if len(args) > 2:
        outpath = args[1]
    else:
        outpath = os.path.splitext(inpath)[0] + ".out"

    problem.solve(inpath, outpath)

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

if __name__ == "__main__":
    main()
