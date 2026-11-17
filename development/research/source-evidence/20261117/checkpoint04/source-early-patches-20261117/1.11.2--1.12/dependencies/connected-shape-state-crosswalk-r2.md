# 1.11.2→1.12 connected-shape crosswalk correction r2

## Bound inputs

This is a narrow correction to the pane-neighbor exception statement in the original crosswalk. It does not re-audit the state matrix, geometry, callers, or runtime behavior.

- Original crosswalk: `dependencies/connected-shape-state-crosswalk.md`, SHA-256 `C5D142FF3F4BB9327194ABC989E1539EF9F1339291F1B1B19D408CEC20A01017`.
- Original independent audit: `dependencies/connected-shape-state-crosswalk-audit/verdict.md`, SHA-256 `ECFF55BB9EEF16EFC268CEEC70F2A9819E58AF17FFD28E83A5B43A8589C8F53F` (REQUEST CHANGES; this amendment addresses its stated correction).
- Pane claim: `findings/pane-neighbor-face-collision.md`, SHA-256 `A9D9C1ECC4AB32B8E8B805D38FDDA431A6903988BDC1BFCA624DB4980C21CA2E`; accepted verdict SHA-256 `75592D9FEF09BDC430B91231934F2E052C48D7399C77AA56B9ABFD2D64BA6EDD`.
- Fence claim/verdict SHA-256s: `3C5BB22D0700858739E0815FFC295C119AAB0787D846705C02FD9DB4835C6066` / `B2C3D12448447F22433AB648D92CB63E2BCB5E75037A38EF9BBD836268BC2075`.
- Wall claim/verdict SHA-256s: `D9012E7C589F6A72A0716F17E19AB43A060772C8C1E5BD93DF371E5656AC1D8D` / `2FC8C608E6B5ECF75DABB530D4448EFE94466C6476754850048990CD15D7129F`.
- Exact 1.11.2 / 1.12 PaneBlock source SHA-256s: `1E8968E7D642358CCE90F0D3156F07F19C5BCA28D2008D5D2D4BF0ECCC7266A9` / `83862F6633B8241C3788E9C9A7E4FD0275C010EC790019B8003908996F4814B3`.
- Exact 1.12 Block and StainedGlassBlock source SHA-256s: `E4A90ECA411E7B0E14F5018F7385EC29891F6CDF1A1E917FA648B5712F9B33A1` / `64CFA035B8C1230BCFE5E269410D9BC52D3C883F4367CAF988F87F150215098D`.

## Corrected pane-neighbor cases

The original crosswalk's sentence that stained glass is excluded and does not connect is wrong. In 1.12, `PaneBlock.shouldConnectTo` returns true for a non-excluded `SOLID` face or any `MIDDLE_POLE_THIN` face. `StainedGlassBlock` extends `TransparentBlock` and inherits `Block.getFaceShape`, which returns `SOLID`. `Blocks.STAINED_GLASS` is absent from the pane exception list. Stained glass therefore connects through the `SOLID` arm, as does ordinary glass. This is not a newly introduced difference: 1.11.2 explicitly accepts both glass blocks.

For the cases established by these predicates:

- Pane neighbors: 1.11.2 connects to this pane and to any `PaneBlock`; 1.12 connects to any horizontal `MIDDLE_POLE_THIN` neighbor. Thus same-block and different-`PaneBlock` neighbors connect in both versions. Iron bars, which are a `PaneBlock`, connect to panes in both versions. No same-block or same-material requirement applies to the 1.12 thin-pole arm.
- Glass neighbors: ordinary glass and stained glass connect in both versions. In 1.11.2 they are explicit legacy cases (ordinary glass is also cube-shaped); in 1.12 they connect through non-excluded `SOLID` faces. Glass color/material identity does not create a pane exception.
- The 1.11.2 explicit `STAINED_GLASS_PANE` case and its general `PaneBlock` case both connect; in 1.12 stained-glass panes connect through the same `MIDDLE_POLE_THIN` rule as other panes.
- The 1.12 pane exception list remains separated on its `SOLID` arm: shulker boxes, abstract leaves, beacon, cauldron, glowstone, ice, sea lantern, piston, sticky piston, piston head, melon, pumpkin, lit pumpkin, and barrier do not connect by `SOLID`. This statement does not change the separately accepted leaves witness. No added material-wide exception is inferred; the predicate checks the listed block classes/identities and face shape.

The accepted pane-plus-leaves witness remains: the old default-cube predicate connects the leaves neighbor, while 1.12 excludes `AbstractLeavesBlock` despite its inherited `SOLID` face. This is the only pane difference asserted here. The previously accepted fence/wall gate per-axis witnesses, direction mapping, and all other claims in the original crosswalk are carried forward unchanged; they are not re-audited by this correction.

## Caller boundary

Retain the caller boundary: fence, wall, and pane collision methods resolve virtual properties only when `forceShape` is false; with `forceShape=true`, they consume the supplied state. Shape accessors resolve virtual properties separately. This correction does not claim movement reachability for the forced-shape route.

No trajectory, full pair coverage, intermediate-version boundary, or runtime parity conclusion is added.

