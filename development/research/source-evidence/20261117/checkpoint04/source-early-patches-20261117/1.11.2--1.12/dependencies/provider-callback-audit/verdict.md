# Independent source audit: provider/callback inventory

Verdict: **ACCEPT** for the bounded inventory only.

## Bound inputs

- Inventory: `build/source-early-patches-20261117/1.11.2--1.12/dependencies/provider-callback-inventory.md`, SHA-256 `39052ed2f95606f012876136a3277b2ff9fc55c075d19936e5e384afc6b193d8`.
- Bound coverage: `build/source-early-patches-20261117/1.11.2--1.12/coverage.md`, SHA-256 `da8f52d9b0e6e6445612cdf1f80096712e10b824f72c3d499f829b6024313514`.
- Source publication identities match the report: 1.11.2 readiness SHA-256 `ceabfb266993567aab8c867525365e18aa0a0d7ba5a2cb7b6cfcc83d21318c40`, source-manifest SHA-256 `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`; 1.12 mapping-local provenance SHA-256 `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467`.

## Audit results

The named five-candidate boundary is consistent with coverage: fence, wall, pane, bed collision response, and liquid-flow face classification are explicitly excluded from this inventory and not accepted by it. The report keeps full provider closure, callback-to-player reachability, moving/piston block-entity state and timing, exhaustive stair combinations, and indirect FaceShape callers open. It makes no pair-freeze claim.

I independently checked the listed FaceShape classifications against the exact 1.12 sources. One explicit declaration not named in the table is `DoubleStoneSlabBlock.getFaceShape` (`DoubleStoneSlabBlock.java:15–17`, SHA-256 `1a3734ac32471179ba9b4637e857f0131968077abbe1f82535acc3fe73300729`). It returns `SOLID` for every face. The report’s `SlabBlock` row describes double slabs as `SOLID` on all faces (`SlabBlock.java:53–61`, SHA-256 `dc510f95b373bc2eccd128fbc82e2f07babc658d8446ab1dbc4e5dd4f27293e0`), which covers that result. The subclass override is redundant with this branch. In 1.11.2, `DoubleStoneSlabBlock` only overrides `isDouble()` (file SHA-256 `134f7fafbbc635b8367f1fbf0f0375e0eb95a387bc1d8667ef3c9160e899121f`); the old `Block.isFaceSolid` predicate returns the solid material property (source `Block.java:343–345`). Therefore the omitted class name does not leave a distinct face classification or movement delta unaccounted for in this bounded report.

The five candidate exclusions and selected provider/callback classifications match the source/coverage boundary. I independently scanned changed common `net/minecraft/block/*.java` sources for changed `Box` construction/box lines and found none outside the described candidate operations. The bounded method screen’s callback/provider distinctions are appropriately qualified: unchanged callback bodies do not establish player reachability, and the inventory leaves those gates open. The listed callback families and their stated partial reachability limits are consistent with the paired source screen.

## Limits

This verdict accepts only the immutable bounded inventory and its stated screen. It does not establish complete changed-provider or callback closure, accept candidate findings, resolve open dependencies, or freeze the 1.11.2→1.12 pair. The report’s collision-box scan is a bounded source diff screen, not proof that every dynamic provider or caller is closed.
