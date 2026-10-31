# Independent candidate review: respawn jump-strength base

**Verdict: ACCEPT, subject to the adapter contract below.** Candidate commit `d2bdc1976c259f24b084dcd793d2c29a21589780` is a narrow, source-consistent hook/change candidate against neutral root `dbe4f4905f7789211de17ceadee6e83fab0bc0f7`. Review was static; no build, tests, or runtime were run.

## Checks

- The return `OptionalDouble.of(0.42F)` passes the float literal to `OptionalDouble.of(double)`, widening the already-rounded float to `0.41999998688697815`. This matches the 1.20.5/1.20.6 registered default (`Attributes.JUMP_STRENGTH`, Mojmap), rather than the nearby double literal `0.42`.
- In 1.20.5/1.20.6, `ClientPacketListener.handleRespawn` copies attributes only when `shouldKeep((byte)1)` is true; the false path leaves the replacement's default base. 1.21/1.21.1 adds the false-path `assignBaseValues`, retaining old bases but not modifiers. The inspected source hashes agree with the supplied `source-hashes.txt` inventory for these respawn and attribute slices.
- The hook is one independently resolved operation, `player.respawn.jump_strength_base`. Its `OptionalDouble` contract supports native preservation: empty means leave vanilla's result alone; the present value is only a base override. Both change classes have public no-argument constructors, the authoritative `@MovementChange` annotation, and implement the annotated hook. Candidate discovery recursively finds annotated hook interfaces and registers them from each annotated class.
- Resolver ordering makes `V1_20_2` the lower native/empty fence for profiles through 1.20.4, and `V1_20_5` the old-behavior range for 1.20.5/1.20.6. There is no 1.21 change class, so 1.21 retains native `assignBaseValues` behavior. The reviewed 1.20.4 and 1.20.5 source slices support the candidate's distinction between the pre-generic player attribute and the 1.20.5 generic jump-strength attribute; the accepted 1.20.6→1.21 finding and alias review support the respawn delta.

## Required integration contract

The product-side client respawn adapter must call this hook only on the `shouldKeep((byte)1) == false` branch and, when present, set only the replacement player's `JUMP_STRENGTH` base. The keep-attributes branch, an empty result, modifiers, and every other attribute must retain the native result. The candidate intentionally has no adapter/mixin; integration must provide the thin client dispatch and ensure it runs after the native respawn operation has selected the replacement player.

For profiles at or below 1.20.4, the candidate's lower fence returns empty. Integration should reconcile that with the older jump calculation: those versions lack the generic player jump-strength attribute, so a separate historical jump path must not accidentally depend on the modern retained base. This candidate alone does not implement that broader jump calculation.

## Review identity

- Candidate: `d2bdc1976c259f24b084dcd793d2c29a21589780`
- Neutral root: `dbe4f4905f7789211de17ceadee6e83fab0bc0f7`
- Exact source basis: read-only `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<version>/mojmap/`; Mojang mappings. See `.native-source/20261031/respawn-jump-alias61/source-hashes.txt` for the reviewed release inventory.
