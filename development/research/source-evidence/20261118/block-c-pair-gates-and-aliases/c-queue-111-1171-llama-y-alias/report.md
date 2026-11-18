# Bounded source audit: llama passenger Y placement

TERMINAL: **COMPLETED**

## Verdict

For a direct local Player passenger on a default adult Llama, the exact 1.11 and 1.17.1 sources both place the player using the Llama-specific `0.67` height term, followed by the player-specific `-0.35` seat offset. The operation and arithmetic order are behaviorally equivalent at these endpoints. The effective rider Y is `vehicleY + (vehicleHeight * 0.67) + (-0.35)`.

At the Llama's default adult height `1.87F`, the `0.67` term evaluates after float-to-double promotion. Relative to the inherited generic vehicle height term `height * 0.75`, it is lower by about `0.1496` blocks (`1.87F * (0.67 - 0.75)`). The player offset is separate and common to both. This is a source arithmetic comparison, not a simulated path. Llama's own non-player physics is outside this slice.

## Exact source trace

| Release | Llama writer / vehicle height term | Player seat term | Source identity |
|---|---|---|---|
| 1.11 | `LlamaEntity.updateRiderPositon`: guarded `rider.setPosition(..., y + getMountHeight() + rider.getRideHeight(), ...)`; `getMountHeight()` returns `height * 0.67`. | `PlayerEntity.getRideHeight()` returns `-0.35`. | `ornithe-feather`; `LlamaEntity.java` SHA-256 `1577cf77a5c1d0e47dde0e13327dc333fcd65aa86c7e9073f49b2c678f0257b1`; listed in `1.11/ornithe-feather.sources.sha256`. Ready marker and provenance exist. |
| 1.12.2 | Same guarded addition and `height * 0.67`. | `-0.35`. | `ornithe-feather`; Llama SHA-256 `487c7209a69391f1a19dce5fbdf34d609d2e9bc53c47b04a225e9c0d0c93cf7e`; manifest entry, ready marker and provenance exist. |
| 1.13.2 | Same guarded addition and `height * 0.67`. | `-0.35`. | `ornithe-feather`; Llama SHA-256 `5be437bcd5600c4dbcd7ef5e5224ac31baa69efbd69b11152b0bc04081922089`; manifest entry, ready marker and provenance exist. |
| 1.14.4 | `Llama.positionRider`: `y + getRideHeight() + entity.getRidingHeight()`; Llama override returns `getBbHeight() * 0.67`. | Player's `getRidingHeight()` returns `-0.35`. | `mojmap`; Llama SHA-256 `3e62e517a4bea2723cb4fd401d00a1de128e2dc1d5bd18dc5fd9e612449f296f`; manifest entry, ready marker and provenance exist. |
| 1.15.2 | Same `positionRider` addition order and Llama `getBbHeight() * 0.67`. | `-0.35`. | `mojmap`; Llama SHA-256 `bbe98226c5afaddce8ca7458c721fbefff592cc75e02bc261d6b440ca6877f19`; manifest entry, ready marker and provenance exist. |
| 1.16.5 | Guarded writer computes `getY() + getPassengersRidingOffset() + entity.getMyRidingOffset()`; Llama override is `getBbHeight() * 0.67`. | Player `getMyRidingOffset()` returns `-0.35`. | `mojmap`; source exists and matches listed manifest hash `18bf21b8d7559e4c9a3195126dc0b30b691e8e869fa1c214956a6395534e5f2d`. Ready/provenance marker details were not revalidated in this audit. |
| 1.17 | Llama source has the same guarded writer and `getBbHeight() * 0.67`; Player source has `getMyRidingOffset() == -0.35`. | `-0.35`. | `mojmap` source files are present, but the `1.17` root has no ready marker, provenance file, or source hash manifest. Treat this exact minor as **unverified provenance**, not as a confirmed bridge. |
| 1.17.1 | Guarded writer adds `getY() + getPassengersRidingOffset() + entity.getMyRidingOffset()`; Llama override is `getBbHeight() * 0.67`. | Player `getMyRidingOffset()` returns `-0.35`. | `mojmap`; Llama SHA-256 `0b41ea3feac6b413976171f45fca83fad0eb9e24f06de5f3ece40d20bfb04d72`; listed in manifest, and ready marker/provenance exist. |

The API renames/refactor in 1.14 and 1.16 preserve the direct sum order: vehicle Y, vehicle-specific height, rider-specific offset. The Llama override is what distinguishes its default passenger height from the generic `0.75` default. In all checked source forms the player remains the direct local-player witness; no inference from a non-player passenger is needed. The audit did not trace a full tick graph or claim a complete release inventory.

## Provenance audit

The supplied prior verdict is the permitted source-only artifact at `C:\Users\Wolfi\.codex\visualizations\2026\11\17\01a1eb7b-b9ff-7a20-8686-0295dde20f26\llama-rider-height-audit\verdict.md`, and its SHA-256 matches the assignment: `3d91a3be598c66bccc724b5182dce96651f68cc40b0cb305f6b74846be49d9e3`. Its bounded 1.17.1-to-1.18 claim is background only; this report independently limits its verdict to the releases listed above.

Canonical local sources were read-only. Exact ready markers and local provenance exist for 1.11, 1.12.2, 1.13.2, 1.14.4, and 1.17.1. Their source manifests list the referenced Llama file hashes. The 1.16.5 Llama hash matches its local source manifest; its marker/provenance was not independently rechecked. The 1.17 tree has source files only, without ready/provenance/manifest evidence, so it cannot establish the broader continuity claim. Raw downloads/mapping artifacts were not independently rehashed.

## Missing boundaries and limits

- The exact 1.17 files show the same `0.67`/`-0.35` formula but lack local publication provenance; secure that provenance before treating 1.17 as a confirmed intermediate.
- This does not inspect every patch release from 1.11 through 1.17.1, prereleases, or snapshots. The endpoint and selected-minor evidence cannot establish a contiguous patch-by-patch cutover or complete coverage.
- No post-1.17.1 source was compared here; this report makes no claim about a later alternative formula or its introduction.
- No vehicle/non-player physics, other carriers, orientation, input/control, or runtime parity was audited.

## Artifact identity

- Report SHA-256 is reported in the completion message to avoid a self-referential digest.