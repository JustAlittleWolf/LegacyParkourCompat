# Independent audit: fence stair collision arm and level-eight water beside bed

## Reviewed source evidence

**Verdict: supported for the bounded player movement operations and exact releases listed below.** This independent audit is based on the exact read-only `ornithe-feather` sources, the source identities recorded below, the accepted endpoint audit and scope disposition, and the two immutable author reports. It covers query-time virtual-property resolution against the same received block states. It excludes block placement, support validation, neighbor updates, vanilla state production/evolution, server historical physics, and runtime parity.

| Reviewed artifact | SHA-256 |
|---|---|
| Fence author report: `fence_query_1112/report.md` | `174D4DEB4C29BD2BCFAF4B53BDE3875459A0F88601B7D6EA7EE338F367CA886C` |
| Water author report: `water_bed_predicate_1112/report.md` | `E2505CF4E466D3B41201852CF506653F43E41B8B57BA1CFB8999F30E72210FAF` |
| Fence lane finding: `canonical-findings/1.11.2--1.12.2/fence-stair-collision-arm.md` | `D37000C64F89D20E6574E60B128387D393803FF927B77A3197A7D5EAAE145CF7` |
| Water lane finding: `canonical-findings/1.11.2--1.12.2/level-eight-water-bed-face.md` | `D7295AF8169A9615E0CCB03C7141908B679B06628A32ED5D42DDE59AFB242A96` |
| Accepted endpoint source audit used as context: `full-pair-audit-r3/report.md` | `68040F4BF78407D074311ED1B1EB2030F3659D5EB811285E664EFE5FA8E99974` |
| Accepted scope disposition used as context: `full-pair-audit-r3/scope-disposition.md` | `2BAEE3E5B4018DDFC2D53264518A2B31AA2950C0C8A2E02461A3C585BFF16254` |

I recomputed the report and finding hashes. I independently inspected the cited source operations, exact Java file hashes, player collision/fluid callers and gates, and exact-version source identities. The author claims reviewed here are not transferred to other releases.

## Exact source identity and aliases

All inspected source trees use the Ornithe Feather Gen2 `build.2` family and Vineflower 1.12.0. Exact identity is anchored by the release-specific ready source manifests for 1.10.2, 1.11, 1.11.1, 1.11.2 and 1.12.2; for 1.12 and 1.12.1 the per-version embedded `.decompile-provenance.json` records the corresponding Feather mapping artifact, client input and a path-to-source-hash map. The inspected 1.12/1.12.1 Java files match those embedded path hashes; the 1.12.2 files match the ready-manifest identity. The water report records these release artifact identities and the inspected source hashes.

Fence applicability was checked at 1.10.2, 1.11, 1.11.1, 1.11.2, 1.12, 1.12.1 and 1.12.2. Relevant `FenceBlock.java` SHA-256 values are `d9375bba3b41ae408bdf65d69b25aecad522a6d0b45b38d30f77d12b343a4909` (1.10.2), `790f3f58a2902a8f2ade860221c4bf1161ba9e82e92cd6ba4596b3b4002794e5` (1.11), `856cb92d4613289747a34a6bc85f306ca9d44b7057a45c79f2020d13025d1cf7` (1.11.1 and 1.11.2), and `633ad3b20250bd010a28d0b1362b24c81a5508be8a81a9d826953a3c9c4a703e` (1.12, 1.12.1 and 1.12.2). The corresponding stair class is `ee143aced2a6902563cddbae77e78d1d1a512244f278987684ebe5bcacba37cb` (1.10.2), `f1ec8567bac6140dd43770d4adcd9b589c25807845c8bac3b01a4b5b61289391` (1.11), `5df1782da6eb7acdb9935b3910a0484d6417661bfd6230ecf81df0de6d782627` (1.11.1 and 1.11.2), and `c52a011a759b6f3ef6d45162d3b61f464ceceee5d2d8735de6f6e25a56e55611` (all three 1.12 releases). In each exact 1.12 release, `World.java` line 1001 calls `BlockState.addCollisions(..., false)`; source line 1002 resumes border handling. This confirms that all requested 1.12 patch releases reach query-time fence-neighbor recomputation.

