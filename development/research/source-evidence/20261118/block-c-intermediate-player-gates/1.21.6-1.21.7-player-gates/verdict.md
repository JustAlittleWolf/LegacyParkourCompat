# Independent audit: 1.21.6 → 1.21.7 player-state and locomotion gates

## Verdict

**Accepted, bounded: no source difference in the reported 1.21.6→1.21.7 slice.** Acceptance covers the compared LocalPlayer input/sprint/item-slowdown/crouch gates, Player pose selection, and LivingEntity jump-power/effect gates only. It does not establish complete pair coverage or runtime parity.

## Immutable input

- Audited report: `C:\Users\Wolfi\.codex\visualizations\2026\11\17\01a1eb7b-b9ff-7a20-8686-0295dde20f26\c-queue-1216-1217-player-gates\report.md`
- Input SHA-256: `b5bcbbcf87fbfb3fe3306d94353973c8530f62186e58481b2cb208a40db1599c`
- Exact source roots: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.21.6\mojmap\` and `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.21.7\mojmap\`.
- Canonical provenance files: each root's `.decompile-provenance.json`. Their SHA-256 values are respectively `56b036c61094d1d936882bdf5b2b59044512189cb33adbb075880c727da9b19e` and `45bf296d921b2239fd0aedb2684e2c1cb9b7d7532b4106b8a2fd9b09309ca82d`, matching the report. Parsed manifest inputs confirm release metadata `1.21.6.json` / `1.21.7.json`, Mojang `client.txt` mappings (hashes `2ff82b34ce5f849aaafdff51e23b9448c4263881238b780a441fe1061a6fc261` / `328f2c5849c7c5fc4f2b1eae4f74a7cfe6bb337c7938242ac5c4f9eac89b1e50`), and client JAR hashes `9592df8587d6d3ec0334c873ad432dc76006d6129afdce9a7eac52638339a24a` / `6d0aa64c6a0aee3270253317f51e9e816f94cd3b16b44f5d605615c39fc62fb9`.
- Each of the eight compared Java files named in the report has the same byte-level SHA-256 in both trees, and each hash agrees with its respective provenance `sources` entry: `LocalPlayer.java`, `ClientInput.java`, `KeyboardInput.java`, `Input.java`, `Player.java`, `LivingEntity.java`, `ClientPacketListener.java`, and `MultiPlayerGameMode.java`. The reviewed method bodies were inspected at the cited locations.

## Gate and ordering audit

The reviewed 1.21.6 bodies and byte-identical 1.21.7 counterparts support the report's no-difference disposition:

- `net/minecraft/client/player/LocalPlayer.java:693-702`: jump, shift, and forward are captured; local crouching is computed from current shift/pose-fit state; only then does `input.tick()` resample keys. Downstream sprint logic therefore mixes the captured `$$1`/`$$2` values with post-`input.tick()` reads: the backward-key reset at `:718`, the sprint-key check at `:731`, and `canStartSprinting()`'s forward predicate at `:1065-1074` read the refreshed input. The report's gate summary is substantively correct; this mixed freshness is the relevant operation-order detail.
- `LocalPlayer.java:718-743, 837-852, 1065-1075`: trigger reset, double-tap/start path and run/swim continuation predicates are unchanged. In particular item-use blocks sprint start (`:1069`) and clears the trigger only while not a passenger (`:718`); it is not a run/swim continuation stop condition. The separate sprint-key start branch is inside `canStartSprinting()` (`:722-733`).
- `LocalPlayer.java:197-205`: `super.tick()` runs before the client sends a changed `input.keyPresses` record and updates `lastSentInput`. This is packet snapshot transport after the superclass tick. It must not be read as evidence that packet transmission advances, delays, or defines the local movement sample; the local sample update/order is the `aiStep`/`input.tick()` path above. `Input.java:6-39` is the packet record/codec; it is not itself a movement-timing change.
- `LocalPlayer.java:608-639` confirms the nonzero-move guard and scaling order: `0.98F`, conditional `0.2F` for item use outside a vehicle, then sneaking attribute scaling, followed by square-movement adjustment. `:593-605` exposes shift from input but crouching from the stored flag.
- `Player.java:459-491` confirms pose-fit guard and selection order (desired pose, crouching fallback, swimming fallback); desired pose priority is sleeping, swimming, fall-flying, spin attack, then shift crouching versus standing.
- `LivingEntity.java:2192-2217` confirms jump strength × multiplier × block factor plus Jump Boost `0.1F * (amplifier + 1.0F)`, followed by the `<= 1.0E-5F` gate, max-with-existing-Y velocity, sprint impulse, and `hasImpulse` write.

The report's adjacent-version note is properly bounded as a packet/input-snapshot transport and respawn-constructor transition in the inspected 1.21.5→1.21.6 interval. The separate shift packet versus full Input packet, and the `lastSentInput`/respawn state shape, do not alone prove a change to when local movement consumes keyboard input. Do not promote that transport boundary into a movement-timing finding without separate evidence from the local tick/input call graph. Its stated 1.21.6→1.21.7 no-difference claim is unaffected.

## Limits

This is an independent audit of the supplied immutable report and exact Mojmap source bodies for the listed operations. It does not audit collision shapes, environmental contacts, the whole reachable tick graph, unrelated 1.21.5→1.21.8 changes, or intermediate patch boundaries beyond those explicitly checked in the report. No product files, Git history, tests, launches, network access, or source generation were used.