# Independent blind source review — F-09-R1

- Verdict: accepted (withdrawal only)
- Review date: 2026-10-10 (Europe/Vienna)
- Review scope: the immutable F-09-R1 source snapshot only; no mod implementation, wiki material, or runtime evidence inspected.
- Pair: 1.19.4 (A) → 1.20.1 (B), Mojmap.
- Revised snapshot commit: `f9adb1d8f2b066395c86eb038ae4ce590b5368c5`.
- Finding path: `workflows/source-campaign-2026-10-07/1.19.4--1.20.1/findings/F-09-R1-open-trapdoor-wire-signal.md`.
- Finding Git blob: `0cad924cc1783a55dfb9b6c3684a5f1c8e5a1725`; raw SHA-256: `dd2398cabead36a2d84a2c41d6e6ec8ccd1a416b57eae24bad94b64d26e7e8c6`.
- Original finding binding retained: commit `234156154202d36a7a4754f7f44c6e02fae19950`, path `workflows/source-campaign-2026-10-07/1.19.4--1.20.1/findings/F-09-redstone-wire-trapdoor-neighbor-signal.md`, blob `06b71e080a2b28db9ef83badd4a498eaa672e4c3`, raw SHA-256 `3c003daa40f51e0b548d1c3ed3d1b64392e23cdd6de0794cdb4f6fa159a220dc`.
- Prior review binding retained: commit `88102131e2a6c50ea291b4a80c0475b517de069a`, path `workflows/source-campaign-2026-10-07/1.19.4--1.20.1/reviews/F-09-independent-blind-review-2026-10-10.md`, blob `aac005af17f657ddf72f9377cf89a60f17694bf4`, verdict `revision-required`, bound to the original finding above.

## Source identity and integrity

Read-only review sources were resolved from `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<version>/mojmap/`. Their file hashes match the cited pair evidence and source manifest identities in the pair run: A source manifest `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3`; B source manifest `858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465`. The publication identities remain `A-1.19.4-MOJMAP-ORIGINAL` and `B-1.20.1-MOJMAP-ORIGINAL`; no revised source artifact is used.

Paired evidence rechecked for this disposition:

- `RedStoneWireBlock.java`: A `9cb901b000b60746263b50f3ec4c70218ee3546645d8959ddb4eff29aa224e77`; B `db9be63a8fe2a90ef72c6321346224c4b564d9f7c445a008f04b84881c7910d3`.
- `TrapDoorBlock.java`: A `fae6479ab458d35b7c5b7a6588c28f8a6f7a3569b8abab96179ae86ac37904c4`; B `2151e4f2c3ea35c3dceab7dcc78e53c0d7e0357f4fbd1197f54c6d772fd6cabb`.
- `BlockBehaviour.java`: A `517aa4bd9875e9f7b3f5fba472a84e82afd1e11e35d3c83dc2677d8bbeb4997d`; B `5f37987191165379c222c412949a0c80a904bf78a453035b1f9d86c6454510fa`.
- `SupportType.java`: both `2604faefc3d4732eafe16363f4e9db68f7a3772e81e062e7ef4441a54709f5c6`.
- `Block.java`: A `440b55e32f468572b4c078ab0ce5bb9b94f1c7abcaa03b03a9ce7faccb646857`; B `0c2df3d958078929c4cc2985e0d6a6a766cbbbb02066e6c9ac471981aa858656`.
- `Shapes.java` / `SliceShape.java`: both endpoints are byte-identical, with hashes `ec207e73b8e9de4b1234132f81126973f0cb2bb37646b070d273259f2e91311a` / `168cceaa0d1471f6998037792c6e2cce9f1b0a9b34e4266a971f4d1c8a3a0b6f`.
- Signal query: A `Level.java` hash `a6d0feba4b033e36dfa54214e1c9ca08a812f60efc97a5906f5e9cb13cd22dfe`; B `SignalGetter.java` hash `387c52b2812332787fbf16c4cca41f120d1afcd9d3130cffd9736689e0eeb86b`.
- Neighbor propagation: A `ServerLevel.java` hash `10651d46d2548f14ac0a0382b233311facbaa05790965d1e94019c0fe248fba0`; B `ServerLevel.java` hash `e1b59acb9d7d52259123dc8244480f8ce66e91e868d3510faefa95ced721a855`.

