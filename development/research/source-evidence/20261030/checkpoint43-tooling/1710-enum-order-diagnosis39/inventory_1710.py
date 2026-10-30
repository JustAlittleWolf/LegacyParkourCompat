import collections
import hashlib
import json
import pathlib
import struct
import zipfile


class Reader:
    def __init__(self, data):
        self.data, self.pos = data, 0

    def u1(self):
        value = self.data[self.pos]
        self.pos += 1
        return value

    def u2(self):
        value = struct.unpack_from(">H", self.data, self.pos)[0]
        self.pos += 2
        return value

    def u4(self):
        value = struct.unpack_from(">I", self.data, self.pos)[0]
        self.pos += 4
        return value

    def take(self, size):
        value = self.data[self.pos:self.pos + size]
        self.pos += size
        return value


def parse_class(data, entry):
    r = Reader(data)
    if r.u4() != 0xCAFEBABE:
        raise ValueError(f"{entry}: bad magic")
    minor, major, count = r.u2(), r.u2(), r.u2()
    cp = [None] * count
    i = 1
    while i < count:
        tag = r.u1()
        if tag == 1:
            cp[i] = (tag, r.take(r.u2()).decode("utf-8", "replace"))
        elif tag in (3, 4):
            r.take(4)
        elif tag in (5, 6):
            r.take(8)
            i += 1
        elif tag in (7, 8, 16, 19, 20):
            cp[i] = (tag, r.u2())
        elif tag in (9, 10, 11, 12, 17, 18):
            cp[i] = (tag, r.u2(), r.u2())
        elif tag == 15:
            r.take(3)
        else:
            raise ValueError(f"{entry}: unsupported CP tag {tag} at {i}")
        i += 1

    def utf(index):
        item = cp[index]
        if not item or item[0] != 1:
            raise ValueError(f"{entry}: CP {index} is not UTF8")
        return item[1]

    def class_name(index):
        return utf(cp[index][1])

    class_access, this_class, super_class = r.u2(), r.u2(), r.u2()
    interfaces = [class_name(r.u2()) for _ in range(r.u2())]
    fields = []
    for _ in range(r.u2()):
        access, name, desc, attr_count = r.u2(), utf(r.u2()), utf(r.u2()), r.u2()
        for _ in range(attr_count):
            r.u2()
            r.take(r.u4())
        fields.append({"access": access, "name": name, "desc": desc})
    methods = []
    for _ in range(r.u2()):
        access, name, desc, attr_count = r.u2(), utf(r.u2()), utf(r.u2()), r.u2()
        for _ in range(attr_count):
            r.u2()
            r.take(r.u4())
        methods.append({"access": access, "name": name, "desc": desc})
    attrs = []
    for _ in range(r.u2()):
        name, size = utf(r.u2()), r.u4()
        payload = Reader(r.take(size))
        if name == "InnerClasses":
            rows = []
            for _ in range(payload.u2()):
                inner, outer, simple, access = payload.u2(), payload.u2(), payload.u2(), payload.u2()
                rows.append({"inner": class_name(inner) if inner else None,
                             "outer": class_name(outer) if outer else None,
                             "simple": utf(simple) if simple else None,
                             "access": access})
            attrs.append({"name": name, "rows": rows})
        elif name == "EnclosingMethod":
            owner, method = payload.u2(), payload.u2()
            attrs.append({"name": name, "owner": class_name(owner),
                          "method": cp[method] if method else None})
        else:
            attrs.append({"name": name})
    if r.pos != len(data):
        raise ValueError(f"{entry}: trailing bytes {len(data) - r.pos}")
    fieldrefs = []
    for idx, item in enumerate(cp):
        if item and item[0] == 9:
            owner, nat = class_name(item[1]), cp[item[2]]
            if nat and nat[0] == 12:
                fieldrefs.append({"owner": owner, "name": utf(nat[1]), "desc": utf(nat[2])})
    return {"entry": entry, "name": class_name(this_class), "super": class_name(super_class) if super_class else None,
            "minor": minor, "major": major, "access": class_access, "interfaces": interfaces,
            "fields": fields, "methods": methods, "attrs": attrs, "fieldrefs": fieldrefs}


def main(jar, out):
    jar, out = pathlib.Path(jar), pathlib.Path(out)
    classes = {}
    with zipfile.ZipFile(jar) as z:
        names = set()
        for info in z.infolist():
            if info.filename in names:
                raise ValueError(f"duplicate path: {info.filename}")
            names.add(info.filename)
            if info.filename.endswith(".class"):
                item = parse_class(z.read(info), info.filename)
                if item["name"] + ".class" != info.filename:
                    raise ValueError(f"{info.filename}: path/internal class name mismatch ({item['name']})")
                if item["name"] in classes:
                    raise ValueError(f"duplicate internal class name: {item['name']}")
                classes[item["name"]] = item
    helpers = []
    for item in classes.values():
        required = 0x0008 | 0x0010 | 0x1000
        fields = item["fields"]
        if (item["access"] & 0x1000 and len(item["methods"]) == 1
                and item["methods"][0]["name"] == "<clinit>" and fields
                and all(field["desc"] == "[I" and field["access"] & required == required for field in fields)):
            helpers.append(item["name"])
    consumers = collections.defaultdict(set)
    for item in classes.values():
        for field in item["fieldrefs"]:
            if field["owner"] in helpers and field["desc"] == "[I":
                consumers[field["owner"]].add(item["name"])
    nested_attr_classes = [item["name"] for item in classes.values()
                           if any(attr["name"] == "InnerClasses" and attr["rows"] for attr in item["attrs"])]
    enclosing = [item["name"] for item in classes.values()
                 if any(attr["name"] == "EnclosingMethod" for attr in item["attrs"])]
    report = {
        "jar": str(jar), "jar_sha256": hashlib.sha256(jar.read_bytes()).hexdigest(),
        "archive_entries": len(names), "classes": len(classes), "class_major_versions": dict(collections.Counter(i["major"] for i in classes.values())),
        "classes_with_inner_classes_attribute": nested_attr_classes,
        "classes_with_enclosing_method_attribute": enclosing,
        "helper_candidates": [{"name": h, "access": classes[h]["access"], "methods": classes[h]["methods"],
                                "fields": classes[h]["fields"], "attrs": classes[h]["attrs"],
                                "fieldref_consumers_including_self": sorted(consumers[h])} for h in sorted(helpers)],
        "helper_candidate_count": len(helpers),
        "helper_fieldref_owner_class_pairs_total_including_self": sum(len(v) for v in consumers.values()),
    }
    out.write_text(json.dumps(report, indent=2), encoding="utf-8")
    print(json.dumps({key: value for key, value in report.items() if key not in ("classes_with_inner_classes_attribute", "classes_with_enclosing_method_attribute", "helper_candidates")}, indent=2))


if __name__ == "__main__":
    import argparse
    parser = argparse.ArgumentParser()
    parser.add_argument("jar")
    parser.add_argument("out")
    args = parser.parse_args()
    main(args.jar, args.out)
