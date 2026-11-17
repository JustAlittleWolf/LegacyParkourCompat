# Independent narrow source audit: client tick/input r2 correction

**Verdict: ACCEPT**

Audited correction: `build/source-early-patches-20261117/1.10.2--1.11/dependencies/client-player-tick-input-r2.md`  
SHA-256: `5A510BCD0697FFB2CD45D8BAA4A4C4EC058261EF531A5E26DA11E77A57E6B389`

Bound original report: `client-player-tick-input.md`, SHA-256 `1169492F3949940BE56A6E3169537B7401EFCFCF8DB522DC686124BB2C851AB2`.  
Bound prior verdict: `client-player-tick-audit/verdict.md`, REQUEST CHANGES, SHA-256 `5385D0166AFCE3C1412F8AB9656C0C308EB59886AD136891C11F77DF54F8EB7F`.

The correction is source-supported. In exact 1.11, `ItemStack.EMPTY` is constructed with a null item and its cached empty state makes `getItem()` return `Item.byBlock(Blocks.AIR)`. `Item.init()` registers an `AirItem` for `Blocks.AIR` in the block-item map, and `Items.AIR` resolves the `air` registry entry; therefore the returned item is `Items.AIR`, not null. `LocalClientPlayerEntity.mobTick()` compares the chest stack item to `Items.ELYTRA` before calling `ElytraItem.canFly`, so an empty chest stack still fails that comparison. The report's existing no-new-movement-delta conclusion remains supported.

For this narrow check, the inspected 1.11 `ItemStack.java`, `Item.java`, `Items.java`, and `LocalClientPlayerEntity.java` hashes match their exact source-manifest rows. The prior verdict's unchanged tick/input and packet findings are reused; no full comparison was repeated.

This verdict accepts only the one-sentence correction. Exact pair `1.10.2→1.11` remains partial; this does not close pair coverage or establish implementation or runtime parity.
