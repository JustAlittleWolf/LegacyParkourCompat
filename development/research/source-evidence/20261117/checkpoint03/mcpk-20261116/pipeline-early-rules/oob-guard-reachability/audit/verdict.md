# Independent source audit verdict

**Verdict: ACCEPT**

**Reviewed packet SHA-256:** `66a23d1d33e756f1876cc2779b90bb2b25662f76f91798666791f22fd545dd8b`

## Findings

The packet's core reachability conclusion is correct for the ordinary client pose query:

- In 1.9, 1.9.1, and 1.9.2, `World.isChunkLoaded(BlockPos)` forwards `allowEmpty=true`; the two-argument overload checks `contains(pos)` before delegating to `isChunkLoadedAt`. The `getCollisions(Box)` scan probes `(x,64,z)` before entering the Y loop, and `contains` rejects any X/Z outside `[-30000000,30000000)`. Thus the later X/Z guard cannot be reached for an OOB scan cell on this path.
- In 1.9.3 and 1.9.4, the two-argument overload delegates directly to `isChunkLoadedAt` and no longer calls `contains`. `ClientWorld.isChunkLoadedAt` returns `allowEmpty || !getChunkSource().getChunk(...).isEmpty()`. Since the scan's one-argument call supplies `true`, the loaded gate admits OOB cells. For a non-corner X/Z cell with an eligible Y iteration, the guard returns `true` before `getBlockState`.
- Across all five client classes, `ClientWorld` has no override of `contains(BlockPos)` or `isChunkLoaded(BlockPos[, boolean])`; the relevant override is `isChunkLoadedAt`.
- `PlayerEntity.updatePlayerPose` makes the candidate box, queries `this.world.getCollisions(box)`, and changes size only when the result is false. The local-client route constructs `LocalClientPlayerEntity` with the `ClientWorld` supplied to `Minecraft.setWorld`, and its constructor forwards that same `World` reference to `PlayerEntity`.

I checked the source bodies and method order in all five exact Feather mapping roots. The six cited Java file hashes for 1.9–1.9.3 match each root's `.decompile-provenance.json` source map; all six for 1.9.4 match `ornithe-feather.sources.sha256`. The client and Feather mapping artifact hashes cited for 1.9–1.9.3 match each provenance input map, including release metadata and Feather version metadata bindings. For 1.9.4, the ready marker's source-manifest/artifact-manifest/client hashes match the local manifests and artifact entries; all six source hashes match. Its verification record reports identical source files and unchanged raw input artifacts.

No correction is required. Acceptance is limited to the packet's stated OOB collision-guard reachability and client pose-query route; it does not establish pair coverage or runtime parity.
