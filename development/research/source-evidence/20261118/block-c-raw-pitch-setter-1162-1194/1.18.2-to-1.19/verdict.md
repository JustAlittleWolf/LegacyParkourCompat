# Frozen source audit: `Entity.turn` pitch setter, 1.18.2 → 1.19

## Verdict

The Mojmap `Entity.turn(double, double)` pitch path is unchanged between exact client releases 1.18.2 and 1.19 (the 1.19.0 release ID). In both, the input pitch delta is narrowed to `float` before multiplication by `0.15F`; the first `setXRot` receives the unclamped float sum and accepts every finite sum; the next `setXRot` clamps the stored current pitch to `[-90.0F, 90.0F]`. A finite ordinary Java argument can make the first setter receive a value above 360, but a successful `turn` does not leave that value as live pitch: the same call immediately clamps it. A local player actively fall-flying next reads the clamped live pitch in `LivingEntity.travel` (first via `getLookAngle`, then directly for the Elytra angle terms). No pitch-setter or clamp difference is found in this pair.

## Exact source trace

**1.18.2 Mojmap** — `net/minecraft/world/entity/Entity.java`:

- `turn` lines 385–396: `float $$2 = (float)$$1 * 0.15F`; `setXRot(getXRot() + $$2)`; `setYRot(getYRot() + $$3)`; then `setXRot(Mth.clamp(getXRot(), -90.0F, 90.0F))`. It subsequently adds the same pitch delta to `xRotO`, clamps `xRotO`, then calls `vehicle.onPassengerTurned(this)` if mounted.
- `setXRot` lines 3031–3037: rejects a non-finite argument with `Util.logAndPauseIfInIde(..., discarding)` and otherwise assigns `this.xRot = $$0` without normalization or range clamp.
- `getXRot` lines 3027–3029 returns that stored float.
- The searched concrete player path has no `setXRot` override in `LivingEntity`, `Player`, or `LocalPlayer`; the Entity implementation is the active setter for those classes.

**1.19 Mojmap** — `net/minecraft/world/entity/Entity.java`:

- `turn` lines 389–400 has the same casts, operation order, first setter, final clamp, old-pitch update/clamp, and vehicle callback.
- `setXRot` lines 3059–3065 has the same finite check and assignment. `getXRot` lines 3055–3057 returns the stored float.
- The corresponding `LivingEntity`, `Player`, and `LocalPlayer` sources likewise contain no `setXRot` override.

### Finite >360 witness

Start with live `xRot == 0.0F` and call `turn(0.0, 3000.0)`. Java narrows `3000.0` to `3000.0F`; the float multiplication `3000.0F * 0.15F` rounds to `450.0000305175781F`. The first setter receives this finite value (>360) and stores it. The immediately following final setter stores `Mth.clamp(450.0000305175781F, -90.0F, 90.0F) == 90.0F`. `xRotO` is independently incremented and clamped to `90.0F`. This witness establishes a transient first-setter input above 360, not a post-return live pitch above 360.

### Real next active local Elytra pitch use

For both versions, `LocalPlayer.aiStep` calls `super.aiStep` (1.18.2 `LocalPlayer.java:803`; 1.19 `LocalPlayer.java:887`). The inherited `LivingEntity.aiStep` updates fall flying and calls `travel` (1.18.2 `LivingEntity.java:2550–2552`; 1.19 `LivingEntity.java:2583–2585`). In the local controlled path, `LivingEntity.travel` is admitted by `isEffectiveAi() || isControlledByLocalInstance()` (1.18.2 `LivingEntity.java:2009–2010`; 1.19 `LivingEntity.java:2042–2043`). When `isFallFlying()` selects its Elytra branch (1.18.2 lines 2074–2085; 1.19 lines 2107–2118), it obtains `getLookAngle()` and then computes `float pitchRadians = getXRot() * (float)(Math.PI / 180.0)`. `Entity.getLookAngle()` reads live `getXRot()` and `getYRot()` before building the view vector (1.18.2 `Entity.java:1816–1818`; 1.19 `Entity.java:1841–1843`). Thus after the witnessed `turn`, the next such active local glide computation sees live pitch 90 degrees; it does not see the transient 450-degree setter input.

The same branch's pitch-sensitive vertical lift/dive calculations follow that radians conversion. This report only establishes the active local pitch read and source continuity; it makes no trajectory claim beyond those direct source operations.

## Frozen evidence identity and byte hashes

