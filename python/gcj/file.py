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

    def read_chars(self, strip=True):
        return list(self.read_line(strip))

    def read_int(self):
        return int(self.read_line())

    def read_ints(self):
        return [int(z) for z in self.read_line().split()]

    def read_float(self):
        return float(self.file.read_line())

    def read_floats(self):
        return [float(z) for z in self.read_line().split()]

    def read_words(self):
        return self.read_line().split()

    def read_decimal(self):
        return [Decimal(d) for d in self.read_line().split()]

    def read_line(self, strip=True):
        if strip:
            return self.file.readline().strip()
        else:
            return self.file.readline()

    def read_grid(self, rows, read_func):
        return [read_func() for x in range(rows)]

    def skip_lines(self, n=1):
        for x in range(n):
            self.read_line()


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
