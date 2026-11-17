# Independent source audit — door collision-shape provider r2

## Verdict

**ACCEPT** for the bounded door registration, state-to-shape, collision-dispatch, and local-player query claims. Bound to report SHA-256 `C64467D2D8F3D5E8780FC9B6BC1C2E5DE2F5AC8CAC533C6674A72E13DF9FAF34`.

## Identity and file verification

- 1.12.2 Feather ready marker and on-disk source sidecar both bind source manifest SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`.
- 1.13.2 Feather ready marker and on-disk source sidecar both bind source manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.
- All 15 Java file hashes in the revised report match both the canonical source bytes and the corresponding source sidecars. The corrected 1.12.2 `LocalClientPlayerEntity.java` hash is `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc`.

## Claim checks

- The registration and normalization evidence supports old `wooden_door` → target `oak_door`, corresponding species keys, and retained `iron_door`. The target conversion table provides the stated legacy state mappings.
- Both `DoorBlock` implementations use the same four 3/16-thick axis-aligned panel shapes and the same facing/open/hinge selector for matching effective inputs. The old getter resolves the companion half virtually; the target reads state-local properties and its update path synchronizes/removes paired halves. The report keeps the paired-state history question open.
- The cited base collision dispatchers return the registered door collision shape. The old/new `LocalClientPlayerEntity.move` overrides call `super.move`, so the described ordinary player movement route reaches the block collision provider. This verifies reachability of the provider, not equivalence of the full resolver or arbitrary paired-door histories.

## Boundary

The revised report resolves the prior hash defect. Acceptance is limited to the stated provider slice. Door placement behavior, every possible two-half state history, full query/provider composition, intermediate release boundaries, tick/external dependencies, and full-pair audit remain open. No movement difference or pair freeze is established.