Canonical read-only roots: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.18.2\mojmap` and `...\1.19\mojmap`. The ready markers identify exact IDs `1.18.2` and `1.19`, mapping `mojmap`, status `ready`; the latter is the official version ID commonly written 1.19.0. Source manifests and artifact manifests match the SHA-256 values recorded by their ready markers. Provenance reports Vineflower 1.12.0, Java 25.0.3+9-LTS, Gradle 9.7.1, TinyRemapper 0.14.1, Mapping IO 0.9.1, ASM 9.10.1, and Gson 2.14.0.

| Exact root item | 1.18.2 SHA-256 | 1.19 SHA-256 |
|---|---|---|
| `mojmap.ready.json` bytes | `d8057d47468c37880de38d658e95070ec3b750305b7856189997604822480946` | `b00a68db8600ec8cdc3917357aff59eb0537be6f382e621e5ed3eeb900898b88` |
| `mojmap.provenance.json` bytes | `ce14d5dfc2d51cffa7b1512878d908e37c930d414147be24e69439a7f1061ca9` | `833cf50d6eab0f24475dcd11085c1414c85eee0e708ff17c59bc8dcd2ae9c35a` |
| `mojmap.sources.sha256` bytes (ready-marker value) | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` | `78bf3f9d5b4b5437240dd0707fa7a9ff56e6d1e267bd8edc13adc8313b535e26` |
| `artifacts.sha256` bytes (ready-marker value) | `a1507e4875faee892aca4c59686bbd68933db21a2274559e81eea8a32b021036` | `eee4a52f029446e075ff10ef761cea9a621b40df6e66240b6323d1596ca60ebf` |
| exact client jar (artifact manifest) | `1d09e3639644b6b2254499469d0765cc005a286d19f3fa595b0ed8fb07971ec7` | `ffd4fb3037d4110085b4d3d2dc47b760695528eb14292c3e5f7c758983c6d764` |
| Mojang client mappings (artifact manifest) | `a2aa6ee1030bfef79e9b2e08e79de1637fdd7ecb5bf8891cf2e9a4b186042543` | `9e31de7fc15b1bac0e58abc441a5e56c320266112feaea981bcffd41dd6201e1` |
| `Entity.java` source bytes (also matches source manifest entry) | `2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a` | `3a8ce3090d29aad1bbfeca31de300d34b42a7e902f0e78b13efd5653cd588766` |
| `LivingEntity.java` source bytes (also matches source manifest entry) | `db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782` | `00c591dd2cac49ee5ccad8705f7dd879ad5d29f0360b98742166294989b3531c` |
| `LocalPlayer.java` source bytes (also matches source manifest entry) | `99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095` | `c5f346728f551d278e88b457e54e539885e0d8e549ed7394887d090f28224805` |

Both provenance manifests have `mappingArtifacts: null` because this is the Mojmap profile; the exact client-mappings input hashes above are recorded in each artifact manifest. No author-provided source claim or draft material was supplied before this freeze.

## Scope boundary

This verdict covers only `Entity.turn` pitch narrowing, finite first setter, final pitch clamp, and the source-backed live pitch read in active local Elytra travel for this pair. It does not cover input-device scheduling, rendering, networking, teleportation, the full movement pair, or runtime parity.

## Phase-two independent binding (2026-11-18)

**Signed verdict: ACCEPT, qualified.** Bound to author report `c-raw-pitch-setter-1182-119-source/report.md`, whose complete literal SHA-256 was verified as `be6b987dc68b832e6e8fd309dd1542bf37d77588400bf88bd7cc2feb5cf58350`. This binding is signed by `/root/c_raw_pitch_setter_1182_119_audit` and is based on the complete independent phase-one verdict preserved above (pre-binding SHA-256 `6dbadde9a85983cdc3f2e8a2fd5f8b24c21b69503cddec0908a3142bedd64764`).

The author's claims that `Entity.turn` has the same pitch narrowing, operation order, finite first-setter behavior, and final clamp in both releases, and that active Elytra travel reads live pitch through look angle and pitch-radians math, agree with the frozen independent source trace. The author's source-file hashes for Entity and LivingEntity match the independently recorded manifest entries. The independent report additionally verified the actual local-player aiStep-to-travel path and the finite >360 witness: `turn(0.0, 3000.0)` from `xRot == 0.0F` sends finite `450.0000305175781F` to the first setter and finishes at `90.0F` after the immediate clamp.

Qualifications: the author report does not include that requested concrete >360 witness/blocker, omits the `artifacts.sha256` manifest-byte identity recorded in the independent freeze, and examines `MouseHandler` caller plumbing, which is outside the assigned boundary. Those points are excluded from the accepted source claim; they do not change the accepted direct-setter/Elytra no-difference verdict. No pair-wide coverage or runtime-parity claim is accepted.