# Independent DDA provider and hit closure review

## Verdict

**HOLD** for a usable historical provider/hit adapter or an integrating candidate. The notes accurately identify the 1.8.9 per-cell contract and its custom provider families, but they also establish that ordinary inherited `Block.rayTrace` providers can be first hits and the complete old-ID/state/shape mapping for those providers is absent. A candidate limited to the named override families would therefore still make unsupported assumptions about occlusion and visitation.

This review does not reject the standalone DDA traversal callback. It confirms that its caller-owned provider dependency remains open. Keep the `V1_8` native barrier; no 1.8 or 1.8.1 source coverage is established.

## Bound inputs

Recomputed SHA-256 values match every requested artifact:

| Artifact | SHA-256 |
|---|---|
| `dda-legacy-provider-closure/provider-and-hit-contract.md` | `20904785D6D067658FC433842146D364851822CAF7C7B8565CA5C8B761894A29` |
| `dda-ray-alias-state-map/closure.md` | `811DBAB2A71571C06AA43A9283373967F8D8F845443ABEEA2119E0CC8B43761B` |
| `dda-provider-closure/full-ray-provider-inventory.md` | `5B970E9685C73E044F51771CA5FE79F00E4D97984F44E17F82978CB6EF05914E` |
| `dda-current-shape-inventory/current-shape-api-inventory.md` | `AA90B1D8673FB825A5C86887BAF347B01D172C7D3D48EE84747B60441AE4106F` |

## Independent source checks

- Canonical 1.8.9 `Entity.rayTrace` passes `false, false, true` to `World.rayTrace` (`Entity.java:1029-1034`). In `World.rayTrace`, the first flag controls liquid eligibility, the second disables the collision-shape prefilter, and the third permits the start-cell result (`World.java:633-652`, `740-760`). A `canRayTrace`-eligible block’s virtual `rayTrace` result returns immediately when non-null; later rejected cells can replace the remembered MISS. Thus provider results affect which subsequent cells are queried.
- The exact 1.8.9 source search finds only the documented `canRayTrace` declarations: base `Block`, `AirBlock`, `FireBlock`, `LiquidBlock`, and `StairsBlock`. Its `rayTrace` declarations are base `Block`, `AbstractRailBlock`, `DoorBlock`, `TrapdoorBlock`, `TorchBlock`, `StairsBlock`, and `MovingBlock`. This matches the contract’s override-family inventory. `StairsBlock` delegates eligibility to its wrapped base block; `MovingBlock` returns null rather than delegating.
- Base `Block.canRayTrace` delegates to `canRayTrace()`, whose base implementation returns true (`Block.java:356-362`). Base `Block.rayTrace` invokes virtual `updateShape` before clipping its current bounds (`Block.java:456-516`). Because the camera call disables the collision prefilter, this default path is available to ordinary old registry blocks, not only the custom override families.
- Canonical 26.2 `BlockGetter.clip` composes block outline clipping, interaction-shape direction adjustment, and a fluid result (`BlockGetter.java:65-96`). Its `traverseBlocks` uses its own endpoint adjustment and cell visitation (`BlockGetter.java:114-173`). Neither method supplies the old virtual provider dispatch, mutable bounds, or old first-hit traversal. The directly rehashed core sources match the inventories: 1.8.9 `Block.java` `EA10F051…FFA528`, `World.java` `3C04F5B8…3888EE`, and 26.2 `BlockGetter.java` `623A4420…B687D`.
- The alias map covers the finite custom families (rails, doors, trapdoors, torches, stairs, and moving piston) and explicitly leaves their state construction or provider behavior incomplete. It does not map every inherited default provider. The 35-entry shared-bound writer inventory is not a registry/metadata crosswalk and does not cover all fixed constructor bounds. The 1.12 pane crosswalk has a different release and cannot fill that gap.

## Required closure before candidate integration

An integrating caller needs an exact 1.8.9 registry identity plus state/metadata reconstruction and legacy ray-bound/provider behavior for every old cell that can be visited and hit, including inherited default providers. It also needs the deliberate modern-only/unmapped-cell fallback semantics: `null` means continue historical traversal and cannot silently stand for “use native modern picking.” The current materials do not establish a safe per-family fallback that preserves first-hit behavior.

No product code or history, backlog, tests, build, game, or runtime was inspected or used. No code was changed.
