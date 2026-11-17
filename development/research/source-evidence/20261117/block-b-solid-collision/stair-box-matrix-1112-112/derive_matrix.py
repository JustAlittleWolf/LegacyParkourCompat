from __future__ import annotations
import hashlib
import json
import re
from pathlib import Path

ROOT = Path(r"D:\Javastuff\LegacyParkourCompat\decompiled_minecraft")
VERSIONS = {"1.11.2": ROOT / "1.11.2" / "ornithe-feather", "1.12": ROOT / "1.12" / "ornithe-feather"}
FACES = ["NORTH", "SOUTH", "WEST", "EAST"]
HALVES = ["BOTTOM", "TOP"]
SHAPES = ["STRAIGHT", "INNER_LEFT", "INNER_RIGHT", "OUTER_LEFT", "OUTER_RIGHT"]

# Matches the source switch in getCollisionShapeForInnerStep.
def inner_dir(facing: str) -> str:
    return facing

# Direction.turnY in the source: N->E->S->W->N. counterClockwiseY is inverse.
CW = {"NORTH":"EAST", "EAST":"SOUTH", "SOUTH":"WEST", "WEST":"NORTH"}
CCW = {v:k for k,v in CW.items()}
OPP = {"NORTH":"SOUTH", "SOUTH":"NORTH", "EAST":"WEST", "WEST":"EAST"}

def outer_dir(facing: str, shape: str) -> str:
    return {"STRAIGHT": facing, "OUTER_LEFT": facing, "OUTER_RIGHT": CW[facing],
            "INNER_RIGHT": OPP[facing], "INNER_LEFT": CCW[facing]}[shape]

# Names directly encode the constants selected by the StairsBlock direction switches.
def emit(facing: str, half: str, shape: str) -> list[str]:
    suffix = "TOP" if half == "TOP" else "BOTTOM"
    out = [f"{suffix}_BASE"]
    if shape in ("STRAIGHT", "INNER_LEFT", "INNER_RIGHT"):
        out.append(f"{suffix}_{inner_dir(facing)}")
    if shape != "STRAIGHT":
        corner_by_dir = {"NORTH":"NORTH_WEST", "SOUTH":"SOUTH_EAST",
                         "WEST":"SOUTH_WEST", "EAST":"NORTH_EAST"}
        out.append(f"{suffix}_{corner_by_dir[outer_dir(facing, shape)]}")
    return out

# Source-method extraction is used only to make the read-only comparison reproducible.
def method_body(path: Path, name: str, ordinal: int = 0) -> str:
    source = path.read_text(encoding="utf-8")
    pat = re.compile(r"(?m)^\s*(?:public|private|protected)\s+(?:static\s+)?[^;{}\n]*?\b" + re.escape(name) + r"\s*\(")
    matches = list(pat.finditer(source))
    if ordinal >= len(matches):
        raise RuntimeError(f"method not found: {path}:{name}[{ordinal}] ({len(matches)} matches)")
    start = matches[ordinal].start()
    brace = source.find("{", matches[ordinal].end())
    depth = 0
    for i in range(brace, len(source)):
        if source[i] == "{": depth += 1
        elif source[i] == "}":
            depth -= 1
            if depth == 0:
                return source[start:i + 1]
    raise RuntimeError(f"unclosed body: {path}:{name}[{ordinal}]")

METHODS = [
    ("StairsBlock.java", "addCollisions", 0),
    ("StairsBlock.java", "getCollisionShapes", 0),
    ("StairsBlock.java", "getCollisionShapeForInnerStep", 0),
    ("StairsBlock.java", "getCollisionShapeForOuterStep", 0),
    ("StairsBlock.java", "resolveVirtualProperties", 0),
    ("StairsBlock.java", "getStairShape", 0),
    ("StairsBlock.java", "isStairSameHalfSameFacing", 0),
    ("StairsBlock.java", "isStair", 0),
    ("Block.java", "addCollision", 0),
    ("World.java", "getCollisions", 0),
    ("Entity.java", "move", 0),
]

print("operation source-body SHA-256 parity (raw extracted declaration and body)")
for filename, name, ordinal in METHODS:
    vals = []
    for version, root in VERSIONS.items():
        body = method_body(root / "net" / "minecraft" / ("block" if filename in ("StairsBlock.java", "Block.java") else "world" if filename == "World.java" else "entity") / filename, name, ordinal)
        vals.append(hashlib.sha256(body.encode("utf-8")).hexdigest())
    print(f"{filename}.{name}[{ordinal}]\t{vals[0]}\t{vals[1]}\t{'SAME' if vals[0] == vals[1] else 'DIFFERENT'}")

print("\n40 resolved-state collision-emission tuples (local unit coordinates; candidate order)")
print("FACING\tHALF\tRESOLVED_SHAPE\tORDERED_BOX_CONSTANTS")
for facing in FACES:
    for half in HALVES:
        for shape in SHAPES:
            print(f"{facing}\t{half}\t{shape}\t" + " -> ".join(emit(facing, half, shape)))

print("\nBox extents; tuple order is minX,minY,minZ,maxX,maxY,maxZ")
boxes = {
"BOTTOM_BASE":(0,0,0,1,.5,1), "TOP_BASE":(0,.5,0,1,1,1),
"BOTTOM_NORTH":(0,.5,0,1,1,.5), "BOTTOM_SOUTH":(0,.5,.5,1,1,1),
"BOTTOM_WEST":(0,.5,0,.5,1,1), "BOTTOM_EAST":(.5,.5,0,1,1,1),
"TOP_NORTH":(0,0,0,1,.5,.5), "TOP_SOUTH":(0,0,.5,1,.5,1),
"TOP_WEST":(0,0,0,.5,.5,1), "TOP_EAST":(.5,0,0,1,.5,1),
"BOTTOM_NORTH_WEST":(0,.5,0,.5,1,.5), "BOTTOM_NORTH_EAST":(.5,.5,0,1,1,.5),
"BOTTOM_SOUTH_WEST":(0,.5,.5,.5,1,1), "BOTTOM_SOUTH_EAST":(.5,.5,.5,1,1,1),
"TOP_NORTH_WEST":(0,0,0,.5,.5,.5), "TOP_NORTH_EAST":(.5,0,0,1,.5,.5),
"TOP_SOUTH_WEST":(0,0,.5,.5,.5,1), "TOP_SOUTH_EAST":(.5,0,.5,1,.5,1),
}
for key, val in boxes.items(): print(key + "\t" + ",".join(str(x) for x in val))
