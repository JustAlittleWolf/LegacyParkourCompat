"""Print the first positional differences between two LPRC recordings."""

import argparse
import math
import struct
from pathlib import Path


def read_recording(path: Path):
    data = path.read_bytes()
    if len(data) < 44 or data[:4] != b"LPRC":
        raise ValueError(f"Invalid LPRC file: {path}")
    version, _, start_x, start_y, start_z, yaw, pitch, count = struct.unpack_from(
        ">HHdddffI", data, 4
    )
    if version != 1 or len(data) != 44 + count * 34:
        raise ValueError(f"Unsupported or truncated LPRC file: {path}")
    frames = [struct.unpack_from(">Hffddd", data, 44 + tick * 34) for tick in range(count)]
    return (start_x, start_y, start_z, yaw, pitch), frames


def ulps(a: float, b: float):
    if not math.isfinite(a) or not math.isfinite(b):
        return None
    if a == b == 0:
        return 0

    def ordered(value):
        bits = struct.unpack(">Q", struct.pack(">d", value))[0]
        return (~bits & ((1 << 64) - 1)) if bits >> 63 else bits ^ (1 << 63)

    return abs(ordered(a) - ordered(b))


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("expected", type=Path)
    parser.add_argument("actual", type=Path)
    parser.add_argument("--limit", type=int, default=20)
    args = parser.parse_args()
    expected_start, expected = read_recording(args.expected)
    actual_start, actual = read_recording(args.actual)
    print(f"start expected={expected_start} actual={actual_start}")
    print(f"ticks expected={len(expected)} actual={len(actual)}")
    shown = 0
    maximum = [0, 0, 0]
    for tick, (left, right) in enumerate(zip(expected, actual)):
        distances = tuple(ulps(left[i], right[i]) for i in (3, 4, 5))
        for axis, distance in enumerate(distances):
            if distance is not None:
                maximum[axis] = max(maximum[axis], distance)
        if any(distance != 0 for distance in distances):
            if shown < args.limit:
                print(f"tick={tick} input={left[:3]} expected={left[3:]} actual={right[3:]} ulps={distances}")
                shown += 1
    print(f"max_ulps_xyz={tuple(maximum)}")


if __name__ == "__main__":
    main()