## Independently verified correction and remaining boundary

1. **The original closed-bottom-trapdoor witness is invalid.** A and B `TrapDoorBlock` define the closed bottom shape as `Block.box(0, 0, 0, 16, 3, 16)`. Its height is 3/16, but its upward face spans the full 16×16 footprint. `BlockBehaviour` routes the default `isFaceSturdy(..., UP)` check through `SupportType.FULL`; `BlockBehaviour#getBlockSupportShape` supplies the collision shape; `SupportType.FULL` calls `Block.isFaceFull` on the selected face. The thin panel therefore has a full UP face, and A's `RedStoneWireBlock#canSurviveOn` succeeds. The old topology does not distinguish A from B.

2. **The B-only branch has a narrow upper-neighbor guard.** At B `RedStoneWireBlock#getConnectingSide` adds `state.getBlock() instanceof TrapDoorBlock` before `canSurviveOn`. The branch still requires `shouldConnectTo` on the block directly above the adjacent trapdoor. That call is the one-argument overload, which passes `null` as the facing. `shouldConnectTo(BlockState, Direction)` accepts redstone wire without a facing; repeaters require a matching non-null facing, observers require their facing to match, and generic signal sources require a non-null direction. The proposed upper neighbor is thus specifically redstone wire in this call path, not an arbitrary connectable source. This is a real B source-branch difference, with no claim that it establishes a reachable world state.

3. **The proposed open-trapdoor/upper-wire replacement is not source-closed.** An open trapdoor lacks the full upward support face, so A fails `canSurviveOn` where B may pass its class exception. But the inspected source does not prove a persistent upper redstone wire through the trapdoor state transition and the notifications that can reach the changed signal query. `RedStoneWireBlock#updateShape` returns the existing state for `Direction.DOWN`; separately, `neighborChanged` tests `canSurvive` and removes unsupported wire. `TrapDoorBlock#use` and its power-driven callback write state with flag `2`, while `Level#setBlock` still evaluates its indirect shape-update path when flag `16` is clear. Power changes also use neighbor-update fan-out. Those facts identify both the local shape callback and the later support/removal route, but the exact topology's callback order and wire lifetime through that fan-out are not closed. No persistent open-trapdoor upper-wire witness or later A/B trapdoor-signal outcome is proved.

4. **Movement eligibility remains unestablished.** Fixed-state trapdoor shape geometry is unchanged. Any movement consequence would depend on redstone/block-state lifecycle producing a different `OPEN` state, which this project excludes from movement emulation. The finding correctly withdraws the original confirmed player-movement consequence and leaves the source branch as a conditional, unresolved candidate. Do not convert the candidate into a replacement finding or implementation handoff.

Citation locator note: F-09-R1 lists B's `SignalGetter.java` under `net/minecraft/world/level/redstone/`; the checked file is `net/minecraft/world/level/SignalGetter.java`. The SHA-256 listed in F-09-R1 matches that file. This path-only correction does not affect the withdrawal disposition or the unresolved wire-lifetime dependency.

## Disposition

Accept F-09-R1 as a withdrawal of the unsupported original trapdoor-signal movement claim. Retain only the real B connection-predicate branch as a conditional source candidate; its required persistent open-trapdoor/upper-wire witness and callback order remain unproved, and its world-state producer is outside movement emulation. Finding-specific handoff for implementation is blocked. This review does not accept an implementation-eligible finding, close D3/S5-02, reopen the pair inventory, freeze the pair, or claim runtime parity. The original finding and its `revision-required` review remain immutable under the bindings above.
