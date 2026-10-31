# Fluid movement route closure — Mojmap 1.15.2 → 1.16.5

## Verdict

The requested fluid rows are closed for **same-input, fixed sampled client state** across occupancy, height, flow direction, and the client player tick consumer. No additional eligible fixed-state Java member delta remains open in `Fluid`, `FluidState`/`FluidStateImpl`, `FlowingFluid`, `WaterFluid`, or `LavaFluid` after the already bounded height, water-current, lava-current, and Lava travel/jump slices are joined.

The broader route rows remain PARTIAL for dynamic tag/resource membership and for fluid-state production/receipt timing. Those are separate from the fixed-state consumer result and are not inferred here. LocalPlayer's unrelated changed `aiStep`/`move`/underwater members remain outside this fluid-only closure.

## Exact source identity and integrity

Canonical sources were read-only at:

- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.15.2\mojmap`
- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.16.5\mojmap`

Both `mojmap.ready.json` files identify the exact requested release, mapping, Vineflower, Java 25, and `status: ready`. Their `sourceManifestSha256` values match the actual manifest files: 1.15.2 `CB7FD93F8730D8B2813857744F8572060535C7DE44FCEE86222ACD1CEB6F43E7`; 1.16.5 `9499F2611D0E6DDE37CB635F1A28A591416382D6BC188B37D5F99E2B2A20823B`.

The corrected route TSV's endpoint SHA-256 values were recomputed for the relevant exact files. Both endpoint hashes matched for `Entity.java`, `LivingEntity.java`, `Player.java`, `LocalPlayer.java`, `Fluid.java`, `FluidState.java`, `FlowingFluid.java`, `WaterFluid.java`, and `LavaFluid.java`; the old `FluidStateImpl.java` hash matched and the newer path is absent, as recorded by the `removed` route. The reusable pool33 flow/current source ledgers also match the corresponding files. No source tree was generated or modified.

## Fixed-state method closure

- **Occupancy and scan:** `Entity.baseTick` reaches the old water scan through `updateWaterState`/`updateInWaterState`/`checkAndHandleWater(FluidTags.WATER)` (1.15.2 `Entity.java`, lines 363, 943–968, 2593–2655). The new `baseTick` reaches `updateInWaterStateAndDoFluidPushing`, runs the water consumer, then the LAVA consumer (1.16.5 `Entity.java`, lines 380, 957–978, 2641–2705). The scan uses the deflated box, the same integer bounds and iteration order, loaded-chunk gate, `FluidState.is(tag)`, surface-versus-box-bottom test, and greatest overlap accumulation. Fixed-state water occupancy/depth is equivalent.
- **Height dispatch:** both `FluidState.getHeight` and `getOwnHeight` delegate to the current fluid type; `FlowingFluid.getHeight` returns `1.0F` for a same-fluid state above and otherwise `getOwnHeight`, while `getOwnHeight` returns amount divided by `9.0F`. The fixed-state provider calculation and numeric result are unchanged. 1.15.2 `FluidStateImpl` is the concrete `StateHolder` implementation; in 1.16.5 these same movement-facing delegates reside on the concrete `FluidState` class. No movement-facing height delta was found in that relocation.
- **Flow/current direction:** `FluidState.getFlow` still delegates to `Fluid.getFlow`; `FlowingFluid.getFlow` has the same horizontal direction order, neighbor-height differences, falling/solid-face branch, vector construction and normalization. The source edit is mutable-position handling only. `WaterFluid`/`LavaFluid` retain equivalent fixed-state amount/source providers used by these routes. The source's exact water/lava current conclusions remain those in the bounded pool33 closures; this audit does not replace them.
- **Water response:** the old water scan and new WATER specialization retain the same averaged current computation and `0.014` factor for fixed inputs. The already bounded low-speed minimum-current addition is the distinct new 1.16.5 branch (pool33 `fluid-current-findings-audit`; locally reached through the normal base tick before local `aiStep`/`travel`).
- **Lava response:** old `Entity.baseTick` does not invoke a lava-current scan; the new helper scans `FluidTags.LAVA` with dimension scale `0.007` (ultra-warm) or `0.0023333333333333335` otherwise. Under the existing scan/current gates, this is the source of the previously reviewed conditional local-player lava-current delta (pool33 `lava-current-independent-audit`).
- **Jump/travel consumer:** `LivingEntity.tick` calls `super.tick` before `aiStep`, and the player tick path reaches that inherited route; `LocalPlayer.tick` then reaches the ordinary `aiStep` and `travel` path. The exact lava shallow-depth jump and travel branch differences, and the unchanged ordinary water-depth branch, remain the bounded conclusions in pool33 `fluid-height-branch-closure`. No distinct new fixed-state consumer delta was found in this rejoin.

