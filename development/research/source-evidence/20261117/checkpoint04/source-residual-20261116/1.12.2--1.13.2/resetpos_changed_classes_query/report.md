# ResetPos changed-class provider reachability: 1.12.2 → 1.13.2

## Scope and result

This bounded source-only report joins only the 17 exact common-key rows whose registered provider class changes in the accepted ResetPos key/provider matrix to the accepted legal-state table and ResetPos block-region note. It does not recompute shapes, infer trajectories, claim pair closure, or extend to the other 167 common keys or the 57 state-cardinality changes.

The accepted block-region note establishes that ResetPos queries the normal 0.6 × 1.8 player box at integer Y and enumerates padded positions, with the selected border/chunk gates. For each of these exact common keys, a block can occupy an interior candidate cell that intersects that box at some reachable world position. Thus all 17 class providers are query-reachable in the bounded geometric sense. This says nothing about a particular map, legal state/context combination, resulting collision, or displacement.

## Endpoint identity and immutable inputs

Exact endpoints: Ornithe Feather Gen2 build 2, 1.12.2 manifest `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` → 1.13.2 manifest `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.

| Input | Exact SHA-256 |
|---|---|
| `resetpos_block_state_residual_matrix/coverage.md` | `C4AC654CBBE8A4958CC2B1388F05A2CE44E2FD320395DB6F60D2EB332F8CC127` |
| `resetpos_block_state_residual_matrix/resetpos-common-key-provider-matrix.csv` | `b7294302cd98bc923f543608ee9f5935a24a02033ac6033828c339dc702a2974` |
| `resetpos_state_domain_expansion/coverage.md` | `fad40d431698e7f7ef2e90646313ce61bcfb5fe3478c69295c9939280ef2dddc` |
| `resetpos_state_domain_expansion/resetpos-legal-state-domains.csv` | `f12118dc09be593fdd58b268469b985fef95cfe644f3f5916aac4525977cb382` |
| `resetpos_block_region/note.md` | `8183cc6d247d0e236287d2b1f5bd9cddfa477fd373cb99d6ced9bc6cccaafe95` |
| `base_collision_dispatch/coverage.md` | `2199f933da3c36f38bdffe942dac095e66c5948b3af9654adc06bbf1af9fef04` |
| `provider_coverage_ledger/coverage-ledger.md` | `f5133f3574a8a4bc9c2aae01a357f9a2b4f9507c37b5cd7ebc669c4a129b44b2` |

Additional accepted packet identities referenced by these supplied inputs: the state-independent default unit-cube provider slice is `default_block_collision_route/coverage-r2.md` SHA-256 `3d2ecb822cde9aa8fa6592aca23687c01840479913409b6c32b83452b252381e`, with ACCEPT verdict `6309ee8ddca94643c97914af7ed57819e0e3641b20c1dea4a333d4303c02741d`. The provider ledger lists bounded liquid packet report/verdict `6214f5ba0475168cc873818f63a9eee10b01c8cdae343a37b58d67301fa080a6` / `7caa16d8de5bcfa60c840a1ec68f43869727c3608f2258109329f9f2bd329602`; snow collision packet r3 report/verdict `124ccdf3667e28f9c241109cb3b1af60fb7bc88be84ae9a091413d2856e2f904` / `57b79925ce42119d2444a3c79d5729fe9d0018e0577fa730790856045202dbf2`; and bounded stateful slab index/lifecycle packet identities `ebf6783a3368234258c71df365b4df58c2d42d61d78855b306c46a338cdeada2` / `c518cb585cfb2c6a6095e97e357c069ebd78ae4a6bcfd427e7f4963f96854c4e` and `02836adc143861993e1214fdc3ef3950e588e1bb20b7768258c63da1f1242c0f` / `3d33b2ee086ffa9fe6da34d4012fb3b30b4d711fbd2f80d7fb6d620c36087254`. Packet identities are cited only to record existing bounded evidence; this report does not expand their accepted claims.

## Changed-class key join

“Reachable” means a key can occur in a candidate cell intersecting the ResetPos box; it does not mean every state is reachable by every world-generation or migration route.

| Key | Registered class change | Legal state counts (old → new) | Query reach | Reusable accepted geometry evidence / remaining gate |
|---|---|---:|---|---|
| `minecraft:grass` | `GrassBlock` → `TallPlantBlock` | 2 → 1 | Yes | No matching accepted packet is identified in the supplied ledger for this changed-key provider pair. Provider/state/context open. |
| `minecraft:dirt` | `DirtBlock` → `Block` | 6 → 1 | Yes | Reuse the accepted state-independent unit-cube provider slice for this exact matrix key. This does not close ResetPos query integration. |
| `minecraft:water` | `LiquidSourceBlock` → `LiquidBlock` | 16 → 16 | Yes | Ledger identifies bounded liquid evidence, but supplied ledger does not bind its reviewed state set to this key’s full level domain. Reuse not extended; exact key/state/context applicability open. |
| `minecraft:lava` | `LiquidSourceBlock` → `LiquidBlock` | 16 → 16 | Yes | Same bounded-liquid limitation as water; exact key/state/context applicability open. |
| `minecraft:sandstone` | `SandstoneBlock` → `Block` | 3 → 1 | Yes | Reuse the accepted state-independent unit-cube provider slice for this exact matrix key. Query integration remains open. |
| `minecraft:stone_slab` | `SingleStoneSlabBlock` → `SlabBlock` | 16 → 6 | Yes | Stateful slab packets are bounded, but supplied ledger retains raw `StructurePiece.setBlockState` insertion as an uncovered route and does not explicitly bind this exact registered key/state crosswalk to the reviewed slice. Full key/state reuse remains open. |
| `minecraft:obsidian` | `ObsidianBlock` → `Block` | 1 → 1 | Yes | Reuse the accepted state-independent unit-cube provider slice for this exact matrix key. Query integration remains open. |
| `minecraft:snow` | `SnowBlock` → `SnowLayerBlock` | 1 → 8 | Yes | Snow r3 accepts corrections only; supplied ledger says layer 1 may conditionally enter old collision enumeration/Y clipping, while layers 2–8 and ordinary-player applicability/displacement remain open. Reuse at most the bounded layer-1 packet claim; no all-state closure. |
| `minecraft:netherrack` | `NetherrackBlock` → `Block` | 1 → 1 | Yes | Reuse the accepted state-independent unit-cube provider slice for this exact matrix key. Query integration remains open. |
| `minecraft:iron_bars` | `PaneBlock` → `IronBarsBlock` | 16 → 32 | Yes | Ledger explicitly says no dedicated pair provider report for this key. Provider/state/context open. |
| `minecraft:glass_pane` | `PaneBlock` → `GlassPaneBlock` | 16 → 32 | Yes | Ledger explicitly says no dedicated pair provider report for this key. Provider/state/context open. |
| `minecraft:trapped_chest` | `ChestBlock` → `TrappedChestBlock` | 4 → 24 | Yes | Ledger’s bounded chest packet does not explicitly bind the trapped-chest key and expanded type/waterlogged state domain. Reuse not extended; exact key/state/context open. |
| `minecraft:quartz_block` | `QuartzBlock` → `Block` | 5 → 1 | Yes | Reuse the accepted state-independent unit-cube provider slice for this exact matrix key. Query integration remains open. |
| `minecraft:prismarine` | `PrismarineBlock` → `Block` | 3 → 1 | Yes | Reuse the accepted state-independent unit-cube provider slice for this exact matrix key. Query integration remains open. |
| `minecraft:red_sandstone` | `RedSandstoneBlock` → `Block` | 3 → 1 | Yes | Reuse the accepted state-independent unit-cube provider slice for this exact matrix key. Query integration remains open. |
| `minecraft:purpur_slab` | `PurpurSlabBlock.Single` → `SlabBlock` | 2 → 6 | Yes | Stateful slab packets are bounded, but supplied ledger retains raw `StructurePiece.setBlockState` insertion as an uncovered route and does not explicitly bind this exact registered key/state crosswalk to the reviewed slice. Full key/state reuse remains open. |
| `minecraft:bone_block` | `BoneBlock` → `AxisBlock` | 3 → 1 | Yes | Reuse the accepted state-independent unit-cube provider slice for this exact matrix key. Query integration remains open. |

## Bounded outcome and remaining gates

Covered keys: all 17 provider-class correspondences above; geometrically reachable in the ResetPos candidate query. Existing accepted state-independent unit-cube provider geometry is reusable for eight exact keys: dirt, sandstone, obsidian, netherrack, quartz block, prismarine, red sandstone, and bone block. Snow has only the reported conditional layer-1 slice; that does not cover the full new eight-layer domain or establish ordinary-player query applicability.

Still open: exact accepted state coverage/applicability for both liquid keys; grass; both pane keys; both slab key/state crosswalks and raw world-generation insertion; trapped-chest key and expanded state domain; snow layers 2–8 and player/query applicability; and ResetPos collision-query integration/consumer effects for every key. The accepted base-dispatch packet itself leaves default provider parity beyond its separately accepted unit-cube slice and final query displacement impact open. This is not a trajectory, pair-closure, or implementation claim.