Water applicability was checked in the same seven releases. `LiquidBlock.java` is `b988d19e379d01d291cb014cb05e1579311c589654ce9ff4faa7f43bbba0533e` (1.10.2), `c91618c8445dd488dd61f4a01aed0799dae2faaf3585bc6f2d5164891e988244` (1.11, 1.11.1 and 1.11.2), and `00f7995dd49a48bf97316e30b3d6e41d8ee1e65dd212f3dc64891883621c4260` (1.12, 1.12.1 and 1.12.2). `BedBlock.java` is `7893d2ceb9e572562018b7e55d8fecdc33e0445be89c39522075a833ccd1e463` (1.10.2), `84f56aab19adae4b953a27040e5f431f65c8923d6bd169995ac5a5f629a024e1` (1.11, 1.11.1 and 1.11.2), and `48c127b2df425cb8d7ff60a284207de10a8408e90ee18a8449522a004a0b4369` (all three 1.12 releases). `FlowingLiquidBlock.java` has identical hash `6be4da4408c29a730fb33227514d8ad93f20b0adc87eda66796d069e3ae93be2` in 1.11–1.12.2; the 1.10.2 hash is `eac6b26e87f4b759bfea9adb1d1d7edcc4ad2b7c6a88677feab9fc83a7b50d79`.

## Independent finding verdicts

### Fence stair collision arm

Supported: the material/full-cube test remains in `FenceBlock.shouldConnectTo` in 1.10.2, 1.11, 1.11.1 and 1.11.2, and each corresponding `StairsBlock.isCube` returns false. These exact old releases therefore do not derive a fence collision arm toward the straight stair witness. 1.11.1 and 1.11.2 add a `forceShape` parameter, but their movement collision enumeration passes false, preserving virtual-neighbor recomputation for the player query.

In 1.12, 1.12.1 and 1.12.2, `FenceBlock.shouldConnectTo` queries the neighbor’s directional `FaceShape`. `resolveVirtualProperties` maps the neighbor south of the fence to face NORTH; a straight north-facing stair returns `SOLID` on that face. `addCollisions` then adds the enabled south-arm box. The call chain is `Entity.move` → `World.getCollisions` → state `addCollisions` → `FenceBlock.addCollisions` → `resolveVirtualProperties` → raw neighboring `BlockState` / `getFaceShape`. Thus same center and neighbor states produce a different query-time player collision list in the two release groups.

Applicability: old behavior is directly proven for 1.10.2 and 1.11–1.11.2; the directional-face behavior is directly proven for 1.12–1.12.2. Behavior earlier than 1.10.2 was not inspected and is unproven. No claim is made about state placement or evolution.

### Level-eight water beside bed

Supported: in 1.10.2 and 1.11–1.11.2, `LiquidBlock.isFaceSolid` falls back to `Block.isFaceSolid`, which reads the neighbor material’s `isSolid()` value. `BedBlock` uses `Material.WOOL`; this material is solid, so a horizontal bed side can supply the wall input. In 1.12–1.12.2, the query instead checks the neighboring full state’s face shape and `BedBlock.getFaceShape` returns `UNDEFINED` for every face/state.

The `getFlow` arithmetic and `LEVEL >= 8` gate are present in both groups. Given an otherwise zero horizontal depth gradient, a level-eight water state, a bed as the only horizontal wall candidate, air at the corresponding above-side and other candidate cells, the old predicate applies `normalize().add(0.0, -6.0, 0.0)` and yields downward flow; the new bed face does not apply that term and the result remains zero. The unchanged response path is `Entity.checkWaterCollisions` → `World.applyLiquidDrag` → `Block.applyMaterialDrag` / `LiquidBlock.getFlow` → aggregate normalized velocity addition. Exact player gates were checked: a boat mount bypasses water drag, and `PlayerEntity.hasLiquidCollision()` returns `!abilities.flying`. The eligible local-player response therefore requires water overlap, no boat mount and non-flying abilities.

Applicability: old behavior is directly proven in 1.10.2 and 1.11–1.11.2; new behavior is directly proven in 1.12–1.12.2. Behavior earlier than 1.10.2 was not inspected and is unproven. This is a query-time response on received state, not a claim about server physics or state production.

No implementation, product source, central backlog, tests, builds, decompilation, or runtime checks were performed.
