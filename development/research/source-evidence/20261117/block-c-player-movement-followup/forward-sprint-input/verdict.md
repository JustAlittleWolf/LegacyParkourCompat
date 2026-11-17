# Forward sprint input audit verdict

Terminal: COMPLETED

Assessment: ACCEPTED WITH A SOURCE-PROVENANCE NOTE CORRECTION. The sprint gate claims are supported by canonical exact-version decompiled source. The comparison's statement that 1.13.2 and 1.14.4 Feather mapping artifact/build identities cannot be verified from the tree is inaccurate: their outer ready and provenance records name the mapping artifacts, and the outer artifact manifests record their hashes. Their hidden `.decompile-provenance.json` files are absent; that does not erase the outer provenance evidence.

## Immutable input

- `comparison.md` SHA-256: `940020412cb258795b4117eda0703836f6a7eee0ab6dc7fb06d52ff04e5b1cea` (verified).
- Source directory: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\`.

## Reviewed behavior

- **1.14.2 Yarn build.7:** `ClientPlayerEntity.java:949–952` selects `input.method_20622()` when `isInWater()` is true and otherwise compares `movementForward >= 0.8` (double literal). `Input.java:22–24` defines the selected input predicate as strict `movementForward > 1.0E-5F`. The sprint-start checks use the helper after input tick; continuation uses the positive-forward predicate directly without the water selector (`ClientPlayerEntity.java:657–675`).
- **1.14.4 Ornithe Feather build.2:** `LocalClientPlayerEntity.java:961–964` selects `input.m_49149051()` when `isSubmergedInWater()` is true and otherwise compares `movementForward >= 0.8`; `Input.java:22–24` defines the same strict positive-forward boundary. Continuation uses that positive-forward predicate independently of the start selector (`LocalClientPlayerEntity.java:679–688`).
- **Ordinary keyboard witnesses:** 1.14.2 Yarn `KeyboardInput.java:18–25` and 1.14.4 Feather `KeyboardInput.java:18–25` produce forward `+1`, back `-1`, and neutral/both `0`; eligible sneak scaling turns a single forward input into approximately `0.3F`. Thus dry selector input accepts ordinary forward and rejects neutral/back; wet selector input accepts positive forward, including the sneak-scaled witness, and rejects zero/back. These are stipulated selector inputs; this audit makes no claim about how water predicates are sensed or computed.
- **Earlier threshold check:** 1.13.2 Feather `LocalClientPlayerEntity.java:700–702, 723–749` uses inclusive `>= 0.8F` for both starts and the complementary `< 0.8F` continuation cutoff. Its keyboard mapping supplies the ordinary `+1/-1/0` values and `0.3` sneak scale (`KeyboardInput.java:13–49`). 1.14 and 1.14.1 Yarn use the same float threshold in their start/continue decisions. The compared 1.14.2+ dry threshold uses a double literal, but representable float inputs classify identically at the ordinary 0.8 boundary.

## Source identity checks

Actual SHA-256 values for all reviewed source files matched the corresponding outer source hash list (1.13.2 and 1.14.4 Feather) or hidden nested source manifest (1.14–1.14.3 Yarn). The 1.14.4 Mojmap `LocalPlayer.java` and input source hashes also match its outer source list; its name-readable helper confirms the positive-forward design, but its differently named water predicate is not used here to equate water sensing across mappings.

Mapping identities from permitted provenance records:

- 1.13.2 / Ornithe Feather: `feather-gen2-1.13.2+build.2-mergedv2.jar` and `feather-gen2-1.13.2+build.2.tiny`; listed in `ornithe-feather.provenance.json` and `ornithe-feather.ready.json`, with hashes in `artifacts.sha256`.
- 1.14 / Yarn build.21; 1.14.1 / Yarn build.10; 1.14.2 / Yarn build.7; 1.14.3 / Yarn build.13. Each nested `.decompile-provenance.json` records its corresponding Yarn input artifact and its source hashes.
- 1.14.4 / Ornithe Feather: `feather-gen2-1.14.4+build.2-mergedv2.jar` and `feather-gen2-1.14.4+build.2.tiny`; listed in outer provenance/ready records and hashed in `ornithe-feather.artifacts.sha256`.
- 1.14.4 Mojmap is supplemental only; no cross-mapping water-predicate equivalence is asserted.

## Limits

This is an independent audit of the bounded forward sprint-start/continuation input decisions and keyboard witnesses in the stated sources. It does not audit water sensing implementation, other sprint gates, movement/trajectory parity, runtime behavior, or uninspected intermediate patch versions. It does not establish exact cutover dates between the checked endpoints.