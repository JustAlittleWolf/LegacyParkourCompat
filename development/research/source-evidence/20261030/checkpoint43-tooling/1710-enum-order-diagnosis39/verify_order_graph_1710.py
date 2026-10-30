import heapq
import json
import pathlib
import re
import subprocess
import zipfile


def main(jar_path, inventory_path):
    jar_path = pathlib.Path(jar_path)
    inventory = json.loads(pathlib.Path(inventory_path).read_text(encoding="utf-8"))
    with zipfile.ZipFile(jar_path) as archive:
        class_names = [item.filename[:-6] for item in archive.infolist() if item.filename.endswith(".class")]

    position = {name: index for index, name in enumerate(class_names)}
    if len(position) != len(class_names):
        raise ValueError("duplicate class paths/internal names in mapped archive")
    outgoing = {name: set() for name in class_names}
    indegree = dict.fromkeys(class_names, 0)
    edges = []

    for helper in inventory["helper_candidates"]:
        helper_name = helper["name"]
        consumers = [name for name in helper["fieldref_consumers_including_self"] if name != helper_name]
        confirmed = []
        for consumer in consumers:
            result = subprocess.run(["javap", "-classpath", str(jar_path), "-c", "-p", consumer],
                                    capture_output=True, text=True, check=True)
            pattern = re.compile(r"getstatic\s+#\d+\s+// Field " + re.escape(helper_name) + r"\.[^:]+:\[I")
            count = sum(bool(pattern.search(line)) for line in result.stdout.splitlines())
            if count:
                confirmed.append((consumer, count))
                if consumer not in outgoing[helper_name]:
                    outgoing[helper_name].add(consumer)
                    indegree[consumer] += 1
                    edges.append((helper_name, consumer, count))
        if len(confirmed) != 1:
            raise ValueError(f"{helper_name}: expected one direct external consumer root, found {confirmed}")

    ready = [(position[name], name) for name, degree in indegree.items() if degree == 0]
    heapq.heapify(ready)
    ordered = []
    while ready:
        _, name = heapq.heappop(ready)
        ordered.append(name)
        for dependent in outgoing[name]:
            indegree[dependent] -= 1
            if indegree[dependent] == 0:
                heapq.heappush(ready, (position[dependent], dependent))
    if len(ordered) != len(class_names):
        raise ValueError(f"cycle or missing class node: sorted {len(ordered)} of {len(class_names)}")
    reordered_position = {name: index for index, name in enumerate(ordered)}
    violations = [(helper, consumer) for helper, consumer, _ in edges
                  if reordered_position[helper] >= reordered_position[consumer]]
    if violations:
        raise ValueError(f"topological order left violations: {violations}")

    print(f"classes={len(class_names)} helpers={len(inventory['helper_candidates'])} "
          f"edges={len(edges)} sites={sum(sites for _, _, sites in edges)} cycles=false "
          f"reversed_before={sum(position[helper] > position[consumer] for helper, consumer, _ in edges)} "
          f"violations_after={len(violations)}")
    for helper, consumer, sites in edges:
        print(f"{position[helper]} {helper} -> {position[consumer]} {consumer}; "
              f"GETSTATIC_sites={sites}; reordered={reordered_position[helper]}<{reordered_position[consumer]}")


if __name__ == "__main__":
    import argparse
    parser = argparse.ArgumentParser()
    parser.add_argument("jar")
    parser.add_argument("inventory")
    args = parser.parse_args()
    main(args.jar, args.inventory)
