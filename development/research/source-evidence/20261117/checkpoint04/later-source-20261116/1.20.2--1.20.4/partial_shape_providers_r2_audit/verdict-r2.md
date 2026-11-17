# Independent audit — 1.20.2 → 1.20.4 partial-shape providers r2

## Verdict

**ACCEPT** for the immutable r2 report `build/later-source-20261116/1.20.2--1.20.4/partial_shape_providers_revision_r2/coverage-r2.md`, SHA-256 `1A4FFFC6F4CD68F7E176E0869DEBF9B386091EB4B416643A2D471B3E5E6B0189`.

## Audit scope and evidence

The prior independent verdict `build/later-source-20261116/1.20.2--1.20.4/partial_shape_providers_audit/verdict.md`, SHA-256 `D1A349BC8BEF128E64B1E9C346A23943B936F3D31B9FB70C5741B0D024072EC8`, verified both endpoint identities/manifests, all fourteen cited source-hash rows, and the original bounded comparisons. It requested the missing inherited ladder collision route only.

The r2 report hash matches the assigned immutable revision. The original r1 file remains SHA-256 `23600E9F2D06C85EC10515AC4ECCC1692448EB1B2A3384B364DE13ECDE6DDFE1`; its full contents are an unchanged prefix of r2, followed by the requested ladder-collision section. I reused the prior verdict for unchanged source identities and comparisons.

I independently checked the added ladder route in the exact Mojmap sources:

- In both releases, `LadderBlock` overrides `getShape` with the four facing-selected boxes, each 3/16 thick (`LadderBlock.java:36–48` in 1.20.2; `43–55` in 1.20.4), and does not override `getCollisionShape`.
- The inherited `BlockBehaviour.getCollisionShape` returns `state.getShape(...)` when `hasCollision` is true (`BlockBehaviour.java:290–292` in 1.20.2; `334–336` in 1.20.4). The properties default `hasCollision` to true (`:908` and `:960`, respectively).
- The `Blocks.LADDER` registrations call `.noOcclusion()` and do not call `.noCollission()` (`Blocks.java:1573–1576` in 1.20.2; `1588–1591` in 1.20.4). `noOcclusion()` changes only `canOcclude` (`BlockBehaviour.java:999–1001` / `1066–1068`); `noCollission()` is the method that also clears `hasCollision` (`:993–997` / `1060–1064`). Thus registered ladder collision uses the same direction-selected 3/16 box as its shape in both endpoints.

Local SHA-256 values for both endpoints match the report's already-bound rows: `BlockBehaviour.java` 1.20.2 `a6d88726ce4642fc0a61f46a01347bb0b74887b61ef55af7e7c76723789a0b57`, 1.20.4 `6284a7d08a6d0d475cd4fe8aea62b35153ae8fbc0bce5f51788389f679233b54`; `Blocks.java` 1.20.2 `75e8e77adab4a07b02ef95619be83a004f5f79810cf3f07157a713d714881253`, 1.20.4 `5392c4349317e55c2989865be38c17b94e6e0543e7017e6a2a64d79217e83643`. These match the corresponding source-manifest entries. The r2 correction introduces no new source file or identity claim.

## Limits

Acceptance is limited to the corrected ladder collision-path evidence and the unchanged bounded provider comparisons accepted in the prior verdict. Snow-tag membership, ordinary state reachability, other shape families, runtime behavior, and pair-wide dependency closure remain open; no pair freeze is implied.
