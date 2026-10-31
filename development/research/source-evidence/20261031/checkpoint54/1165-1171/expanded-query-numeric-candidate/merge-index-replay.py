import math
EPS = 1.0e-7

def indirect_merger(first, second, include_first=True, include_second=True):
    # Mirrors the index and coordinate construction in IndirectMerger.java.
    m = n = result_len = 0
    previous = math.nan
    result = []
    first_indices = []
    second_indices = []
    while True:
        first_done = m >= len(first)
        second_done = n >= len(second)
        if first_done and second_done:
            return result, first_indices, second_indices
        take_first = (not first_done) and (second_done or first[m] < second[n] + EPS)
        if take_first:
            m += 1
        else:
            n += 1
        i = m - 1
        j = n - 1
        value = first[i] if take_first else second[j]
        if not (previous >= value - EPS):
            result.append(value)
            first_indices.append(i)
            second_indices.append(j)
            result_len += 1
            previous = value
        else:
            first_indices[result_len - 1] = i
            second_indices[result_len - 1] = j

def report_case(name, provider_coords, query_coords, provider_filled, query_filled):
    coords, first_i, second_i = indirect_merger(provider_coords, query_coords)
    callbacks = []
    for k in range(max(0, len(coords) - 1)):
        a = first_i[k] in provider_filled
        b = second_i[k] in query_filled
        callbacks.append((k, first_i[k], second_i[k], a, b, a and b))
    print(name)
    print("mergedCoords=" + repr(coords))
    print("mergedIndexPairs=" + repr(list(zip(first_i, second_i))))
    print("intervalCallbacks(k,firstIndex,secondIndex,providerFull,queryFull,and)=")
    for row in callbacks:
        print(repr(row))
    print("joinIsNotEmptyAND=" + str(any(row[-1] for row in callbacks)))

provider = [-0.3749999403953552, 0.12500005960464478]
old_query = [0.12500008, 0.75000008]
new_query = [i / 8.0 for i in range(9)]
report_case("old raw ArrayVoxelShape Y", provider, old_query, {0}, {0})
report_case("new quantized CubeVoxelShape Y", provider, new_query, {0}, set(range(1, 6)))
