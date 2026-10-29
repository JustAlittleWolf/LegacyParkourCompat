# Product reconciliation rebind: normalized DDA provider inventory

This immutable rebind supersedes only the inventory digest references in earlier product-reconciliation notes. The normalized current `full-ray-provider-inventory.md` has SHA-256 `5B970E9685C73E044F51771CA5FE79F00E4D97984F44E17F82978CB6EF05914E`. The author's row-count clarification is `inventory-row-count-correction.md`, SHA-256 `A717C149BE0F8301839552B2CEAFCFA6F381B9E7E370FBF230207BAB7703D0C4`.

The correction normalizes the existing `BannerBlock.Wall.updateShape` table row so it is counted consistently. The inventory now parses as 35 writer rows. It adds no writer and changes no accepted behavior or movement-consumer scope: the independently accepted movement-consumer closure remains Anvil and Chest; the Banner entry is a ray-time writer only, and other provider-consumer composition remains open.

**Reconciliation: HOLD unchanged; no product edits.** The neutral traversal hook still leaves block eligibility, provider dispatch, hit construction, and remembered-miss behavior to a caller. The current modern-pick observer does not establish historical provider visitation and first-hit order. The normalized inventory and its clarification do not close the source-faithful visitor needed to integrate the Anvil/Chest cache behavior. The `V1_8` native barrier remains necessary because 1.8 and 1.8.1 canonical source roots are unpublished; this inventory covers only 1.8.9.

Reopen integration after a source-closed historical provider/hit adapter and native fallback are available, bounded to the earliest applicable profile supported by ready source evidence. No build, tests, or runtime checks were run.
