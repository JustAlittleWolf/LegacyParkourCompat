# Erratum 1 — block-break response closure

This erratum updates the member disposition in `report.md`; the original census remains unchanged. The fresh independent report at `../block-ack-shape-response-review/report.md` finds no changed LocalPlayer `isColliding` predicate from the 1.16.5→1.17 `Shapes.create(AABB)` arithmetic transition: every player pose has horizontal width 0.6F or 0.2F, which fails grid encoding in both versions, leaving the same exact AABB coordinates. The paired client response route remains `ClientPacketListener.handleBlockBreakAck` → `MultiPlayerGameMode.handleBlockBreakAck` → `Entity.isColliding` → conditional `absMoveTo`, but there is no branch flip under equal raw inputs from this encoder change.

The independent review’s source identity is corrected by its `erratum-1.17-publication-manifest.md`: 1.17’s embedded `.decompile-provenance.json` exists, its SHA-256 is bound there, and all nine reviewed source hashes match its `sources` map. Withdraw the review’s earlier manifest-gap statement.

The census report’s block-ack row is now **paired no-difference for the named AABB encoder change**. This does not close other direct response behavior in that handler, nor the distinct moving block-query case accepted in the collision-review addendum. Other open callsite dependencies listed in the census remain open.
