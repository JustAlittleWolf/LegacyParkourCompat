# Supplement 03: immutable hash and bounds wording corrections

This correction note is additive and preserves `coverage.md`, `supplement.md`, and `supplement-02.md` unchanged. It binds the correction to the existing report snapshots by SHA-256:

| Existing artifact | SHA-256 |
|---|---|
| `supplement.md` | `6a72afe0af8fcab041ddcbe7c96598cd4a4657334181fe3a346a3e4aa690eb30` |
| `supplement-02.md` | `421fad6d7c0d9a210954c0e060da4ab2038f40895c335940a5db761931c78e63` |

## Corrected source hashes

In `supplement-02.md`'s bound evidence table, replace the following two 1.9.3 source hashes. The 1.9.2 hashes and all other rows remain as recorded there.

| Relative path | Previously recorded 1.9.3 hash (incorrect) | Correct 1.9.3 SHA-256 |
|---|---|---|
| `net/minecraft/entity/ai/pathing/WalkNodeEvaluator.java` | `893108191844cee1eceb32fbef9c245a70cad969c02c8895b17e0475436b4c85c` | `893108191844cee1eceb32fbe9c245a70cad969c02c8895b17e0475436b4c85c` |
| `net/minecraft/block/FireBlock.java` | `48b734bf3665b9c743c05c176bb1208992e357f376fecfb1b6d337dade5a2c29` | `48b734bf3665b9c743c05c176bb1208992e357f376fefcb1b6d337dade5a2c29` |

The corrected values bind the exact 1.9.3 files in the same mapped source root and manifest already identified in Supplement 02. This correction changes only transcription, not the source classification.

## Pose-query edge-boundary wording

Refine the pose-query statement in Supplement 02: the candidate player box itself does **not** need to cross the horizontal world bound. `World.getCollisions(Box)` expands the scanned integer range by one block in each axis:

```java
i = floor(shape.minX) - 1;  j = ceil(shape.maxX) + 1;
m = floor(shape.minZ) - 1;  n = ceil(shape.maxZ) + 1;
for (o = i; o < j; o++)
  for (p = m; p < n; p++) {
    q = (o != i && o != j - 1 ? 0 : 1) + (p != m && p != n - 1 ? 0 : 1);
    if (q != 2 && isChunkLoaded((o, 64, p))) {
      for (r = floor(shape.minY)-1; r < ceil(shape.maxY)+1; r++)
        if (q <= 0 || (r != floor(shape.minY)-1 && r != ceil(shape.maxY)+1-1)) {
          // set (o,r,p), then check horizontal bounds
        }
    }
  }
```

The exact source expressions are `i=floor(minX)-1`, `j=ceil(maxX)+1`, `m=floor(minZ)-1`, `n=ceil(maxZ)+1`, with exclusive loop upper bounds; similarly `k=floor(minY)-1`, `l=ceil(maxY)+1`, and `r<l`. For each x/z column, `q` is the sum of its x-edge and z-edge indicators. The column is considered only when `q != 2` and `isChunkLoaded` succeeds. Within it, y positions are considered when `q <= 0` or `r` is neither vertical endpoint (`k` nor `l-1`). The horizontal out-of-range early return occurs only after these gates and after setting the scanned position. Thus the extra one-block scan padding can encounter an out-of-range coordinate while the candidate box remains within the bound; whether a relevant padded coordinate reaches the early return also depends on the exact floor/ceil values, the `q` and y-endpoint gates, and the loaded-chunk result. The 1.9.2 versus 1.9.3 `isChunkLoaded` difference described in Supplement 02 remains the relevant gate change.

This is a wording precision only. It adds no behavior or reachability finding and makes no parity claim; the pose-query case remains unresolved as previously stated.
