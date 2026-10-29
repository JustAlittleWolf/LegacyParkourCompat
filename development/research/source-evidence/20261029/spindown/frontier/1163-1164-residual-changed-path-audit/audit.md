# Independent audit: 1.16.3 → 1.16.4 residual changed paths

## Verdict

The residual TSV is a complete, hash-correct changed-path ledger for the canonical Mojmap manifests: 64 changed shared Java paths, 7 additions, and 0 removals. The focused dispositions for EnderDragon, FireworkRocketEntity, and ServerGamePacketListenerImpl are supported with the qualifications below. This is not a full-pair freeze, runtime parity finding, or closure of external server packet/callback producers.

There is one artifact-identity discrepancy: the assignment supplied closure SHA-256 `05f4d4ee92e19984cf138a89f84fce9a628e9a4e420e2387d5f65cae9a5bfe7a`, but the current `closure.md` hashes to `ac47558c7fe7ac0c67a92c7cf6f94a815c61902bcec5b5ecb2bdf4a89855edb3`. The TSV hash matches its supplied digest `d26252d6822e271123b146ff5b61d70496e19fc72629c16284895e22da0921fb`. I audited the current closure contents and the pinned TSV; the closure claim itself cannot be bound to the closure revision named in the assignment until that digest is reconciled.

## Source identity and path reconciliation

Read-only canonical roots:

- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.16.3\mojmap\`
- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.16.4\mojmap\`

The release parents each contain only `mojmap`. The mapping-root provenance hashes are 1.16.3 `513271804ead6757345d305717e7e0bfc1641434c35a3b254f287bd8fd281498` and 1.16.4 `1e04555c915527a398a2c7150679fb24d056f54bd680261e3c1dd55da821147c`. Their source maps contain 3,516 and 3,523 entries. The manifests identify the 1.16.3 client JAR/mapping hashes `da85197babd989a7de318774cf5d97bcef95e89a70c9fa9a4c8180463e9589fa` / `c41c58faaf3a3b651d8883b9b1df355cb6ad59c4d6296b2e1ffc3dfe2bf9be3`, and 1.16.4 hashes `d6740464ee2bdf37d285c5d368d2e16497492427fb5783e2db3482bbf0079ed0` / `93f188a0038fec8c2402d78e7acca983680978d6a9237c1fab80f22caddb00fe`. These are provenance input pins; this audit did not download upstream inputs.

I regenerated the manifest path/hash delta and recomputed the actual SHA-256 of every TSV-listed source file. All 71 ledger rows match both manifests and canonical file bytes; no row is missing or extra, and no changed or added manifest path is omitted. Counts independently reconcile to 64 changed, 7 added, 0 removed. No classified row source hash mismatch was found. In particular, all seven additions are represented on the 1.16.4 side with `-` on the absent side.

The closure's correction of the 1.16.4 provenance digest is right: the canonical hash is `1e04555c915527a398a2c7150679fb24d056f54bd680261e3c1dd55da821147c`, not the mistyped value copied into the earlier block-callback closure. The `ServerLevel.java` per-path digest in the TSV also matches the canonical manifest and source file.

## Residual path review

I used the existing ordinary-tick, direct-response, movement-modifier, movement-tag, and block-callback closures/audits only to exclude their owned analyses from this residual review. The new audit does not reopen or transfer those conclusions. Collision-shape aggregation remains separately owned.

- **EnderDragon:** In `EnderDragon#tick`, the `knockBack` caller is inside `!this.level.isClientSide && this.hurtTime == 0`. In `knockBack`, 1.16.3 computes `h = f * f + g * g`, while 1.16.4 computes `h = Math.max(f * f + g * g, 0.1)` before `entity.push(f / h * 4.0, 0.2F, g / h * 4.0)`. This can change velocity written to a `LivingEntity`, including a player, in the server simulation. Since the changed caller is server-only, it is a causal server-side input note, not a historical client movement delta. Do not interpret the no-client-physics boundary as proving every resulting server packet/state path equivalent.

- **FireworkRocketEntity:** The delta is confined to the unattached rocket branch: 1.16.3 multiplies its own horizontal motion by `1.15`; 1.16.4 uses `horizontalCollision ? 1.0 : 1.15`. It does not write player velocity. The distinct attached fall-flying branch remains unchanged and directly writes the attached living entity's velocity. `FireworkRocketItem#use` (same source hash on both sides) constructs an attached rocket for the player only when `player.isFallFlying()`, and only on the logical server. Thus the player-affecting attached route exists, but the changed multiplier does not reach it. This distinction is supported and should remain explicit.

- **ServerGamePacketListenerImpl:** The changed hunks are for text-filter setup/processing, chat and book handling, not the movement input/move packet handlers. I extracted and compared the complete `handlePlayerInput(ServerboundPlayerInputPacket)` and `handleMovePlayer(ServerboundMovePlayerPacket)` method bodies; both are text-identical between releases. The whole server listener file is therefore a changed-path row but these edits do not introduce a movement handler delta. Its server-side role and outgoing packet/state production are not closed by this determination; retain the broader external packet-producer gap.

Other residual rows are presentation/UI/social, authentication and protocol login/book serialization, server diagnostics/configuration, server world-generation lookup, or metadata. The table dispositions are consistent with this bounded movement classification; this is not a claim that all server-side movement-affecting inputs have been inventoried.

## Limits and open dependencies

- Collision-shape aggregation/provider review remains outside this assignment.
- Effective built-in, data-pack, and server-supplied tag/resource membership is not established by the Java manifests.
- Unchanged client receivers and callback bodies do not establish closure over all external packet producers, callbacks, or server-produced state.
- Endpoint comparison does not establish an intermediate patch-introduction boundary.
- No tests, builds, launches, product files, canonical sources, histories, or wiki artifacts were used or changed.
