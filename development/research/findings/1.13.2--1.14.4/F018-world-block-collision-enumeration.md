# F018: World block-collision enumeration

1.13.2, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/world/WorldView.java`, `getBlockCollisions`, lines 112–151.
1.14.4, ornithe-feather: `1.14.4/ornithe-feather/net/minecraft/world/WorldView.java`, `getBlockCollisions`, lines 133–187.

Difference: Both movement paths request block collision shapes through the paired world-view query, with 1.13.2's `World` adding entity obstacles and 1.14.4's default query concatenating block and entity collision streams. The block enumerators differ. 1.13.2 computes integer bounds as floor(min)-1 through ceil(max)+1, iterates the upper endpoint exclusively, rejects selected outer-corner positions, checks `isChunkLoaded`, and combines full-block results in an `IntVoxelShape`; it also handles the world border through a `checkWorldBorder` flag. 1.14.4 computes bounds using a `1.0E-7` adjustment, and its traversal helper visits both endpoints inclusively. It checks chunk availability through `getChunk(..., getChunkStatusForCollisions(), false)`, filters some positions using `hasLargeCollisionShape` / moving-block flags, obtains the collision context from the queried entity, and emits intersecting block shapes individually. It first tests whether the entity's own shape crosses the world border and may emit the border shape. At exact integral query bounds, the 1.14.4 enumerator's adjusted inclusive bounds can visit an additional outer cell compared with 1.13.2. The 1.14.4 query also has explicit per-block flags and collision context, while 1.13.2 has perimeter skipping, full-block aggregation, and a separate border mode. These are source-level algorithm changes; they do not by themselves establish that a common-era block returns a differing intersecting obstacle for a player movement query.

Movement meaning: The path is reachable from local player collision resolution and the post-move auto-jump scan.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Current annotated Change/interface/catalog inventory contains no operation implementing: F018: World block-collision enumeration. Existing unrelated later operations do not establish coverage."
    ]
  },
  "applicability": [
    "Endpoint interval (1.13.2, 1.14.4]; exact cutover remains the original evidence’s separate applicability gate."
  ],
  "sources": [
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather",
      "path": "1.13.2/ornithe-feather/net/minecraft/world/WorldView.java",
      "member": "getBlockCollisions",
      "lines": [
        [
          112,
          151
        ]
      ],
      "status": "verified-content",
      "sha256": "9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f",
      "detail": "Canonical source read. Matches digest in archived original."
    },
    {
      "version": "1.14.4",
      "mapping": "ornithe-feather",
      "path": "1.14.4/ornithe-feather/net/minecraft/world/WorldView.java",
      "member": "getBlockCollisions",
      "lines": [
        [
          133,
          187
        ]
      ],
      "status": "verified-content",
      "sha256": "fcdf8dc98f97781f4639c4b8cda8a238edf834f3963e401e6eeef6cb475c4909",
      "detail": "Canonical source read. Matches digest in archived original."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
