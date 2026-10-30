# Independent static integration review: profile 1.8.1 split

Reviewed product commit: `2b485d86` (`fix: split Minecraft 1.8.1 movement profile`), against `69016625`.

## Verdict

**CHANGES REQUIRED** for the old movement-speed change's authoritative version. The enum/profile split, IDs, chronological position, UI labels, generic parsing, and End Portal Frame profile gate are internally consistent on static inspection. No build, tests, game launch, source generation, or runtime parity check was performed.

## Required correction

`src/main/java/me/wolfii/legacyparkourcompat/change/v1_8/UncappedPlayerMovementSpeed.java` remains annotated `@MovementChange(emulates = ParkourVersion.V1_8)`. `ChangeResolver` only considers changes with `emulates >= selected`; therefore for selected `V1_8_1` through `V1_8_4`, this old uncapped implementation is excluded. The 1.8.5-and-later native cap behavior then wins, incorrectly dropping the uncapped behavior for the whole new 1.8.1–1.8.4 group.

Exact Feather source confirms the accepted boundary and that moving the authoritative version preserves behavior without changing the calculation: 1.8.0 and 1.8.1 declare movement speed with maximum `Double.MAX_VALUE`; 1.8.1's `ModifiableEntityAttributeInstance.computeValue()` retains the same operation 0, operation 1 relative to the operation-0 result, operation 2, then attribute-clamp order as 1.8.0. The 1.8.2–1.8.4 source closure in `development/research/source-evidence/20261028/early-movement-speed/mapped-source.md` records the same uncapped declaration and byte-identical relevant calculation sources. Exact 1.8.5 declares the movement-speed maximum as `1024.0`. This is the already accepted 1.8.4→1.8.5 boundary; no new physics conclusion is made here.

Minimal fix: move the existing class into `change/v1_8_1/`, change only its annotation to `@MovementChange(emulates = ParkourVersion.V1_8_1)`, and correct its stale source comment from “bounded to the V1_8 alias” to the 1.8.1–1.8.4 source boundary. Leave the math and gates untouched. The resolver will still use this change for selected `V1_8` as the closest later-applicable implementation, preserving 1.8 behavior; for selected 1.8.1–1.8.4 it will now remain active; selected 1.8.5+ will use the native capped behavior. This follows the existing per-operation resolution contract and needs no resolver refactor.

## Other reviewed profile wiring

- `ParkourVersion` inserts `V1_8_1` between `V1_8` and `V1_8_5`, with exactly `1.8.1`–`1.8.4`. Existing `of`/`byPatch`, `selectable`, `id`, `patches`, `displayLabel`, ordinal comparisons, and `next()` are data-driven; the new alias maps IDs to `V1_8_1`, displays `1.8.1-1.8.4`, and preserves chronological resolution.
- The client config builds its version list from enum values, formats via `displayLabel()`, and matches a release against `id()` or `patches()`. Handshake/config paths use `ParkourVersion.id()` and the client/server parsing uses `ParkourVersion.of()`, so canonical profile ID `1.8.1` and all patch IDs round-trip through existing generic paths.
- The sole prior `target == V1_8` check in the reviewed product tree was the End Portal Frame suffocation probe. The commit adds `V1_8_1` to the same explicit old-profile gate; that is the consistent update for the newly separated 1.8.1–1.8.4 alias. The code continues to retain its native fallback.
- `HistoricalRideables` uses `V1_8` as an introduction threshold for rideables already present by 1.8; keeping that earliest-era threshold is correct and should not be changed merely because the alias was split.

## Limits

Static integration review only. The speed source check inspected exact read-only `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft` Feather sources for 1.8, 1.8.1 and 1.8.5, and reused the cited accepted 1.8.2–1.8.4 source closure. No runtime parity claim is made.
