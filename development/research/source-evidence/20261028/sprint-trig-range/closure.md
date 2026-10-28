# Sprint-jump trig applicability closure (source only)

## Scope and result

Assignment: determine whether the sprint-jump yaw-trigonometry behavior applies unchanged across releases 1.20.5 through 1.21.10, using only canonical decompiled Mojmap sources. Checked all 13 release directories present in that interval: 1.20.5, 1.20.6, 1.21, 1.21.1, 1.21.2, 1.21.3, 1.21.4, 1.21.5, 1.21.6, 1.21.7, 1.21.8, 1.21.9, and 1.21.10.

Source conclusion: the sprint-gated horizontal impulse formula and its trigonometric helper behavior are text-identical in every directory. It is applicable at both interval endpoints and every listed intermediate directory. No trig formula cutover is evidenced within this interval. This is a source conclusion only; no trajectory was measured.

## Exact operation and gates

`net/minecraft/world/entity/LivingEntity.java`, `jumpFromGround`, contains the same control-flow and trig expressions across the interval:

1. Read `float jumpPower = getJumpPower()`.
2. Continue only when `!(jumpPower <= 1.0E-5F)`.
3. Read current `Vec3` movement and write horizontal components unchanged with a new jump Y. Through 1.21.1 that Y is `jumpPower`; starting in 1.21.2 it is `Math.max(jumpPower, priorY)`.
4. Only then, if `isSprinting()`, compute `float angle = getYRot() * (float)(Math.PI / 180.0)` and add `new Vec3(-Mth.sin(angle) * 0.2, 0.0, Mth.cos(angle) * 0.2)`.
5. Set `hasImpulse = true`.

Thus the formula's own eligibility is nested under successful jump-power gate and sprint state. The expression preserves float radians conversion/multiplication and float `Mth` return values before multiplication by the double literal `0.2`; the constructed horizontal vector is added after the vertical write. `Entity.addDeltaMovement(Vec3)` is text-identical in all 13 directories and delegates to `setDeltaMovement(getDeltaMovement().add(vector))`.

`net/minecraft/util/Mth.java` has identical sine-table initialization and `sin(float)`/`cos(float)` methods throughout the 13 directories: the 65,536-entry float table is filled with `(float)Math.sin(index * Math.PI * 2.0 / 65536.0)`; `sin` indexes with `(int)(radians * 10430.378F) & 65535`; `cos` adds `16384.0F` before the same cast/mask. The exact `SIN_SCALE`-through-`cos(float)` snippet is identical in all 13 releases (SHA-256 `d5576f4c45b409993f6155ee776885199fda327dfca90d7bd9b02fa1759e2ba8`).

## Per-release evidence

The `LivingEntity.java` digest is the SHA-256 of the complete file. `manifest matched` means the digest matched that release's `mojmap.sources.sha256`; the release directory had the ready marker, provenance file, and source manifest. The other five rows lack the root ready marker and complete source/artifact manifests, but hidden `mojmap/.decompile-provenance.json` and pinned cache metadata bind their version, client and mappings inputs; the provenance source digest matches the inspected `LivingEntity.java`. See `identity-followup.md` for exact pin hashes. These five are provenance-recorded identities, distinct from ready-marker/full-manifest-sealed rows.

| Release | `jumpFromGround` line | Full `LivingEntity.java` SHA-256 | Canonical identity evidence |
|---|---:|---|---|
| 1.20.5 | 2069 | `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` | ready/provenance/source manifest; manifest matched |
| 1.20.6 | 2069 | `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` | ready/provenance/source manifest; manifest matched |
| 1.21 | 2056 | `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8` | hidden provenance record; no root ready/full manifests |
| 1.21.1 | 2056 | `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8` | ready/provenance/source manifest; manifest matched |
| 1.21.2 | 2138 | `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52` | hidden provenance record; no root ready/full manifests |
| 1.21.3 | 2138 | `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52` | ready/provenance/source manifest; manifest matched |
| 1.21.4 | 2149 | `e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30` | ready/provenance/source manifest; manifest matched |
| 1.21.5 | 2173 | `a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271` | ready/provenance/source manifest; manifest matched |
| 1.21.6 | 2205 | `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8` | hidden provenance record; no root ready/full manifests |
| 1.21.7 | 2205 | `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8` | hidden provenance record; no root ready/full manifests |
| 1.21.8 | 2205 | `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8` | ready/provenance/source manifest; manifest matched |
| 1.21.9 | 2235 | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` | hidden provenance record; no root ready/full manifests; hidden `.mojmap.staging-7509591801405925509` directory also present |
| 1.21.10 | 2235 | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` | ready/provenance/source manifest; manifest matched |

For the eight metadata-backed release directories, each ready marker's `versionMetadataId` matched the directory release ID; each ready marker's full source-manifest and artifact-manifest hashes matched the corresponding files; and each complete `LivingEntity.java` digest matched its source manifest. Mojmap namespace is recorded for each. The 1.20.5 provenance records the official Mojang mapping artifact SHA-256 `9afdb3b7fcc4c6acbec3bc6ccb46192bfc6460e12bf9e268e29bfee46bc7fbac`. Other ready/provenance files identify the `mojmap` mapping option/namespace but do not record a mapping-artifact digest.

The five rows 1.21, 1.21.2, 1.21.6, 1.21.7, and 1.21.9 have hidden provenance and pinned cache inputs verified in `identity-followup.md`; all five provenance source digests match their `LivingEntity.java` files. They lack root ready markers and full source/artifact manifests, so retain the distinction between provenance-recorded identity and ready-marker/full-manifest-sealed identity. The 1.21.9 hidden staging directory is not used; the completed `mojmap` provenance suffices. The formula is text-identical in every supplied directory.

## Call path and adjacent change

Every listed `LocalPlayer.java` has a direct call to `this.jumpFromGround()` in its grounded creative-flight toggle path; this is separate from ordinary jump dispatch, which reaches the inherited `LivingEntity.aiStep` jump block. The line numbers are 734 (1.20.5–1.20.6), 731 (1.21–1.21.1), 725 (1.21.2–1.21.3), 739 (1.21.4), 760 (1.21.5–1.21.8), and 764 (1.21.9–1.21.10). In 1.20.5–1.21.1, `Player.jumpFromGround()` delegates to `super`; starting in 1.21.2 the generic `Player` override is absent and `ServerPlayer.jumpFromGround()` delegates to `super`. The base method's visibility changes from `protected` through 1.20.6 to `public` at 1.21, and receives `@VisibleForTesting`; neither affects the horizontal formula.

Adjacent non-trig difference: the vertical write changes at 1.21.2 from replacing Y with jump power to taking `Math.max(jumpPower, priorY)`. That changes surrounding jump semantics and is an independent behavior; it does not change the sprint trig term, its gate, or its order relative to the Y write.

## Bounded endpoints and limitations

- Formula applicability supported by source text: 1.20.5 through 1.21.10 inclusive, at all 13 available release directories.
- Ready-marker/full-manifest-sealed source identities: 1.20.5, 1.20.6, 1.21.1, 1.21.3, 1.21.4, 1.21.5, 1.21.8, 1.21.10.
- Provenance-recorded identities without root ready/full manifests: 1.21, 1.21.2, 1.21.6, 1.21.7, 1.21.9.
- No trajectory measurement, client/server runtime check, or implementation review was performed.

