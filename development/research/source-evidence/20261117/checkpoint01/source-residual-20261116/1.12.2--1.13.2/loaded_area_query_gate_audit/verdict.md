# Independent source audit — loaded-area and client chunk-presence query

## Verdict

**ACCEPT** for the bounded source result: the inspected `allowEmpty=true` loaded-area and one-argument client player chunk-presence paths show no boolean-result change across 1.12.2, 1.13.0, 1.13.1, and 1.13.2. The nonempty chunk-presence predicate also remains equivalent for the described client cache representations in this slice. This verdict is bound to report SHA-256 `16573D09A81B7E8607861CB645ECBF7A3A44D87E3459021425DDDF96C2332019`. It does not establish pair freeze, movement parity, or behavior for all other chunk-availability callers.

## Identity and source hashes

All four Feather ready markers report `status=ready`, the expected version and mapping, and the source manifest SHA named in the report. In each case the on-disk sidecar SHA-256 matches that ready-marker value:

| Version | Source-manifest SHA-256 | Identity check |
|---|---|---|
| 1.12.2 | `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` | match |
| 1.13.0 | `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a` | match |
| 1.13.1 | `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba` | match |
| 1.13.2 | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` | match |

All 24 Java source hashes in the report table match both the corresponding canonical source file and its endpoint sidecar entry. The audit also directly rechecked the source methods and claims below.

## Claim checks

- 1.12.2 `World.isAreaLoaded(BlockPos,int)` and 1.13.0/1.13.1/1.13.2 `WorldView.isAreaLoaded(BlockPos,int)` each default `allowEmpty` to `true`. Their range overloads forward the same `pos ± range` bounds and flag. The endpoint helpers preserve the Y-domain guard, arithmetic X/Z chunk shifts, and inclusive chunk loops.
- The client `allowEmpty=true` checks short-circuit to true. Therefore any in-range area passes regardless of a cached chunk; a Y range wholly outside `[0,256)` remains false at the shared earlier guard. This conclusion follows from the exact guards and short-circuit expressions, without assumptions about chunk cache contents.
- For `allowEmpty=false`, 1.12.2 and 1.13.0 test whether the returned chunk is not the empty sentinel. In 1.13.0, a missing cache entry yields `EmptyChunk`, `EmptyChunk.isEmpty()` is true, and `WorldChunk.isEmpty()` is false. In 1.13.1/1.13.2, `getChunk(x,z,true,false)` returns the stored chunk or null; the client loadedness method tests non-null. The nullable refactor thus preserves the presence boolean for cached versus missing client chunks as modeled by these exact source methods.
- The one-argument `isChunkLoaded(BlockPos)` defaults to `allowEmpty=true` in all four releases. Each local client player's tick invokes that overload before `super.tick()` with the same position form. The call therefore has the same source-level gate in these releases.

## Boundary

The report correctly leaves water/fluid reachability, block providers, collision queries, ordered tick coverage, external/resource dependencies, and independent whole-pair audit open. The accepted comparison covers only the named query and caller slice; no broad chunk-availability equivalence or runtime outcome is inferred.
