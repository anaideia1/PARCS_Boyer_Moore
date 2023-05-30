from Pyro4 import expose


class Solver:
    def __init__(self, workers=None, input_file_name=None,
                 output_file_name=None):
        self.input_file_name = input_file_name
        self.output_file_name = output_file_name
        self.workers = workers
        print("Inited")

    def solve(self):
        print("Job Started")
        print("Workers %d" % len(self.workers))

        text = self.read_input()
        pattern = 'zxc'

        num_workers = len(self.workers)
        step = len(text) // num_workers
        remainder = len(text) - num_workers * step

        mapped = []
        for i in range(num_workers):
            overlap = len(pattern) if i == num_workers - 1 else remainder
            print("map %d" % i)
            mapped.append(
                self.workers[i].mymap(
                    text[i * step: (i + 1) * step + overlap], pattern
                )
            )

        res = self.myreduce(mapped)

        self.write_output(res)

        print("Job Finished")

    @staticmethod
    @expose
    def mymap(text, pattern):
        count = Solver.search(text, pattern)

        return count

    @staticmethod
    @expose
    def myreduce(mapped):
        output = 0

        for result in mapped:
            output += result.value

        return output

    def read_input(self):
        file = open(self.input_file_name, 'r')
        data = file.read()
        file.close()
        return data

    def write_output(self, output):
        file = open(self.output_file_name, 'w')
        file.write(str(output))
        file.close()
        print("output done")

    @staticmethod
    def forming_d(pattern):
        sigh_table = set()
        len_pat = len(pattern)
        d = {}

        for i in range(len_pat - 2, -1, -1):
            if pattern[i] not in sigh_table:
                d[pattern[i]] = len_pat - i - 1
                sigh_table.add(pattern[i])

        if pattern[len_pat - 1] not in sigh_table:
            d[pattern[len_pat - 1]] = len_pat

        d['*'] = len_pat

        return d

    @staticmethod
    def search(text, pattern):
        counter = 0
        d = Solver.forming_d(pattern)
        len_pat = len(pattern)

        x, j, k = len_pat, len_pat, len_pat
        while x <= len(text) and j > 0:
            if pattern[j - 1] == text[k - 1]:
                j -= 1
                k -= 1
                if j == 0:
                    x += d.get(text[k - 1], d.get('*'))
                    k = x
                    j = len_pat
                    counter += 1
            else:
                x += d.get(text[k - 1], d.get('*'))
                k = x
                j = len_pat

        return counter