## Remaining boundaries

1. **Dynamic membership:** `FluidState.is(tag)` delegates to `Fluid.is(tag)`. The effective WATER/LAVA tag contents can be resource-driven; the Java source trees do not establish default or datapack membership. The conditional source routes remain conditional on identical effective tag membership at the compared endpoints. No resource or tag payload was inspected.
2. **State production and receipt:** fluid ticking/spreading, callbacks that establish old lava-contact state, chunk/block/fluid updates, and the timing at which those states reach the local client determine the sampled input. They do not change the equal-input client consumer math established above. Server scheduling, packet production, and receipt ordering are not claimed closed by this report.
3. **Unrelated LocalPlayer residual:** this task closes only fluid-related LocalPlayer tick consumers. Any other changed `LocalPlayer.aiStep`, `move`, or underwater dependencies remain with their separately bounded route work.

Accordingly, the corrected unresolved TSV rows for `Fluid`, `FluidState`, `FluidStateImpl`, `FlowingFluid`, `WaterFluid`, and `LavaFluid` have no remaining same-input fixed-state movement member open; retain their broader PARTIAL disposition only for the boundaries above. The `Entity`/`LivingEntity`/`Player` rows retain their unrelated gates and other movement members. This report does not freeze the pair or establish implementation/runtime parity.

## Source-only procedure

Read-only static source inspection. No build, tests, launch, decompilation, downloads, product edit, wiki use, or backlog edit.

## Exact unresolved route rows and source pins

The current route table's fluid rows were `PARTIAL`, not `OPEN`; this follow-up closes their fixed-state member slice. The remaining precise gates are:

- `FluidState.is(Tag<Fluid>)` → `Fluid.is(Tag<Fluid>)` → `Tag.contains(Fluid)`: effective WATER/LAVA membership is not present in these Java source trees. This is a resource/tag input gate, not an unresolved height/vector formula.
- Fluid-state production before `Entity.baseTick` samples the world: the old `LiquidBlock.entityInside` lava-contact setter and fluid tick/spread routes (`FluidState.tick` → `Fluid.tick`/`FlowingFluid` and `WaterFluid`/`LavaFluid`) can determine future sampled states. Their scheduling and packet/update delivery are not established by equal-input consumption. Client chunk/block/fluid state receipt ordering is likewise outside this source slice. No server producer or packet timing assertion is made.
- In the fixed-state consumer, `Entity.baseTick` → fluid update → `LivingEntity.tick`/`aiStep`/`travel` is closed for the fluid dependencies named above. Other `LocalPlayer.aiStep`/`move` residuals are not reclassified here.

Direct SHA-256 pins for the exact row inputs (older / newer; newer blank means absent) are:

| Source | 1.15.2 | 1.16.5 |
|---|---|---|
| `Entity.java` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` |
| `LivingEntity.java` | `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54` | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` |
| `Player.java` | `1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793` | `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` |
| `LocalPlayer.java` | `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd` | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` |
| `Fluid.java` | `b20c97ed8fdae1e3ef945340113a2629a8bc53b12fad84bf1ac6c689669de13b` | `b37e7870f93cf1c7d4c96456a668c3f42ee48b5c5b9882e9c7cecea55717ae2f` |
| `FluidState.java` | `f88bff7d54ea0220ee67e0fe36a78df67871e52ae636e584ea26dd107f652c15` | `665b4024a32f659d92ce1259b25f529616e44b6f3df8a6aab4a2c57c7bc26d` |
| `FluidStateImpl.java` | `4d678aa8c80271d1a3f1069accd2c726b094602727875436b5e05e3101236b13` | absent |
| `FlowingFluid.java` | `e8a395552e53f33bcb5648325615d31bc1796ce2a8aeb501bdda3127622dccdf` | `a85a5cd6625b1b4a047bd693399e1f78c2978ae207af927d4abfac3b34229552` |
| `WaterFluid.java` | `94de56135503a3474dc15b86e9a1d13e149dc6ab20d60caa6e8a085a4141dd96` | `d3f28e4be4be66a0e222eccad96c0c90d57f55380fdd39cf8e5840c871cc052b` |
| `LavaFluid.java` | `d315b4cc1cd215e981bf343c03d1fe86de64b4fcea3b0869efff5ac57aff7fd2` | `6e98f387c7561bff3b66f2e86ce54bab54a8ff3337639cb106e5e6aaad8ada9b` |

These are the exact endpoint file hashes from the corrected route ledger and were recomputed from the canonical roots. The source-tree manifest digests and ready-marker identities are recorded above.
