# Independent correction audit: 1.10.1 → 1.10.2

**Verdict: ACCEPT** for the narrow coverage correction. The corrected scope description is supported. The exact-pair full-coverage conclusion may be accepted together with the unchanged full-pair audit; this verdict does not reassess unrelated inventory findings or runtime parity.

## Bound inputs

- Corrected coverage: `build/source-early-patches-20261117/1.10.1--1.10.2/coverage-r2.md`, SHA-256 `77A4D5359FE2B6159643B35B996DACC4553DF2E4D332286FD6650AF22EEA06EC`.
- Original coverage: `build/source-early-patches-20261117/1.10.1--1.10.2/coverage.md`, SHA-256 `A25A3B142686C31702CDED51F9AB192C06CF8F30BFAC54B890EC0F0D5DFEF71A`.
- Prior full-pair audit: `build/source-early-patches-20261117/1.10.1--1.10.2/audit/full-pair-verdict.md`, SHA-256 `F5F85EBB0DE8B41D84C0F435DF6E18D69F7EF517E02518D3C55EB67FAA11E43E`.

## Narrow verification

The corrected report accurately records that `PathNavigation` constructs `WorldRegion` in `findPath` at lines 90 and 112 in both 1.10.1 and 1.10.2. Exact `PathNavigation.java` bytes are identical in the pair, SHA-256 `71E45321F485734CA9F313B1E0CC414F70B06CC4095054F6F7D1B233E94B856F`; the 1.10.1 provenance entry and 1.10.2 source-manifest entry match this digest. `MobEntity.createNavigation` returns `GroundPathNavigation` in both releases; `MobEntity.java` is likewise byte-identical, SHA-256 `8DB17EDED70F554728E1E7D201FA61A05230B22176942EF32F362B8E52C134AA`. The exact source inventory finds PathNavigation construction only through MobEntity and its mob subclasses. `PlayerEntity` extends `LivingEntity`, not `MobEntity`, and the correction properly classifies this extra `WorldRegion` use as non-player mob pathfinding, outside the local-player movement scope.

The correction changes no substantive movement disposition. The prior full-pair verdict already verified exact publication identities and complete 1,845-path inventories, reviewed the 21 changed paths, and supported the no-player-movement conclusion. It identified this caller/scope wording as its sole required coverage correction. No neighboring patch-edge acceptance, implementation status, or runtime parity is inferred.

## Pair disposition

The specific REQUEST CHANGES gate in the prior full-pair audit is resolved. Together, the complete unchanged audit and this accepted correction provide independent coverage acceptance for **1.10.1→1.10.2**. The coordinator/recordkeeper may record the exact-pair source freeze; adjacent edges remain separate.
