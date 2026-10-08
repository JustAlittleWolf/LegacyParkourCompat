# Source-only handoff: 1.17.1 → 1.18.2

- Author-side freeze candidate commit: `9ce2300628fe6d16b47656e3c65ee943129d86d6`.
- Pair status: `partial`; independent blind source audit is still required before a full-pair freeze. This file records author-side closure only.
- Current local `main` merged: `7e7b7bb7068ec7b68f3a8f09cbb42bc498a04bf4`; verified as an ancestor of this branch after merge commit `fb125648594d6c2b8f0881c99d59c814216916ee`. `origin/main` at merge time was stale at `0bfb72a0bc08726f2ee3a984203c08b5d90cf34e`.
- Branch: `feat/source-1-17-1-18-2-fresh-oct8`.
- Exact artifact roots: `build/movement-campaign-2026-10-07/ready/1.17.1/mojmap` and `build/movement-campaign-2026-10-07/ready/1.18.2/mojmap`.
- A identity: source manifest `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b`; artifact manifest `e52c5dbae7d9663190ccc55a4f9b44a8e0615fb1fbbd8280811df43f70ec8aaa`; mapped jar `2a2be036174902e447865498741b8c59fa2e090d352d786a8507dccb7c23008c`.
- B identity: source manifest `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a`; artifact manifest `a1507e4875faee892aca4c59686bbd68933db21a2274559e81eea8a32b021036`; mapped jar `60a2017dd217b23df8a5ddbebac96fccb58ff747e961974696e0018f6ef12dba`.

## Author-side inventory closure

The paired run ledger contains 28 terminal coverage rows: 13 `compared-no-difference`, 15 `findings`, zero pending/in-progress/not-applicable/blocked. Seven required inventories are complete:

| Inventory | Status | Assigned slice count | Evidence summary |
| --- | --- | ---: | --- |
| `INV-TICK` | complete | 8 | Input sampling/assignment; LocalPlayer/Player/LivingEntity tick order; pre/post-travel; fluid/air/fall-flying and auto-jump; unloaded-chunk branch. |
| `INV-STATE` | complete | 9 | Position/velocity/AABB, pose/dimensions/eye height, collision/ground/fluid flags, sprint/jump/fall state and external writers/readers. |
| `INV-COLLISION` | complete | 8 | Query and solver order, border, entity candidates, shapes/providers/state/default/registration/support, callbacks and server rollback. |
| `INV-WORLD-MOVEMENT` | complete | 2 | Block/fluid registrations, defaults, tags/resources, movement properties and consumer math, collision/support shapes. |
| `INV-MODIFIERS` | complete | 2 | Attributes, effects/potions, enchantments/equipment, payloads, lifecycle and player consumers. |
| `INV-EXTERNAL` | complete | 8 | Packet correction/acceptance, direct player velocity/impulse callers, mount/passenger input and rider positions, teleport and dimension-fed travel. |
| `INV-EXCLUSIONS` | complete | scope boundary | Health/food and attack/damage production, target-only/non-player and vehicle motion excluded; equal-input direct Player response retained. |

Author-side dependency closure includes `D-PACKET-RECONCILIATION`, `D-EXTERNAL-VELOCITY`, `D-MOUNT-INPUT`, and `D-OVERWORLD-MIN-Y`; the detailed method ranges and rationale are in `run.md`. The firework rocket position/hit-routing change is excluded as projectile trajectory; the direct player boost and gates were compared for equivalent player inputs. Border, auto-jump, crouch-edge, dismount, and chorus-fruit findings remain candidates pending independent review. No implementation source, Wiki, or MCPK material was used for this source-only conclusion.

## Immutable report and finding bindings

The following Git blob and raw SHA-256 values bind the exact report/findings at author-side freeze candidate commit `9ce2300628fe6d16b47656e3c65ee943129d86d6`. Every finding is paired to the exact A/B source and artifact identities above. Existing earlier candidate snapshot commits and prior histories are preserved in Git and documented in `run.md`.

