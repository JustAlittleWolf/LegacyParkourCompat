# Independent full-pair source audit: 1.10 → 1.10.1

**Verdict: ACCEPT** for the exact source coverage and the bounded farmland finding. This closes source coverage for this exact patch edge only. It makes no claim about implementation or runtime parity.

## Bound inputs

- Coverage report: `build/source-early-patches-20261117/1.10--1.10.1/coverage.md`, SHA-256 `A6834451183E112421E522D0BFF6A4D76F8EE91C440B77CEFA23D4F8579D0A51`.
- Finding: `build/source-early-patches-20261117/1.10--1.10.1/findings/farmland-collision-height.md`, SHA-256 `B53ADDA9AE51241C39F2925F64437BE7C278062E314DAD80092BFA9A098F7FFF`.
- Exact source identities: 1.10 mapping-local provenance SHA-256 `DF266E2C3009DD1A873C1665C30FE388078ED1C6227668E10CDE87409A18C6DA`; 1.10.1 mapping-local provenance SHA-256 `A9A50497930848BF0DDEC732B6D424E947F643A8FA6290E01425AC2BBFCE219F`.
- Neutral source contract: `build/source-inputs-110-112-20261117/queue.md`, SHA-256 `358D084A0CBEFC450B70224608845580B3854C64EDA90D15FACD8AB46682EC72`.

## Audit results

Both exact source manifests were checked against every local source file: 1,845 entries per release, with no missing files or mismatched hashes. The path sets match; the independently recomputed delta is 29 changed shared paths, with no additions or removals. The report accounts for the full changed set.

The movement claim is supported. In 1.10, `FarmlandBlock.getCollisionShape` returns `FULL_BLOCK_SHAPE`, while the unchanged outline shape is 15/16 block high. In 1.10.1 the override is absent, so the inherited `Block.getCollisionShape` returns `state.getShape(world, pos)`, the same 15/16 shape. The unchanged `World.getCollisions` / `BlockState.addCollisions` / `Block.addCollisions` path supplies that box to `Entity.move`, inherited by the local player through `LivingEntity`. A downward collision with farmland can therefore resolve the player position 1/16 block lower in 1.10.1. The provider is constant and has no state, neighbor, tag or entity dependency.

The remaining changed paths were checked against the exact inventory and classified within scope: player data-version and health migration changes do not alter movement; the potion synchronized-data owner change is for a non-player projectile; the world/view `isEmpty` removal and block-entity lookup overload do not affect the local player collision path; server-side End-fight/persistence edits do not change client movement physics or vanilla block-state rules; version/UI/render changes are excluded. The keyboard polling boundary changes concern input-device/key-code plumbing, which is excluded; no historical movement rule change is inferred for valid ordinary key bindings.

No unresolved source dependency remains for the farmland finding or the changed-file inventory. This ACCEPT is specific to `1.10→1.10.1`; no adjacent edge or runtime result is inferred.