| ID | Repository path | Git blob | Raw file SHA-256 |
| --- | --- | --- | --- |
| Run ledger | `workflows/source-campaign-2026-10-07/1.17.1--1.18.2/run.md` | `53bef7aae76bef026d5f5d19bc111635ddc6265d` | `70610a914b440ab651163f1ccd708000f4261da24727f7a4614eca473095b3f5` |
| F-001 | `findings/F-001-minor-horizontal-collision-sprint.md` | `9bc53d27195a6f89ddb19711ef40558d3c7bdcc8` | `77957564feaa103b7817055c71c0a9d9e31d7af4bacae5432b4b18d82c99e2fd` |
| F-002 | `findings/F-002-elytra-cosine-precision.md` | `75b6206ca3434ffd96d84caa1a6ff2fc6b5c4ab5` | `bb72676167340fa5e001062b3f9cb69ce36d0160b37fe834212693a4feb0b546` |
| F-003 | `findings/F-003-autojump-candidate-order.md` | `e801da616021d5cab6a1ec6d985210803824f618` | `221aa9b6e306ded17642e6cf59afcf5c03d12f676d0bc7ded30e80cb670f77f1` |
| F-004 | `findings/F-004-autojump-border-shape.md` | `57aa2270e44cee63f27c538c13681ef90efe04d4` | `6a9b9d60301b6670fc4b3e6279bf2f9399d1bb714de9137a79738004935063d2` |
| F-005 | `findings/F-005-fall-distance-reset-edge-gate.md` | `8a9ff41d1c685bda185180863fe5069b5ec90783` | `6a0803d36f7d7f0f51ebdf9753e5f7adf3863cf8fe7418415ad55b12e6128d73` |
| F-006 | `findings/F-006-boat-passenger-yaw-refresh.md` | `4bc7991b67eca816de3f16b44180a7ef708bfb90` | `3994115e3789a8280aa61228b2b092a007e57985cde72492f19731a8f150db95` |
| F-007 | `findings/F-007-position-packet-threshold.md` | `2d3b24b4b99c828dfbe8b287e1fc67a863f6e1d0` | `e311648628d45ab6680d0715ae85817399933157d243bfc6ed37b30b1f5aba09` |
| F-008 | `findings/F-008-border-edge-gate.md` | `58ebc5b2dbff5f0e409fdceb38b0466e89322ce4` | `3a8a5258cb741497a52085f76688df7ca32161dcbd0e068ffe5ee70b3a271afd` |
| F-009 | `findings/F-009-border-overlap-axis-collision.md` | `1db71cf25a7b31b2efbebbed12cd582af7aec423` | `c54aa0c02915c6ba7853a5dec58f0a8d360f27d65510501f0d746254703c1c21` |
| F-010 | `findings/F-010-border-dismount-candidates.md` | `5fe78a7d848bc370bb9bc86d44a296a9efb90f37` | `65407bcfbc1758f4e267362ac7cfeb88ac67abdf6f646b62452ce5ccfff757e6` |
| F-011 | `findings/F-011-chorus-fruit-border-teleport.md` | `0d72a74ab5f1b5e982a630e026c2c31cc2a79c0a` | `decbf5a6a4f8963efb8d19824c30d96ebeac25c22598e8622858e3fa6bf153e3` |
| F-012 | `findings/F-012-sprint-air-control-float.md` | `947ca63e44ec3f8ecaa710f4168c95bf66e5402a` | `b089246435bb50d410fe8dbe5502fbcd2245a4aa72f6e896c1040eecb47c6bff` |
| F-013 | `findings/F-013-llama-passenger-rider-height.md` | `7f021ef954c2e90314bea14e0e9ed06bbc7c2ef1` | `fc39381d46aa3257077dd20fd8ce7f8242b201b6b7c74ca9fc7fd2b1dceb511d` |
| F-014 | `findings/F-014-overworld-min-y-unloaded-chunk-fall.md` | `ca968ab66df2cb44ea8c0918a3b4ff870740fc93` | `65161ef4a77c6c369af2a74e8a1cc4673cf2f724c8b94fa826548c04a05ae25a` |

## Remaining gate

A different reviewer must independently re-walk the full pair inventory and tick/call graph while blind to implementation and Wiki/MCPK material, record concrete missed-slice routes or `none found`, route any misses, and decide whether the pair can freeze. Until that audit passes, the pair report remains `partial`; no implementation or runtime status is claimed. The static checker only validates report structure/status. No tests, builds, runtime, clients, servers, TAS/Gym, Docker, or push were performed.
